package wolforce.ascendancytools.data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import wolforce.utils.stacks.UtilInventory;

public abstract class Ascendancy {

	public static final String PICKAXE = "Pickaxe";
	public static final String AXE = "Axe";
	public static final String SHOVEL = "Shovel";
	public static final String HOE = "Hoe";
	public static final String[] allSpeedTypes = { PICKAXE, AXE, SHOVEL, HOE };

	private static final String ASCENDANCY_TAG_ID = "ascendancy";

	private static final String TOOL_ID = "ascendancyId";
	private static final String MAX_DURABILITY = "maxDurability";
	private static final String UNLOCKS_ARRAY = "unlocksArray";
	private static final String SPEED = "Speed";
	private static final String SPECIALIZATION = "Specialization";
	private static final String CHOICES = "choices";

	public enum UnlockState {
		UNLOCK_READY, UNLOCK_UNREADY, UNLOCK_UNLOCKED, UNLOCK_UNWANTED_1, UNLOCK_UNWANTED_2, UNLOCK_GIVEN_UP, UNLOCK_BLOCKED;

		byte Byte() {
			return (byte) ordinal();
		}
	}

	public static void initTool(ItemStack stack, Level world, Player player) {

		setMaxDurability(stack, 100);

		setToolId(stack, (player.getUUID() + "" + System.currentTimeMillis()).hashCode());
		for (String type : allSpeedTypes) {
			setBaseSpeed(stack, type, 1f);
		}
		CompoundTag tag = getAscendancyTagOf(stack);

		// init unlocks
		byte[] unlocksArray = new byte[AscendancyNode.values().length];
		Arrays.fill(unlocksArray, UnlockState.UNLOCK_UNREADY.Byte());
		set(unlocksArray, AscendancyNode.pickaxe_speed, UnlockState.UNLOCK_READY);
		set(unlocksArray, AscendancyNode.axe_speed, UnlockState.UNLOCK_READY);
		set(unlocksArray, AscendancyNode.shovel_speed, UnlockState.UNLOCK_READY);
		set(unlocksArray, AscendancyNode.hoe_speed, UnlockState.UNLOCK_READY);
		tag.putByteArray(UNLOCKS_ARRAY, unlocksArray);
	}

	public static void unlock(Player player, ItemStack stack, int choice) {
		AscendancyNode[] choices = getDefinedChoices(stack);
		if (choices == null)
			return;
		AscendancyNode chosenNode = choices[choice];

		if (!UtilInventory.hasRequiredStacks(player, chosenNode.ingredients)) {
			player.sendMessage(new TextComponent("You don't have the required materials.").withStyle(Style.EMPTY.withColor(0xFF4422)), null);
			return;
		}

		UtilInventory.removeRequiredStacks(player, chosenNode.ingredients);

		CompoundTag tag = getAscendancyTagOf(stack);
		byte[] unlocksArray = tag.getByteArray(UNLOCKS_ARRAY);
		for (int i = 0; i < choices.length; i++) {
			AscendancyNode a = choices[i];
			if (a == chosenNode) {
				set(unlocksArray, a, UnlockState.UNLOCK_UNLOCKED);
			} else {
				unwantNode(a, unlocksArray);
			}
		}

		makeMoreNodesReady(chosenNode, unlocksArray);
		chosenNode.action(stack);
		int maxDurability = (int) (getMaxDurability(stack) * 1.25);
		setMaxDurability(stack, maxDurability);
		stack.setDamageValue(maxDurability - 1);
		resetChoices(stack);
	}

	//
	//
	//
	//

	public static void setMaxDurability(ItemStack stack, int value) {
		getAscendancyTagOf(stack).putLong(MAX_DURABILITY, value);
	}

	public static int getMaxDurability(ItemStack stack) {
		return getAscendancyTagOf(stack).getInt(MAX_DURABILITY);
	}

	//
	//

	private static List<AscendancyNode> getReadyUnlocks(ItemStack stack) {
		CompoundTag tag = getAscendancyTagOf(stack);
		byte[] unlocksArray = tag.getByteArray(UNLOCKS_ARRAY);
		List<AscendancyNode> list = new LinkedList<>();

		AscendancyNode[] values = AscendancyNode.values();
		for (AscendancyNode a : values) {
			if (is(unlocksArray, a, UnlockState.UNLOCK_READY) //
					|| is(unlocksArray, a, UnlockState.UNLOCK_UNWANTED_1) //
					|| is(unlocksArray, a, UnlockState.UNLOCK_UNWANTED_2)) {
				list.add(a);
			}
		}

		if (list.size() < 3) {
			int size = list.size();

			for (int i = values.length - 1; i >= 0; i--) {
				AscendancyNode a = values[i];
				if (is(unlocksArray, a, UnlockState.UNLOCK_GIVEN_UP)) {
					list.add(a);
					size++;
					if (size == 3)
						return list;
				}
			}

		}

		return list;
	}

	private static void makeMoreNodesReady(AscendancyNode from, byte[] unlocksArray) {
		for (String fromString : from.makesReady) {
			AscendancyNode a = AscendancyNode.valueOf(fromString);
			if (!is(unlocksArray, a, UnlockState.UNLOCK_GIVEN_UP) && //
					!is(unlocksArray, a, UnlockState.UNLOCK_UNLOCKED)) {
				set(unlocksArray, a, UnlockState.UNLOCK_READY);
			}
		}
	}

	private static void unwantNode(AscendancyNode a, byte[] unlocksArray) {

		if (is(unlocksArray, a, UnlockState.UNLOCK_READY))
			set(unlocksArray, a, UnlockState.UNLOCK_UNWANTED_1);

		else if (is(unlocksArray, a, UnlockState.UNLOCK_UNWANTED_1))
			set(unlocksArray, a, UnlockState.UNLOCK_UNWANTED_2);

		else if (is(unlocksArray, a, UnlockState.UNLOCK_UNWANTED_2))
			set(unlocksArray, a, UnlockState.UNLOCK_GIVEN_UP);

	}

	private static boolean is(byte[] unlocksArray, AscendancyNode a, UnlockState s) {
		return unlocksArray[a.ordinal()] == s.Byte();
	}

	private static void set(byte[] unlocksArray, AscendancyNode a, UnlockState s) {
		unlocksArray[a.ordinal()] = s.Byte();
	}

	//
	//

	public static long getToolId(ItemStack stack) {
		return getAscendancyTagOf(stack).getLong(TOOL_ID);
	}

	public static void setToolId(ItemStack stack, long ascendancyId) {
		getAscendancyTagOf(stack).putLong(TOOL_ID, ascendancyId);
	}

	//
	//

	public static float getSpeed(ItemStack stack, String type) {
		CompoundTag tag = getAscendancyTagOf(stack);
		float f = 1f;
		if (tag != null) {
			if (tag.contains(type + SPEED))
				f = tag.getFloat(type + SPEED);

			for (String otherType : allSpeedTypes) {
				if (otherType != type && tag.contains(otherType + SPECIALIZATION))
					f -= 2;
			}
			if (tag.contains(type + SPECIALIZATION))
				f += 2;
		}
		return f;
	}

	public static void setBaseSpeed(ItemStack stack, String type, float value) {
		CompoundTag tag = getAscendancyTagOf(stack);
		if (tag != null)
			tag.putFloat(type + SPEED, value);
	}

//	public static void addSpeed(ItemStack stack, String type, float value, boolean reduceOthers) {
//		setBaseSpeed(stack, type, getSpeed(stack, type) + value);
//		if (reduceOthers) {
//			for (String otherType : allSpeedTypes) {
//				if (!otherType.equals(type))
//					setBaseSpeed(stack, otherType, getSpeed(stack, otherType) - value);
//			}
//		}
//	}

	//
	//

	public static void setCurrentChoices(ItemStack stack) {
		List<AscendancyNode> list = getReadyUnlocks(stack);
		if (list.size() == 0)
			return;

		Random rand = new Random();
		int i1 = list.remove(Math.min(list.size() - 1, (int) Math.abs(rand.nextGaussian() * 5))).ordinal();
		int i2 = i1;
		if (list.size() > 0)
			i2 = list.remove(Math.min(list.size() - 1, (int) Math.abs(rand.nextGaussian() * 5))).ordinal();
		int i3 = i2;
		if (list.size() > 0)
			i3 = list.remove(Math.min(list.size() - 1, (int) Math.abs(rand.nextGaussian() * 5))).ordinal();

		CompoundTag tag = getAscendancyTagOf(stack);
		tag.putIntArray(CHOICES, new int[] { i1, i2, i3 });
	}

	public static void resetChoices(ItemStack stack) {
		CompoundTag tag = getAscendancyTagOf(stack);
		if (tag.contains(CHOICES))
			tag.remove(CHOICES);
	}

	@Nullable
	public static int[] tryGetDefinedChoicesIndexes(ItemStack stack) {
		CompoundTag tag = getAscendancyTagOf(stack);
		if (tag.contains(CHOICES)) {
			int[] choiceIndexes = tag.getIntArray(CHOICES);
			if (choiceIndexes.length == 3) {
				return choiceIndexes;
			}
		}
		return null;
	}

	public static AscendancyNode[] getDefinedChoices(ItemStack stack) {
		int[] choiceIndexes = tryGetDefinedChoicesIndexes(stack);
		if (choiceIndexes != null && choiceIndexes.length == 3) {
			AscendancyNode[] nodes = AscendancyNode.values();
			return new AscendancyNode[] { nodes[choiceIndexes[0]], nodes[choiceIndexes[1]], nodes[choiceIndexes[2]] };
		}
		return null;
	}

	//
	//
	//
	//

	private static CompoundTag getAscendancyTagOf(ItemStack stack) {
		CompoundTag itemTag = stack.getTag();
		if (itemTag == null) {
			itemTag = new CompoundTag();
		}
		CompoundTag ascTag = itemTag.getCompound(ASCENDANCY_TAG_ID);
		if (ascTag == null) {
			ascTag = new CompoundTag();
		}
		itemTag.put(ASCENDANCY_TAG_ID, ascTag);
		stack.setTag(itemTag);
		return ascTag;
	}
}

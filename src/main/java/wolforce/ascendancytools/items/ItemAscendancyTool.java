package wolforce.ascendancytools.items;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import wolforce.ascendancytools.data.Ascendancy;
import wolforce.ascendancytools.net.Net;

public class ItemAscendancyTool extends Item {

	public ItemAscendancyTool() {
		super(new Item.Properties().stacksTo(1).defaultDurability(100));
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level world, Player player) {
		Ascendancy.initTool(stack, world, player);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (player.isShiftKeyDown() && stack.getDamageValue() == 0) {

			if (!world.isClientSide) {
				if (Ascendancy.tryGetDefinedChoicesIndexes(stack) == null)
					Ascendancy.setCurrentChoices(stack);
				long toolId = Ascendancy.getToolId(stack);
				int[] choices = Ascendancy.tryGetDefinedChoicesIndexes(stack);
				if (Ascendancy.tryGetDefinedChoicesIndexes(stack) != null)
					Net.sendOpenScreen(player, toolId, choices[0], choices[1], choices[2]);
			}
			return InteractionResultHolder.success(stack);
		}
		return InteractionResultHolder.pass(stack);
	}

	public boolean isFoil(ItemStack stack) {
		return !stack.isDamaged();
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
		for (String type : Ascendancy.allSpeedTypes) {
			float typeSpeed = Ascendancy.getSpeed(stack, type);
			list.add(new TextComponent("  " + type + " Speed: " + typeSpeed).withStyle(Style.EMPTY.withColor(0xAAAAAA)));
		}
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		return super.damageItem(stack, amount, entity, onBroken);
	}

	@Override
	public boolean isDamageable(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
		return toolAction == ToolActions.HOE_TILL;
	}

	public float getDestroySpeed(ItemStack stack, BlockState bs) {
		if (bs.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
			return Ascendancy.getSpeed(stack, "pickaxe");
		}
		if (bs.is(BlockTags.MINEABLE_WITH_AXE)) {
			return Ascendancy.getSpeed(stack, "axe");
		}
		if (bs.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
			return Ascendancy.getSpeed(stack, "shovel");
		}
		if (bs.is(BlockTags.MINEABLE_WITH_HOE)) {
			return Ascendancy.getSpeed(stack, "hoe");
		}
		return 1F;
	}

	public float getAttackDamage() {
		return 1;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return Ascendancy.getMaxDurability(stack);
	}

	@Override
	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
		return true;
	}

	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity entity2) {
		return true;
	}

	public boolean mineBlock(ItemStack stack, Level world, BlockState bs, BlockPos pos, LivingEntity entity) {
		tryGrow(stack);
		return true;
	}

	@Override
	public boolean isEnchantable(ItemStack p_41456_) {
		return false;
	}

	private void tryGrow(ItemStack stack) {
		if (stack.getDamageValue() < stack.getMaxDamage())
			stack.setDamageValue(stack.getDamageValue() - 1);
	}

}

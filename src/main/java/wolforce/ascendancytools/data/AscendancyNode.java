package wolforce.ascendancytools.data;

import static net.minecraft.world.item.Items.COBBLESTONE;
import static wolforce.ascendancytools.data.Constants.axeSpeeds;
import static wolforce.ascendancytools.data.Constants.hoeSpeeds;
import static wolforce.ascendancytools.data.Constants.pickaxeSpeeds;
import static wolforce.ascendancytools.data.Constants.shovelSpeeds;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import wolforce.utils.stacks.TagStack;

public enum AscendancyNode {

	pickaxe_speed("Pickaxe Speed", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[0]), "pickaxe_speed_2", //
			new TagStack(ItemTags.PLANKS, 4), new TagStack(Tags.Items.COBBLESTONE, 4)), //
	pickaxe_speed_2("Pickaxe Speed II", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[1]), "pickaxe_speed_3", //
			new TagStack(ItemTags.PLANKS, 4), new TagStack(Tags.Items.STONE, 4)), //
	pickaxe_speed_3("Pickaxe Speed III", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[2]), "pickaxe_speed_4,pickaxe_specialization", //
			new ItemStack(COBBLESTONE, 4)), //
	pickaxe_speed_4("Pickaxe Speed IV", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[3]), "pickaxe_speed_5", //
			new ItemStack(COBBLESTONE, 4)), //
	pickaxe_speed_5("Pickaxe Speed V", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[4]), "pickaxe_speed_6", //
			new ItemStack(COBBLESTONE, 4)), //
	pickaxe_speed_6("Pickaxe Speed VI", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[5]), "pickaxe_speed_7", //
			new ItemStack(COBBLESTONE, 4)), //
	pickaxe_speed_7("Pickaxe Speed VII", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[6]), "pickaxe_speed_8", //
			new ItemStack(COBBLESTONE, 4)), //
	pickaxe_speed_8("Pickaxe Speed VIII", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[7]), "pickaxe_speed_9", //
			new ItemStack(COBBLESTONE, 4)), //
	pickaxe_speed_9("Pickaxe Speed IX", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[8]), "pickaxe_speed_10", //
			new ItemStack(COBBLESTONE, 4)), //
	pickaxe_speed_10("Pickaxe Speed X", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.PICKAXE, pickaxeSpeeds[9]), "", //
			new ItemStack(COBBLESTONE, 4)), //
	//
	axe_speed("Axe Speed", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[0]), "axe_speed_2", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_speed_2("Axe Speed II", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[1]), "axe_speed_3", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_speed_3("Axe Speed III", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[2]), "axe_speed_4,axe_specialization", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_speed_4("Axe Speed IV", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[3]), "axe_speed_5", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_speed_5("Axe Speed V", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[4]), "axe_speed_6", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_speed_6("Axe Speed VI", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[5]), "axe_speed_7", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_speed_7("Axe Speed VII", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[6]), "axe_speed_8", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_speed_8("Axe Speed VIII", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[7]), "axe_speed_9", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_speed_9("Axe Speed IX", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[8]), "axe_speed_10", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_speed_10("Axe Speed X", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.AXE, axeSpeeds[9]), "", //
			new ItemStack(COBBLESTONE, 4)), //
	//
	shovel_speed("Shovel Speed", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[0]), "shovel_speed_2", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_speed_2("Shovel Speed II", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[1]), "shovel_speed_3", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_speed_3("Shovel Speed III", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[2]), "shovel_speed_4,shovel_specialization", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_speed_4("Shovel Speed IV", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[3]), "shovel_speed_5", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_speed_5("Shovel Speed V", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[4]), "shovel_speed_6", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_speed_6("Shovel Speed VI", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[5]), "shovel_speed_7", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_speed_7("Shovel Speed VII", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[6]), "shovel_speed_8", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_speed_8("Shovel Speed VIII", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[7]), "shovel_speed_9", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_speed_9("Shovel Speed IX", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[8]), "shovel_speed_10", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_speed_10("Shovel Speed X", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.SHOVEL, shovelSpeeds[9]), "", //
			new ItemStack(COBBLESTONE, 4)), //
	//
	hoe_speed("Hoe Speed", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[0]), "hoe_speed_2", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_speed_2("Hoe Speed II", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[1]), "hoe_speed_3", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_speed_3("Hoe Speed III", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[2]), "hoe_speed_4,hoe_specialization", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_speed_4("Hoe Speed IV", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[3]), "hoe_speed_5", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_speed_5("Hoe Speed V", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[4]), "hoe_speed_6", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_speed_6("Hoe Speed VI", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[5]), "hoe_speed_7", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_speed_7("Hoe Speed VII", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[6]), "hoe_speed_8", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_speed_8("Hoe Speed VIII", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[7]), "hoe_speed_9", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_speed_9("Hoe Speed IX", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[8]), "hoe_speed_10", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_speed_10("Hoe Speed X", "", s -> Ascendancy.setBaseSpeed(s, Ascendancy.HOE, hoeSpeeds[9]), "", //
			new ItemStack(COBBLESTONE, 4)), //
	//
	pickaxe_specialization("Pickaxe Specialization", "Increased pickaxe speed but decreases all other speeds.", null, "", //
			new ItemStack(COBBLESTONE, 4)), //
	axe_specialization("Axe Specialization", "Increased axe speed but decreases all other speeds.", null, "", //
			new ItemStack(COBBLESTONE, 4)), //
	shovel_specialization("Shovel Specialization", "Increased shovel speed but decreases all other speeds.", null, "", //
			new ItemStack(COBBLESTONE, 4)), //
	hoe_specialization("Hoe Specialization", "Increased hoe speed but decreases all other speeds.", null, "", //
			new ItemStack(COBBLESTONE, 4)), //
	;

	public final String title, description;
	public final AscendancyAction action;
	public final String[] makesReady;
	public final Object[] ingredients;

	AscendancyNode(String title, String description, AscendancyAction action, String makesReady, Object... ingredients) {
		this.title = title;
		this.description = description;
		this.action = action;
		this.makesReady = makesReady.trim().equals("") ? new String[] {} : makesReady.split(",");
		this.ingredients = ingredients;
	}

	private static interface AscendancyAction {
		void invoke(ItemStack stack);
	}

	public void action(ItemStack stack) {
		if (this.action != null)
			this.action.invoke(stack);
	}

	public static void init() {
		for (AscendancyNode node : values()) {
			for (int i = 0; i < node.ingredients.length; i++) {
				Object o = node.ingredients[i];
				if (o instanceof TagStack tagStack)
					tagStack.init();
			}
		}
	}

}

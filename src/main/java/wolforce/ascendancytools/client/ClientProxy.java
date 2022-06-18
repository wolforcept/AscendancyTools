package wolforce.ascendancytools.client;

import net.minecraft.client.Minecraft;
import wolforce.ascendancytools.client.screen.AscendancyScreen;

public class ClientProxy {

	public final static Minecraft MC = Minecraft.getInstance();

	public static void openAscendancyScreen(long toolId, int a, int b, int c) {
		MC.setScreen(new AscendancyScreen(toolId, a, b, c));
	}

//	public static void openAscendancyScreen(ItemStack stack) {
//	MC.setScreen(new AscendancyScreen(stack));
//}

//	public static void openAscendancyScreen() {
//		ItemStack stack = MC.player.getMainHandItem();
//		if (stack.getItem() == Items.ascendancy_tool.get()) {
//			MC.setScreen(new AscendancyScreen(a,b,c));
//			return;
//		}
//
//		stack = MC.player.getOffhandItem();
//		if (stack.getItem() == Items.ascendancy_tool.get()) {
//			MC.setScreen(new AscendancyScreen(stack));
//			return;
//		}
//
//	}

}

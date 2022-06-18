package wolforce.ascendancytools.net;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import wolforce.ascendancytools.AscendancyTools;
import wolforce.ascendancytools.client.ClientProxy;
import wolforce.ascendancytools.data.Ascendancy;

public class Net {

	private static SimpleChannel INSTANCE;

	public static void register() {

		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(AscendancyTools.MODID, "network"), //
				() -> AscendancyTools.MODID, //
				AscendancyTools.MODID::equals, //
				AscendancyTools.MODID::equals //
		);

		int id = 0;
		INSTANCE.registerMessage(id++, MessageAscendancy.class, MessageAscendancy::encode, MessageAscendancy::decode, MessageAscendancy::onMessage);
		INSTANCE.registerMessage(id++, MessageOpenAscendancyScreen.class, MessageOpenAscendancyScreen::encode, MessageOpenAscendancyScreen::decode, MessageOpenAscendancyScreen::onMessage);
	}

	public static void sendChoiceMessage(long toolId, int choice) {
		INSTANCE.sendToServer(new MessageAscendancy(toolId, choice));
	}

	public static void sendOpenScreen(Player player, long toolId, int a, int b, int c) {
		if (player instanceof ServerPlayer serverPlayer)
			INSTANCE.sendTo(new MessageOpenAscendancyScreen(toolId, a, b, c), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}

	public static class MessageAscendancy {

		private long toolId;
		private int choice;

		public MessageAscendancy(long toolId, int choice) {
			this.toolId = toolId;
			this.choice = choice;
		}

		public void encode(FriendlyByteBuf buff) {
			buff.writeLong(toolId);
			buff.writeInt(choice);
		}

		public static MessageAscendancy decode(FriendlyByteBuf buff) {
			return new MessageAscendancy(buff.readLong(), buff.readInt());
		}

		public void onMessage(final Supplier<NetworkEvent.Context> ctx) {
			Player player = ctx.get().getSender();
			ItemStack stack = tryGetToolWithId(player, toolId);
			if (stack != null)
				Ascendancy.unlock(player, stack, choice);
			ctx.get().setPacketHandled(true);
		}

		private static ItemStack tryGetToolWithId(Player player, long toolId) {
			for (ItemStack stack : player.getHandSlots()) {
				if (Ascendancy.getToolId(stack) == toolId) {
					return stack;
				}
			}
			for (ItemStack stack : player.getInventory().items) {
				if (Ascendancy.getToolId(stack) == toolId) {
					return stack;
				}
			}
			return null;
		}

	}

	public static class MessageOpenAscendancyScreen {

		private long toolId;
		private final int a, b, c;

		public MessageOpenAscendancyScreen(long toolId, int a, int b, int c) {
			this.toolId = toolId;
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public void encode(FriendlyByteBuf buff) {
			buff.writeLong(toolId);
			buff.writeInt(a);
			buff.writeInt(b);
			buff.writeInt(c);
		}

		public static MessageOpenAscendancyScreen decode(FriendlyByteBuf buff) {
			return new MessageOpenAscendancyScreen(buff.readLong(), buff.readInt(), buff.readInt(), buff.readInt());
		}

		public void onMessage(final Supplier<NetworkEvent.Context> ctx) {
			ClientProxy.openAscendancyScreen(toolId, a, b, c);
			ctx.get().setPacketHandled(true);
		}

	}
}

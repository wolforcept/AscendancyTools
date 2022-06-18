package wolforce.ascendancytools.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import wolforce.ascendancytools.AscendancyTools;
import wolforce.ascendancytools.data.AscendancyNode;
import wolforce.ascendancytools.net.Net;
import wolforce.utils.client.UtilRenderItem;
import wolforce.utils.stacks.TagStack;

public class AscendancyScreen extends Screen {

	public static boolean EDIT_MODE = false;

	public static final ResourceLocation GUI_TEXTURE = new ResourceLocation(AscendancyTools.MODID, "textures/gui/ascendancy_button.png");
	public static final TextComponent ID = new TextComponent(AscendancyTools.MODID + ":" + "ascendancy_screen");

	public static final int BUTTON_TEX_WIDTH = 128, BUTTON_TEX_HEIGHT = 128, BUTTON_DISTANCE = 4;

	private long toolId;
	private final AscendancyNode choice1, choice2, choice3;
	private ItemStack tooltipStack = null;

	public AscendancyScreen(long toolId, int choice1, int choice2, int choice3) {
		super(ID);
		this.toolId = toolId;
		AscendancyNode[] nodes = AscendancyNode.values();
		this.choice1 = nodes[choice1];
		this.choice2 = nodes[choice2];
		this.choice3 = nodes[choice3];
	}

	OnPress onPress(int index, AscendancyNode choice) {
		return (Button button) -> {
			if (button instanceof AscendancyButton ab)
				Net.sendChoiceMessage(toolId, index);
			AscendancyScreen.this.onClose();
		};
	}

	private AbstractWidget makeButton(int index, AscendancyNode node, int dx) {
		return new AscendancyButton(//
				width / 2 - BUTTON_TEX_WIDTH / 2 + dx, // x
				height / 2 - BUTTON_TEX_HEIGHT / 2, // y
				BUTTON_TEX_WIDTH, BUTTON_TEX_HEIGHT, // wh
				onPress(index, node), //
				node //
		);
	}

	@Override
	protected void init() {
		for (AbstractWidget widget : new AbstractWidget[] { //
				makeButton(0, choice1, -BUTTON_TEX_WIDTH - BUTTON_DISTANCE), //
				makeButton(1, choice2, 0), //
				makeButton(2, choice3, BUTTON_TEX_WIDTH + BUTTON_DISTANCE) //
		}) {
			addRenderableWidget(widget);
		}
	}

	private class AscendancyButton extends Button {

		private AscendancyNode node;

		public AscendancyButton(int x, int y, int w, int h, OnPress onClick, AscendancyNode node) {
			super(x, y, w, h, new TextComponent(node.title), onClick, (Button button, PoseStack pose, int mx, int my) -> {
				System.out.println(mx);
			});
			this.node = node;
		}

		@Override
		public void render(PoseStack pose, int mx, int my, float partialTicks) {

			RenderSystem.setShaderTexture(0, GUI_TEXTURE);
			blit(pose, x, y, /* w,h */width, height, /* u,v */0, 0, /* uw,vh */width, height, /* w,h */width, height);

			RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
			AscendancyScreen.this.minecraft.textureManager.getTexture(InventoryMenu.BLOCK_ATLAS).setFilter(false, false);

			int dy = 8;

			TextComponent titleComp = new TextComponent(node.title);
			font.draw(pose, titleComp, x + width / 2 - font.width(titleComp) / 2, y + dy, 0x000000);

			dy = 24;

			TextComponent descrComp = new TextComponent("Increases Axe Speed by 3");
			font.drawWordWrap(descrComp, x + 8, y + dy, width - 16, 0x000000);

			// draw requirements
			dy = 82;

			TextComponent textComp = new TextComponent("Requirements:");
			font.draw(pose, textComp, x + width / 2 - font.width(textComp) / 2, y + dy, 0x000000);

			dy += 16;
			int dx = 0;
			int w = (width - 32) / node.ingredients.length;
			for (Object obj : node.ingredients) {
				if (obj instanceof ItemStack stack) {
					int sx = 16 + x + dx;
					int sy = y + dy;
					UtilRenderItem.init(stack).pos(sx, sy, 100).renderGui();
					TextComponent itemDescrComp = new TextComponent("x" + stack.getCount());
					font.draw(pose, itemDescrComp, sx + 20, sy + 4, 0x000000);
					if (mx > sx && my > sy && mx < sx + 16 && my < sy + 16)
						tooltipStack = stack;
					dx += w;
				}

				if (obj instanceof TagStack tag) {
					int sx = 16 + x + dx;
					int sy = y + dy;
					if (tag.stacks.length > 0) {
						int itemIndex = (int) System.currentTimeMillis() / 1000 % tag.stacks.length;
						ItemStack stack = tag.stacks[itemIndex];
						UtilRenderItem.init(stack).pos(sx, sy, 100).renderGui();
						TextComponent itemDescrComp = new TextComponent("x" + tag.count);
						font.draw(pose, itemDescrComp, sx + 20, sy + 4, 0x000000);
						if (mx > sx && my > sy && mx < sx + 16 && my < sy + 16)
							tooltipStack = stack;
					}
					dx += w;
				}
			}

		}

	}

	@Override
	public void render(PoseStack pose, int mx, int my, float partialTicks) {
		super.render(pose, mx, my, partialTicks);
		if (tooltipStack != null) {
			renderTooltip(pose, tooltipStack, mx, my);
			tooltipStack = null;
		}
	}

}

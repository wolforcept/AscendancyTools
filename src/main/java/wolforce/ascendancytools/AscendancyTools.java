package wolforce.ascendancytools;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import wolforce.ascendancytools.data.AscendancyNode;
import wolforce.ascendancytools.net.Net;
import wolforce.ascendancytools.registry.Items;

@Mod(AscendancyTools.MODID)
public class AscendancyTools {

	public static final String MODID = "ascendancytools";

	public AscendancyTools() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onSetup);
		MinecraftForge.EVENT_BUS.addListener(this::onTagsUpdated);
		Items.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	private void onSetup(FMLCommonSetupEvent event) {
		Net.register();
	}

	public void onTagsUpdated(TagsUpdatedEvent event) {
		AscendancyNode.init();
	}
}

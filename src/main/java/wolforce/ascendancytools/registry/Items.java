package wolforce.ascendancytools.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import wolforce.ascendancytools.AscendancyTools;
import wolforce.ascendancytools.items.ItemAscendancyTool;

public class Items {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			AscendancyTools.MODID);

	public static final RegistryObject<Item> ascendancy_tool = ITEMS.register("ascendancy_tool",
			() -> new ItemAscendancyTool());

}

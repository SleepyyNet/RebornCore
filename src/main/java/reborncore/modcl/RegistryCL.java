package reborncore.modcl;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Prospector
 */
public abstract class RegistryCL {

	public LinkedHashMap<String, ItemCL> itemRegistry = new LinkedHashMap<>();
	public LinkedHashMap<String, BlockCL> blockRegistry = new LinkedHashMap<>();
	public LinkedHashMap<String, BlockContainerCL> blockContainerRegistry = new LinkedHashMap<>();
	public HashMap<ItemStack, String> oreEntries = new HashMap<>();

	protected static void register(ItemCL item) {
		GameRegistry.register(item);
	}

	protected static void register(BlockCL block) {
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block), block.getRegistryName());
	}

	protected static void register(BlockContainerCL block) {
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block), block.getRegistryName());
		GameRegistry.registerTileEntity(block.tileEntity, "TilePackagerRD");
	}

	public abstract void init(ModCL mod);
}

package zmaster587.libVulpes.api.material;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class MaterialRegistry {

	static HashMap<Object, MixedMaterial> mixedMaterialList = new HashMap<>();
	static HashMap<AllowedProducts, List<Block>> productBlockListMapping;
	static List<MaterialRegistry> registries = new LinkedList<>();

	@SideOnly(Side.CLIENT)
	static Object oreProductColorizer;

	public HashMap<String, zmaster587.libVulpes.api.material.Material> strToMaterial = new HashMap<>();
	public List<zmaster587.libVulpes.api.material.Material> materialList = new LinkedList<>();
	public Item[] oreProducts;


	public MaterialRegistry() {
		productBlockListMapping = new HashMap<>();
		registries.add(this);
	}

	@SideOnly(Side.CLIENT)
	public void init() {

	}
	public List<Block> getBlockListForProduct(AllowedProducts product) {
		return productBlockListMapping.get(product);
	}


}

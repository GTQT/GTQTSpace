package zmaster587.libVulpes.api.material;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class Material {
	String unlocalizedName, tool;
	String[] oreDictNames;
	int harvestLevel;
	int allowedProducts;
	int color;
	int index;
	//Hack assigning in libvulpes preinit
	public MaterialRegistry registry;

	public Material(String unlocalizedName, String tool, int level, int color, int allowedProducts, boolean hasOre) {
		this(unlocalizedName, tool, level, color, hasOre ? AllowedProducts.getProductByName("ORE").getFlagValue() | allowedProducts : allowedProducts, new String[] {unlocalizedName});
	}

	public Material(String unlocalizedName, String tool, int level, int color, int allowedProducts, MixedMaterial ... products) {
		this(unlocalizedName, tool, level, color, allowedProducts | AllowedProducts.getProductByName("ORE").getFlagValue(), new String[] {unlocalizedName});
	}

	public Material(String unlocalizedName, String tool, int level, int color, int allowedProducts) {
		this(unlocalizedName, tool, level, color, allowedProducts | AllowedProducts.getProductByName("ORE").getFlagValue(), new String[] {unlocalizedName});
	}

	public Material(String unlocalizedName, String tool, int level, int color, int allowedProducts, String[] oreDictNames) {
		this.unlocalizedName = unlocalizedName;
		this.tool = tool;
		this.harvestLevel = level;
		this.oreDictNames = oreDictNames;
		this.allowedProducts = allowedProducts;
		this.color = color;
	}

	/**
	 * @return true if the material is vanilla (Gold, iron)
	 */
	public boolean isVanilla() {
		return this.unlocalizedName.equals("Iron") ||  this.unlocalizedName.equals("Gold");
	}
	
	public void setIndex(int index) {
		this.index = index;
	}


	/**
	 * @return 32wide-bitmask corresponding to allowed products by this material
	 */
	public int getAllowedProducts() {
		return allowedProducts;
	}

	/**
	 * @return harvest level required to harvest the ore of this material
	 */
	public int getHarvestLevel() {
		return harvestLevel;
	}

	/**
	 * @return tool required to harvest the ore of this material
	 */
	public String getTool() {
		return tool;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	/**
	 * @return list of ore dictionary names for this material.  Example: {iron, pigiron}
	 */
	public String[] getOreDictNames() {
		return oreDictNames;
	}

	/**
	 * Used in rendering of the item and block
	 * @return color of the material 0xRRGGBB
	 */
	public int getColor() {
		return color;
	}

	@Deprecated
	public Block getBlock() {
		return registry.getBlockListForProduct(AllowedProducts.getProductByName("ORE")).get(index/16);
		//return LibVulpesBlocks.blockOre.get(this.ordinal()/16);
	}

	/**
	 * @return the meta value for the itemstack representing a block of this material
	 */
	public int getMeta() {
		return index % 16;
	}

	public int getIndex() {
		return index;
	}

}

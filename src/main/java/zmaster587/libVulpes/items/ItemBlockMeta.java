package zmaster587.libVulpes.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemBlockMeta extends  ItemBlock {

	public ItemBlockMeta(Block p_i45326_1_) {
		super(p_i45326_1_);
		
		setHasSubtypes(true);
	}

	@Override
	public String getTranslationKey(@Nonnull ItemStack stack) {
		return super.getTranslationKey(stack) + "." + stack.getItemDamage();
	}
	

	@Override
	public int getMetadata(int damage) {
		return damage & 15;
	}
	
}

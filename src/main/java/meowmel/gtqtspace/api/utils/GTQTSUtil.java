package meowmel.gtqtspace.api.utils;


import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;
import java.util.BitSet;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static net.minecraft.util.EnumFacing.*;

public class GTQTSUtil {

	@Nonnull
	public static ResourceLocation gtqtId(@Nonnull String path) {
		return new ResourceLocation("gtqtcore", path);
	}

	public static int intValueOfBitSet(BitSet set) {
		int result = 0;
		for (int i = 0; i < set.length(); i++) {
			result = result | (set.get(i) ? 1 : 0) << i;
		}
		return result;
	}

	public static BitSet forIntToBitSet(int i, int length) {
		return forIntToBitSet(i, length, new BitSet(length));
	}

	public static BitSet forIntToBitSet(int i, int length, BitSet result) {
		for (int j = 0; j < length; j++) {
			if (((i & (0b1 << j)) / (0b1 << j)) == 1) {
				result.set(j);
			} else {
				result.clear(j);
			}
		}
		return result;
	}

	public static <T> T getOrDefault(BooleanSupplier canGet, Supplier<T> getter, T defaultValue) {
		return canGet.getAsBoolean() ? getter.get() : defaultValue;
	}


	public static EnumFacing getFacingFromNeighbor(BlockPos pos, BlockPos neighbor) {
		BlockPos rel = neighbor.subtract(pos);
		if (rel.getX() == 1) {
			return EAST;
		}
		if (rel.getX() == -1) {
			return WEST;
		}
		if (rel.getY() == 1) {
			return UP;
		}
		if (rel.getY() == -1) {
			return DOWN;
		}
		if (rel.getZ() == 1) {
			return SOUTH;
		}
		return NORTH;
	}


	public static int getOrDefault(NBTTagCompound tag, String key, int defaultValue) {
		if (tag.hasKey(key)) {
			return tag.getInteger(key);
		}
		return defaultValue;
	}

	public static String translate(String key)
	{
		String result = I18n.translateToLocal(key);
		int comment = result.indexOf('#');
		String ret = (comment > 0) ? result.substring(0, comment).trim() : result;
		for (int i = 0; i < key.length(); ++i)
		{
			Character c = key.charAt(i);
			if (Character.isUpperCase(c))
			{
				System.err.println(ret);
			}
		}
		return ret;
	}

	public static final Material[] tierList = { MarkerMaterials.Tier.ULV, MarkerMaterials.Tier.LV, MarkerMaterials.Tier.MV, MarkerMaterials.Tier.HV, MarkerMaterials.Tier.EV, MarkerMaterials.Tier.IV, MarkerMaterials.Tier.LuV, MarkerMaterials.Tier.ZPM, MarkerMaterials.Tier.UV, MarkerMaterials.Tier.UHV, MarkerMaterials.Tier.UEV, MarkerMaterials.Tier.UIV, MarkerMaterials.Tier.UXV, MarkerMaterials.Tier.OpV, MarkerMaterials.Tier.MAX };


}
package meowmel.gtqtspace.api.unifications.ore;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class GTQTSStoneTypes {
    public static StoneType MOON_STONE;
    public static StoneType MARS_STONE;
    public static StoneType VENUS_STONE;
    public static StoneType METHANE_STONE;
    public static StoneType IO_STONE;
    public static StoneType MERCURY_STONE;

    public GTQTSStoneTypes(){
    }
    public static void init(){
        MOON_STONE = new StoneType(21, "moon_stone", SoundType.STONE, GTQTSOrePrefix.oreMoon, GTQTSpaceMaterials.MoonStone,
                () -> gtStoneState(GTQTSStoneVariantBlock.StoneType.MOON_STONE),
                state -> gtStonePredicate(state, GTQTSStoneVariantBlock.StoneType.MOON_STONE), false);
        MARS_STONE = new StoneType(22, "mars_stone", SoundType.STONE, GTQTSOrePrefix.oreMars, GTQTSpaceMaterials.MarsStone,
                () -> gtStoneState(GTQTSStoneVariantBlock.StoneType.MARS_STONE),
                state -> gtStonePredicate(state, GTQTSStoneVariantBlock.StoneType.MARS_STONE), false);
        VENUS_STONE = new StoneType(23, "venus_stone", SoundType.STONE, GTQTSOrePrefix.oreVenus, GTQTSpaceMaterials.VenusStone,
                () -> gtStoneState(GTQTSStoneVariantBlock.StoneType.VENUS_STONE),
                state -> gtStonePredicate(state, GTQTSStoneVariantBlock.StoneType.VENUS_STONE), false);
        METHANE_STONE = new StoneType(24, "methane_stone", SoundType.GLASS, GTQTSOrePrefix.oreMethane, Materials.Ice,
                () -> gtStoneState(GTQTSStoneVariantBlock.StoneType.METHANE_ICE),
                state -> gtStonePredicate(state, GTQTSStoneVariantBlock.StoneType.METHANE_ICE), false);
        IO_STONE = new StoneType(25, "io_stone", SoundType.STONE, GTQTSOrePrefix.oreIo, GTQTSpaceMaterials.IoStone,
                () -> gtStoneState(GTQTSStoneVariantBlock.StoneType.IO_STONE),
                state -> gtStonePredicate(state, GTQTSStoneVariantBlock.StoneType.IO_STONE), false);
        MERCURY_STONE = new StoneType(26, "mercury_stone", SoundType.STONE, GTQTSOrePrefix.oreMercury, Materials.Ice,
                () -> gtStoneState(GTQTSStoneVariantBlock.StoneType.MERCURY_STONE),
                state -> gtStonePredicate(state, GTQTSStoneVariantBlock.StoneType.MERCURY_STONE), false);
    }
    private static IBlockState gtStoneState(GTQTSStoneVariantBlock.StoneType stoneType) {
        return GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(stoneType);
    }

    private static boolean gtStonePredicate(IBlockState state, GTQTSStoneVariantBlock.StoneType stoneType) {
        GTQTSStoneVariantBlock block = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH);
        return state.getBlock() == block && block.getState(state) == stoneType;
    }
}
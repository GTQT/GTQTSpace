package meowmel.gtqtspace.common.block.blocks;

import gregtech.api.block.VariantBlock;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Random;

import static meowmel.gtqtspace.common.CommonProxy.Planet_TAB;

public class GTQTSStoneVariantBlock extends VariantBlock<GTQTSStoneVariantBlock.StoneType> {
    private static final PropertyEnum<GTQTSStoneVariantBlock.StoneType> PROPERTY = PropertyEnum.create("variant", GTQTSStoneVariantBlock.StoneType.class);
    private final GTQTSStoneVariantBlock.StoneVariant stoneVariant;

    public GTQTSStoneVariantBlock(@Nonnull GTQTSStoneVariantBlock.StoneVariant stoneVariant) {
        super(Material.ROCK);
        this.stoneVariant = stoneVariant;
        this.setRegistryName(stoneVariant.id);
        this.setTranslationKey(stoneVariant.translationKey);
        this.setHardness(stoneVariant.hardness);
        this.setResistance(stoneVariant.resistance);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.getState(GTQTSStoneVariantBlock.StoneType.MOON_STONE));
        this.setCreativeTab(Planet_TAB);
    }

    @Nonnull
    protected BlockStateContainer createBlockState() {
        this.VARIANT = PROPERTY;
        this.VALUES = GTQTSStoneVariantBlock.StoneType.values();
        return new BlockStateContainer(this, this.VARIANT);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }


    protected boolean canSilkHarvest() {
        return this.stoneVariant == GTQTSStoneVariantBlock.StoneVariant.SMOOTH;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this.stoneVariant == StoneVariant.SMOOTH ? (Block) GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock.StoneVariant.COBBLE) : this);
    }

    public enum StoneVariant {
        SMOOTH("gtqts_stone_smooth"),
        COBBLE("gtqts_stone_cobble", 2.0F, 10.0F),
        BRICKS("gtqts_stone_bricks");

        public final String id;
        public final String translationKey;
        public final float hardness;
        public final float resistance;

        StoneVariant(@Nonnull String id) {
            this(id, id);
        }

        StoneVariant(@Nonnull String id, @Nonnull String translationKey) {
            this(id, translationKey, 1.5F, 10.0F);
        }

        StoneVariant(@Nonnull String id, float hardness, float resistance) {
            this(id, id, hardness, resistance);
        }

        StoneVariant(@Nonnull String id, @Nonnull String translationKey, float hardness, float resistance) {
            this.id = id;
            this.translationKey = translationKey;
            this.hardness = hardness;
            this.resistance = resistance;
        }
    }

    public enum StoneType implements IStringSerializable {
        MOON_STONE("moon", MapColor.GRAY),
        MARS_STONE("mars", MapColor.RED_STAINED_HARDENED_CLAY),
        VENUS_STONE("venus", MapColor.ORANGE_STAINED_HARDENED_CLAY),
        METHANE_ICE("methane_ice", MapColor.ICE);

        public final MapColor mapColor;
        private final String name;

        StoneType(@Nonnull String name, @Nonnull MapColor mapColor) {
            this.name = name;
            this.mapColor = mapColor;
        }

        @Nonnull
        public String getName() {
            return this.name;
        }

        public OrePrefix getOrePrefix() {
            switch (this) {
                case MOON_STONE:
                case MARS_STONE:
                case VENUS_STONE:
                case METHANE_ICE:
                    return OrePrefix.stone;
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }

        public gregtech.api.unification.material.Material getMaterial() {
            switch (this) {
                case MOON_STONE:
                    return GTQTSpaceMaterials.MoonStone;
                case MARS_STONE:
                    return GTQTSpaceMaterials.MarsStone;
                case VENUS_STONE:
                    return GTQTSpaceMaterials.VenusStone;
                case METHANE_ICE:
                    return Materials.Methane;
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }
    }
}
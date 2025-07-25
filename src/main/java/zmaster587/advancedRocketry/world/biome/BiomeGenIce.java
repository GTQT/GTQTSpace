package zmaster587.advancedRocketry.world.biome;

import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import zmaster587.advancedRocketry.dimension.biome.CustomBiome;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

import static meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock.DirtType.MOON_DIRT;
import static net.minecraft.init.Blocks.FROSTED_ICE;
import static net.minecraft.init.Blocks.PACKED_ICE;

public class BiomeGenIce extends CustomBiome {

    public BiomeGenIce(BiomeProperties properties) {
        super(properties);

        //cold and dry
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;
        this.topBlock = PACKED_ICE.getDefaultState();
        this.fillerBlock= PACKED_ICE.getDefaultState();
    }

    @Override
    @Nonnull
    public List<SpawnListEntry> getSpawnableList(EnumCreatureType p_76747_1_) {
        return new LinkedList<>();
    }

    @Override
    public float getSpawningChance() {
        return 0f; //Nothing spawns
    }
}

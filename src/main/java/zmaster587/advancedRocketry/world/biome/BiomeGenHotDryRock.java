package zmaster587.advancedRocketry.world.biome;

import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.world.biome.Biome;
import zmaster587.advancedRocketry.api.AdvancedRocketryBlocks;
import zmaster587.advancedRocketry.dimension.biome.CustomBiome;

import static meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock.DirtType.IO_DIRT;

public class BiomeGenHotDryRock extends CustomBiome {

    public BiomeGenHotDryRock(BiomeProperties properties) {
        super(properties);

        //hot and stinks
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;
        this.topBlock = GTQTSMetaBlocks.GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF).getState(IO_DIRT);
        this.fillerBlock= GTQTSMetaBlocks.GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.DIRT).getState(IO_DIRT);
        this.STONE = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.IO_STONE);
    }

    @Override
    public float getSpawningChance() {
        return 0f; //Nothing spawns
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        return 0x664444;
    }
}

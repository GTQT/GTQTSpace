package zmaster587.advancedRocketry.world.biome;

import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.MapGenBase;
import zmaster587.advancedRocketry.api.AdvancedRocketryBlocks;
import zmaster587.advancedRocketry.dimension.biome.CustomBiome;
import zmaster587.advancedRocketry.world.decoration.MapGenVolcano;

public class BiomeGenBarrenVolcanic extends Biome {

    public static MapGenBase volcano;

    public BiomeGenBarrenVolcanic(BiomeProperties properties) {

        super(properties);

        spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;
        volcano = new MapGenVolcano(800);
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        return 0x332428;
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        return 0x132113;
    }


}

package zmaster587.advancedRocketry.world.biome;

import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import zmaster587.advancedRocketry.dimension.biome.CustomBiome;
import zmaster587.advancedRocketry.world.decoration.MapGenCrater;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

import static meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock.DirtType.MERCURY_DIRT;
import static meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock.StoneType.MERCURY_STONE;

/**
 * 水星陨石坑生物群系 - 典型的水星地貌，充满撞击坑
 */
public class BiomeGenMercuryCratered extends CustomBiome {

    public static MapGenCrater crater;
    
    public BiomeGenMercuryCratered(BiomeProperties properties) {
        super(properties);

        // 清空所有生物生成
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        
        // 禁用装饰
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;
        
        // 设置水星特色的岩石
        this.topBlock = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
            .getState(MERCURY_STONE);
        this.fillerBlock = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
            .getState(MERCURY_STONE);
        this.STONE = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
            .getState(MERCURY_STONE);
            
        // 初始化陨石坑生成器 (使用正确的构造函数签名)
        crater = new MapGenCrater(1200, true); // 大型陨石坑
    }

    @Override
    @Nonnull
    public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType p_76747_1_) {
        return new LinkedList<>();
    }

    @Override
    public float getSpawningChance() {
        return 0f; // 完全禁用生物生成
    }

    @Override
    public int getGrassColorAtPos(@javax.annotation.Nonnull BlockPos pos) {
        // 水星岩石的颜色 - 深灰棕色
        return 0x4a4a4a;
    }

    @Override
    public int getFoliageColorAtPos(@javax.annotation.Nonnull BlockPos pos) {
        // 水星没有植被，返回默认颜色
        return 0x4a4a4a;
    }
}

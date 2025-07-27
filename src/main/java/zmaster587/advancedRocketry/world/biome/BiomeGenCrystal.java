package zmaster587.advancedRocketry.world.biome;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import zmaster587.advancedRocketry.api.AdvancedRocketryBlocks;
import zmaster587.advancedRocketry.world.decoration.MapGenLargeCrystal;
import zmaster587.advancedRocketry.world.gen.WorldGenLargeCrystal;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BiomeGenCrystal extends Biome {

    WorldGenerator crystalGenerator;
    MapGenBase crystalGenBase;

    public BiomeGenCrystal(BiomeProperties properties) {
        super(properties);

        topBlock = Blocks.SNOW.getDefaultState();
        fillerBlock = Blocks.PACKED_ICE.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;

        crystalGenerator = new WorldGenLargeCrystal();
        crystalGenBase = new MapGenLargeCrystal(fillerBlock, AdvancedRocketryBlocks.blockCrystal.getDefaultState());
    }
    @Override
    @Nonnull
    public List<SpawnListEntry> getSpawnableList(EnumCreatureType p_76747_1_) {
        return new LinkedList<>();
    }
    @Override
    public void genTerrainBlocks(World worldIn, Random rand,
                                 @Nonnull ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        super.genTerrainBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal);

        if (x % 16 == 0 && z % 16 == 0)
            crystalGenBase.generate(worldIn, x >> 4, z >> 4, chunkPrimerIn);
    }
}

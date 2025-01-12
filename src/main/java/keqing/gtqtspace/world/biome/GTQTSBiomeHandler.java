package keqing.gtqtspace.world.biome;

import keqing.gtqtspace.api.world.BiomeData;
import keqing.gtqtspace.world.biome.biomes.MarsBiome;
import keqing.gtqtspace.world.biome.biomes.MoonBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class GTQTSBiomeHandler {
    // 创建一个静态实例
    public static final Biome MOON_BIOME = new MoonBiome();
    public static final Biome MARS_BIOME = new MarsBiome(new BiomeData());
    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        IForgeRegistry<Biome> registry = event.getRegistry();

        MOON_BIOME.setRegistryName(new ResourceLocation("GTQTSpace", "gtqts_biome.moon"));
        registry.register(MOON_BIOME);
        BiomeManager.addSpawnBiome(MOON_BIOME);

        //MARS_BIOME.setRegistryName(new ResourceLocation("GTQTSpace", "gtqts_biome.mars"));
        registry.register(MARS_BIOME);
        BiomeManager.addSpawnBiome(MARS_BIOME);
    }
}
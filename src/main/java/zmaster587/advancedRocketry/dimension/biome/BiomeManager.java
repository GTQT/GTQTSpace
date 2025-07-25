package zmaster587.advancedRocketry.dimension.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import zmaster587.advancedRocketry.api.AdvancedRocketryBiomes;
import zmaster587.advancedRocketry.api.Constants;
import zmaster587.advancedRocketry.world.biome.*;

public class BiomeManager {
    public static void registerBiomes(RegistryEvent.Register<Biome> evt) {
        //Biome properties
        AdvancedRocketryBiomes.moonBiome = new BiomeGenMoon(new Biome.BiomeProperties("Regolith Highlands").setRainDisabled().setBaseHeight(1f).setHeightVariation(0.2f).setRainfall(0).setTemperature(0.3f));
        AdvancedRocketryBiomes.marsBiome = new BiomeGenMars(new Biome.BiomeProperties("Mars").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
        AdvancedRocketryBiomes.venusBiome = new BiomeGenVenus(new Biome.BiomeProperties("Venus").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
        AdvancedRocketryBiomes.alienForest = new BiomeGenAlienForest(new Biome.BiomeProperties("Alien Forest").setWaterColor(0x8888FF));
        AdvancedRocketryBiomes.hotDryBiome = new BiomeGenHotDryRock(new Biome.BiomeProperties("Ferric Regolith Wasteland").setRainDisabled().setBaseHeight(1f).setHeightVariation(0.01f).setRainfall(0).setTemperature(0.9f));
        AdvancedRocketryBiomes.spaceBiome = new BiomeGenSpace(new Biome.BiomeProperties("Space").setRainDisabled().setBaseHeight(-2f).setHeightVariation(0f).setTemperature(1f));
        AdvancedRocketryBiomes.stormLandsBiome = new BiomeGenStormland(new Biome.BiomeProperties("Stormland").setBaseHeight(1f).setHeightVariation(0.1f).setRainfall(0.9f).setTemperature(0.9f));
        AdvancedRocketryBiomes.crystalChasms = new BiomeGenCrystal(new Biome.BiomeProperties("Crystal Chasms").setHeightVariation(0.1f).setBaseHeight(1f).setRainfall(0.2f).setTemperature(0.1f));
        AdvancedRocketryBiomes.swampDeepBiome = new BiomeGenDeepSwamp(new Biome.BiomeProperties("Deep Swamp").setBaseHeight(-0.1f).setHeightVariation(0.2f).setRainfall(0.9f).setTemperature(0.9f).setWaterColor(14745518));
        AdvancedRocketryBiomes.marsh = new BiomeGenMarsh(new Biome.BiomeProperties("Marsh").setBaseHeight(-0.4f).setHeightVariation(0f));
        AdvancedRocketryBiomes.oceanSpires = new BiomeGenOceanSpires(new Biome.BiomeProperties("Ocean Spires").setBaseHeight(-0.5f).setHeightVariation(0f));
        AdvancedRocketryBiomes.moonBiomeDark = new BiomeGenMoonDark(new Biome.BiomeProperties("Regolith Lowlands").setRainDisabled().setBaseHeight(0.5f).setHeightVariation(0.01f).setRainfall(0).setTemperature(0.3f));
        AdvancedRocketryBiomes.volcanic = new BiomeGenVolcanic(new Biome.BiomeProperties("Volcanic").setRainDisabled().setBaseHeight(0f).setHeightVariation(0.9f).setRainfall(0).setTemperature(1.0f));
        AdvancedRocketryBiomes.volcanicBarren = new BiomeGenBarrenVolcanic(new Biome.BiomeProperties("Volcanic Lowlands").setRainDisabled().setBaseHeight(0f).setHeightVariation(0.9f).setRainfall(0).setTemperature(1.0f));
        AdvancedRocketryBiomes.iceBiome = new BiomeGenIce(new Biome.BiomeProperties("Ice Biome").setRainDisabled().setBaseHeight(0.5f).setHeightVariation(0.01f).setRainfall(0).setTemperature(0.0f));

        //Biome registry names outside of constructor
        AdvancedRocketryBiomes.moonBiome.setRegistryName(Constants.modId, "moon");
        AdvancedRocketryBiomes.marsBiome.setRegistryName(Constants.modId, "mars");
        AdvancedRocketryBiomes.venusBiome.setRegistryName(Constants.modId, "venus");
        AdvancedRocketryBiomes.alienForest.setRegistryName(Constants.modId, "alien_forest");
        AdvancedRocketryBiomes.hotDryBiome.setRegistryName(Constants.modId, "hotdryrock");
        AdvancedRocketryBiomes.spaceBiome.setRegistryName(Constants.modId, "space");
        AdvancedRocketryBiomes.stormLandsBiome.setRegistryName(Constants.modId, "stormland");
        AdvancedRocketryBiomes.crystalChasms.setRegistryName(Constants.modId, "crystalchasms");
        AdvancedRocketryBiomes.swampDeepBiome.setRegistryName(Constants.modId, "deepswamp");
        AdvancedRocketryBiomes.marsh.setRegistryName(Constants.modId, "marsh");
        AdvancedRocketryBiomes.oceanSpires.setRegistryName(Constants.modId, "oceanspires");
        AdvancedRocketryBiomes.moonBiomeDark.setRegistryName(Constants.modId, "moondark");
        AdvancedRocketryBiomes.volcanic.setRegistryName(Constants.modId, "volcanic");
        AdvancedRocketryBiomes.volcanicBarren.setRegistryName(Constants.modId, "volcanicbarren");
        AdvancedRocketryBiomes.iceBiome.setRegistryName(Constants.modId, "ice");

        //Actual registry
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.moonBiome, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.marsBiome, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.venusBiome, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.alienForest, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.hotDryBiome, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.spaceBiome, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.stormLandsBiome, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.crystalChasms, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.swampDeepBiome, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.marsh, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.oceanSpires, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.moonBiomeDark, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.volcanic, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.volcanicBarren, evt.getRegistry());
        AdvancedRocketryBiomes.instance.registerBiome(AdvancedRocketryBiomes.iceBiome, evt.getRegistry());

        BiomeDictionary.addTypes(AdvancedRocketryBiomes.moonBiome,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.COLD
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.moonBiomeDark,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.COLD
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.marsBiome,
                BiomeDictionary.Type.SANDY,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.COLD
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.venusBiome,
                BiomeDictionary.Type.SANDY,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.HOT
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.alienForest,
                BiomeDictionary.Type.MAGICAL,
                BiomeDictionary.Type.FOREST
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.hotDryBiome,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.HOT
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.volcanic,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.HOT,
                BiomeDictionary.Type.MOUNTAIN
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.volcanicBarren,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.HOT,
                BiomeDictionary.Type.MOUNTAIN
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.iceBiome,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.COLD
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.spaceBiome, BiomeDictionary.Type.VOID);
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.stormLandsBiome,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.WET,
                BiomeDictionary.Type.HOT
        );

        BiomeDictionary.addTypes(AdvancedRocketryBiomes.swampDeepBiome,
                BiomeDictionary.Type.WET,
                BiomeDictionary.Type.HOT
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.marsh,
                BiomeDictionary.Type.WET,
                BiomeDictionary.Type.HOT
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.oceanSpires,
                BiomeDictionary.Type.OCEAN
        );
        BiomeDictionary.addTypes(AdvancedRocketryBiomes.crystalChasms,
                BiomeDictionary.Type.SNOWY,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.COLD
        );
    }
}

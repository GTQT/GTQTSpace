package keqing.gtqtspace.loaders.recipes.categories;

import gregtech.api.fluids.store.FluidStorageKeys;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtspace.GTQTSConfig;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class SpacePumpRecipes {

    /*
    public static Object2ObjectMap<String, FluidStack> GAS_SIPHON_RECIPES = new Object2ObjectOpenHashMap<>();

    public static Object2ObjectMap<Integer, String> PLANET_ID_TO_PLANET_NAME_MAP = new Object2ObjectOpenHashMap<>();

    public static void init() {
        initSpacePumpPlanets();
        gasSiphon();
    }


    private static void gasSiphon() {
        addSpacePumpRecipe(1, 1, Helium3.getFluid(2000));
        addSpacePumpRecipe(1, 2, Argon.getFluid(100));
        addSpacePumpRecipe(1, 3, Neon.getFluid(100));
        addSpacePumpRecipe(1, 4, Hydrogen.getFluid(50));

        addSpacePumpRecipe(2, 1, MarsAir.getFluid(2000));
        addSpacePumpRecipe(2, 2, Oxygen.getFluid(500));
        addSpacePumpRecipe(2, 3, CarbonDioxide.getFluid(500));
        addSpacePumpRecipe(2, 4, Argon.getFluid(100));

        addSpacePumpRecipe(3, 1, SulfuricAcid.getFluid(2000));
        addSpacePumpRecipe(3, 2, HydrogenSulfide.getFluid(2000));
        addSpacePumpRecipe(3, 3, SaltWater.getFluid(2000));
        addSpacePumpRecipe(3, 4, OilGas.getFluid(500));

        addSpacePumpRecipe(4, 1, Hydrogen.getFluid(2000));
        addSpacePumpRecipe(4, 2, Neon.getFluid(500));
        addSpacePumpRecipe(4, 3, Benzene.getFluid(2000));
        addSpacePumpRecipe(4, 4, DistilledWater.getFluid(2000));

        addSpacePumpRecipe(5, 1, Methane.getFluid(500));
        addSpacePumpRecipe(5, 2, Ammonia.getFluid(500));
        addSpacePumpRecipe(5, 3, Ethane.getFluid(500));
        addSpacePumpRecipe(5, 4, Acetylene.getFluid(500));


        addSpacePumpRecipe(6, 1, Radon.getFluid(500));
        addSpacePumpRecipe(6, 2, Deuterium.getFluid(500));
        addSpacePumpRecipe(6, 3, Tritium.getFluid(500));
        addSpacePumpRecipe(6, 4, Oil.getFluid(500));

        addSpacePumpRecipe(7, 1, Helium.getFluid(FluidStorageKeys.LIQUID, 2000));
        addSpacePumpRecipe(7, 2, Hydrogen.getFluid(FluidStorageKeys.LIQUID, 2000));
        addSpacePumpRecipe(7, 3, Nitrogen.getFluid(FluidStorageKeys.LIQUID, 2000));
        addSpacePumpRecipe(7, 4, Radon.getFluid(FluidStorageKeys.LIQUID, 2000));
    }

    private static void initSpacePumpPlanets() {
        for (int i = 0; i < GTQTSConfig.space.planetNames.length; i++) {
            PLANET_ID_TO_PLANET_NAME_MAP.put(i + 1, GTQTSConfig.space.planetNames[i]);
        }
    }

    private static void addSpacePumpRecipe(int planetID, int fluidID, FluidStack fluidStack) {
        try {
            if (planetID < 1 || fluidID < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException("Planet ID or Fluid ID is less than 1! This is not allowed!");
        }

        GAS_SIPHON_RECIPES.put(planetID + "," + fluidID, fluidStack);
    }

    public static String getPlanetNameByID(int id) {
        return PLANET_ID_TO_PLANET_NAME_MAP.get(id);
    }

     */
}

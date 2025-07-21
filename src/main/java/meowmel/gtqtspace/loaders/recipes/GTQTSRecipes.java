package meowmel.gtqtspace.loaders.recipes;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
import static meowmel.gtqtspace.api.unifications.ore.GTQTSOrePrefix.densePlate;

public class GTQTSRecipes {
    public static void registerOrePrefix(){
        densePlate.addProcessingHandler(PropertyKey.INGOT, GTQTSRecipes::processDensePlate);
    }

    private static void processDensePlate(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        if (material.hasFlag(GENERATE_PLATE)) {
            RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                    .input(OrePrefix.plateDense, material,4)
                    .output(orePrefix, material)
                    .explosives(new ItemStack(MetaBlocks.ITNT))
                    .duration(20)
                    .EUt(VA[HV])
                    .buildAndRegister();
        }
    }
}

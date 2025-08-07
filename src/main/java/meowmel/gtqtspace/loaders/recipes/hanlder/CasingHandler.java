package meowmel.gtqtspace.loaders.recipes.hanlder;


import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.MaterialStack;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.ore.OrePrefix.*;
import static meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials.*;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.*;

public class CasingHandler {
    public static void init() {
        registerDustRecipes(TAZAlloy,5);
        registerDustRecipes(SAZAlloy,4);
        registerDustRecipes(IAZAlloy,4);
        registerDustRecipes(CAZAlloy,3);

        registerCasingRecipes(TAZAlloy,5,TAZ_CASING,TAZ_HEAT_VENT);
        registerCasingRecipes(SAZAlloy,4,SAZ_CASING,SAZ_HEAT_VENT);
        registerCasingRecipes(IAZAlloy,4,IAZ_CASING,IAZ_HEAT_VENT);
        registerCasingRecipes(CAZAlloy,3,CAZ_CASING,CAZ_HEAT_VENT);
    }
    public static void registerCasingRecipes(gregtech.api.unification.material.Material material, int tier, GTQTSMultiblockCasing.CasingType casing, GTQTSMultiblockCasing.CasingType pipe) {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Materials.StainlessSteel)
                .input(plate,material,8)
                .input(screw,material,4)
                .circuitMeta(4)
                .outputs(GTQTSMetaBlocks.multiblockCasing.getItemVariant(casing))
                .EUt(VA[tier])
                .duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Materials.StainlessSteel)
                .input(plate,material,4)
                .input(rotor,material,2)
                .input(pipeNormalFluid,Materials.Aluminium,2)
                .circuitMeta(4)
                .outputs(GTQTSMetaBlocks.multiblockCasing.getItemVariant(pipe))
                .EUt(VA[tier])
                .duration(20)
                .buildAndRegister();
    }
    public static void registerDustRecipes(gregtech.api.unification.material.Material material, int tier) {
        int amount = 0;
        // generate builder
        RecipeBuilder<?> builder;

        builder = RecipeMaps.MIXER_RECIPES.recipeBuilder();

        for (MaterialStack component : material.getMaterialComponents()) {
            amount += (int) component.amount;
            builder.input(dust, component.material, (int) component.amount);
        }

        builder.circuitMeta(amount%10)
                .output(dust, material, amount)
                .EUt(VA[tier])
                .duration(10*amount*tier)
                .buildAndRegister();
    }
}

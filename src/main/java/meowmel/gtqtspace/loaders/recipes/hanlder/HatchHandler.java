package meowmel.gtqtspace.loaders.recipes.hanlder;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.common.metatileentities.MetaTileEntities;

import static gregtech.loaders.recipe.CraftingComponent.CIRCUIT;
import static gregtech.loaders.recipe.CraftingComponent.ROBOT_ARM;
import static meowmel.gtqtspace.common.metatileentities.GTQTSMetaTileEntities.SPACE_MAINTENANCE_HATCH;

public class HatchHandler {
    public static void init() {
        ModHandler.addShapedRecipe(true, "space_maintenance_hatch",
                SPACE_MAINTENANCE_HATCH.getStackForm(), "CMC", "RHR", "CMC", 'C',
                CIRCUIT.getIngredient(GTValues.IV), 'M', MetaTileEntities.AUTO_MAINTENANCE_HATCH.getStackForm(), 'R',
                ROBOT_ARM.getIngredient(GTValues.IV), 'H', MetaTileEntities.HULL[GTValues.IV].getStackForm());

    }
}

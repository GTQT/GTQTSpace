package meowmel.gtqtspace.api.multiblock;

import gregtech.api.recipes.RecipeMap;

import static meowmel.gtqtspace.api.recipes.GTQTScoreRecipeMaps.*;

public enum SpaceModulesType {
    MINING_MODULE(MINING_MODULE_RECIPES),
    PUMP_MODULE(PUMPING_MODULE_RECIPES),
    ASSEMBLY_MODULE(ASSEMBLER_MODULE_RECIPES);

    private final RecipeMap<?> recipeMap;

    SpaceModulesType(RecipeMap<?> recipeMap) {
        this.recipeMap = recipeMap;
    }

    public RecipeMap<?> recipeMap() {
        return recipeMap;
    }
}
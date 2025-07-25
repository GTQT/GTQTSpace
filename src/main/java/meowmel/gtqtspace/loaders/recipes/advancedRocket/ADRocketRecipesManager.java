package meowmel.gtqtspace.loaders.recipes.advancedRocket;

import meowmel.gtqtspace.loaders.recipes.advancedRocket.recipes.miseRecipes;
import meowmel.gtqtspace.loaders.recipes.advancedRocket.recipes.rocketRecipes;
import meowmel.gtqtspace.loaders.recipes.advancedRocket.recipes.satelliteRecipes;

public class ADRocketRecipesManager {
    public static void init() {
        rocketRecipes.init();
        miseRecipes.init();
        satelliteRecipes.init();
    }
}

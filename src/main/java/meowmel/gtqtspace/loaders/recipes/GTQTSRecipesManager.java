package meowmel.gtqtspace.loaders.recipes;

import meowmel.gtqtspace.loaders.OreDictionaryLoader;
import meowmel.gtqtspace.loaders.recipes.hanlder.*;
import meowmel.gtqtspace.loaders.recipes.machine.GTQTSSatelliteAssembler;
import meowmel.gtqtspace.loaders.recipes.machine.StarSuvery;

public class GTQTSRecipesManager {
	private GTQTSRecipesManager() {

	}

	public static void load() {
	}

	public static void init() {
		OreDictionaryLoader.init();
		OreRecipeHandler.register();
		SpaceElevatorLoader.init();
		StarSuvery.init();
		HatchHandler.init();
		WirelessEnergyHatches.init();
		GTQTSSatelliteAssembler.init();
		SpaceElevatorrHanlder.init();
		ComponentCasingsRecipes.init();
		CasingHandler.init();
	}
}

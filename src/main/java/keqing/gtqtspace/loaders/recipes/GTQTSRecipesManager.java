package keqing.gtqtspace.loaders.recipes;

import keqing.gtqtspace.loaders.recipes.hanlder.MiningModulerHanlder;
import keqing.gtqtspace.loaders.recipes.machine.GTQTSSatelliteAssembler;
import keqing.gtqtspace.loaders.recipes.machine.StarSuvery;

public class GTQTSRecipesManager {
	private GTQTSRecipesManager() {

	}

	public static void load() {
	}

	public static void init() {
		//SEloader.init();
		StarSuvery.init();
		//SpacePumpRecipes.init();
		//SpaceMiningRecipes.init();
		GTQTSSatelliteAssembler.init();

		MiningModulerHanlder.init();
	}
}

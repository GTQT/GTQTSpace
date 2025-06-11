package keqing.gtqtspace.loaders.recipes;

import keqing.gtqtspace.loaders.recipes.hanlder.SpaceElevatorrHanlder;
import keqing.gtqtspace.loaders.recipes.hanlder.WirelessEnergyHatches;
import keqing.gtqtspace.loaders.recipes.machine.GTQTSSatelliteAssembler;
import keqing.gtqtspace.loaders.recipes.hanlder.SpaceElevatorLoader;
import keqing.gtqtspace.loaders.recipes.machine.StarSuvery;

public class GTQTSRecipesManager {
	private GTQTSRecipesManager() {

	}

	public static void load() {
	}

	public static void init() {
		SpaceElevatorLoader.init();
		StarSuvery.init();
		WirelessEnergyHatches.init();
		GTQTSSatelliteAssembler.init();
		SpaceElevatorrHanlder.init();
	}
}

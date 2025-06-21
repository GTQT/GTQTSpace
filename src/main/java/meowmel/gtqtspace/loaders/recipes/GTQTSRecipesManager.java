package meowmel.gtqtspace.loaders.recipes;

import meowmel.gtqtspace.loaders.recipes.hanlder.SpaceElevatorrHanlder;
import meowmel.gtqtspace.loaders.recipes.hanlder.WirelessEnergyHatches;
import meowmel.gtqtspace.loaders.recipes.machine.GTQTSSatelliteAssembler;
import meowmel.gtqtspace.loaders.recipes.hanlder.SpaceElevatorLoader;
import meowmel.gtqtspace.loaders.recipes.machine.StarSuvery;

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

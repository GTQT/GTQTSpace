package meowmel.gtqtspace.api.unifications;

import gregtech.api.unification.material.Material;
import meowmel.gtqtspace.api.unifications.materials.FirstDegreeMaterials;

public class GTQTSpaceMaterials {
	public static Material MeteoricIron;

	public static Material Desh;
	public static Material MoonStone;
	public static Material MarsStone;
	public static Material VenusStone;
	public GTQTSpaceMaterials() {
	}

	public static void register() {

		FirstDegreeMaterials.register();

	}
}

package meowmel.gtqtspace.api.unifications;

import gregtech.api.unification.material.Material;
import meowmel.gtqtspace.api.unifications.materials.FirstDegreeMaterials;
import meowmel.gtqtspace.api.unifications.materials.SecondDegreeMaterials;

public class GTQTSpaceMaterials {
	public static Material MeteoricIron;
	public static Material Desh;

	public static Material TAZAlloy;
	public static Material SAZAlloy;
	public static Material IAZAlloy;
	public static Material CAZAlloy;

	public GTQTSpaceMaterials() {
	}

	public static void register() {

		FirstDegreeMaterials.register();
		SecondDegreeMaterials.register();

	}
}

package meowmel.gtqtspace.api.unifications;

import gregtech.api.unification.material.Material;
import meowmel.gtqtspace.api.unifications.materials.ElementMaterials;
import meowmel.gtqtspace.api.unifications.materials.FirstDegreeMaterials;
import meowmel.gtqtspace.api.unifications.materials.MaterialPropertyAddition;
import meowmel.gtqtspace.api.unifications.materials.SecondDegreeMaterials;

public class GTQTSpaceMaterials {
	public static Material MeteoricIron;
	public static Material Desh;
	public static Material Columbite;
	public static Material Gallite;
	public static Material Dilithium;
	public static Material SbVOx;

	public static Material MoonStone;
	public static Material MarsStone;
	public static Material VenusStone;

	public static Material TAZAlloy;
	public static Material SAZAlloy;
	public static Material IAZAlloy;
	public static Material CAZAlloy;

	public GTQTSpaceMaterials() {
	}

	public static void register() {
		ElementMaterials.register();
		FirstDegreeMaterials.register();
		SecondDegreeMaterials.register();
		MaterialPropertyAddition.init();
	}
}

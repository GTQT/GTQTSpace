package meowmel.gtqtspace.client.textures;

import codechicken.lib.texture.TextureUtils;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraft.client.renderer.texture.TextureMap;

public class GTQTSTextures {

	public static SimpleOverlayRenderer SPACE_ELEVATOR_CASING;
	public static SimpleOverlayRenderer ELEVATOR_CASING;

	//补充工业外壳
	public static SimpleOverlayRenderer TAZ_CASING;
	public static SimpleOverlayRenderer SAZ_CASING;
	public static SimpleOverlayRenderer IAZ_CASING;
	public static SimpleOverlayRenderer CAZ_CASING;

	//Overlay
	public static SimpleOverlayRenderer PUMP_MODULE_OVERLAY;
	public static SimpleOverlayRenderer MINING_MODULE_OVERLAY;
	public static SimpleOverlayRenderer ASSEMBLER_MODULE_OVERLAY;

	public static void init() {
		SPACE_ELEVATOR_CASING = new SimpleOverlayRenderer("multiblock/casings/support_structure_side");
		ELEVATOR_CASING = new SimpleOverlayRenderer("multiblock/casings/elevator/elevator_base");

		TAZ_CASING= new SimpleOverlayRenderer("multiblock/casings/taz_casing");
		SAZ_CASING= new SimpleOverlayRenderer("multiblock/casings/saz_casing");
		IAZ_CASING= new SimpleOverlayRenderer("multiblock/casings/iaz_casing");
		CAZ_CASING = new SimpleOverlayRenderer("multiblock/casings/caz_casing");

		//Overlay
		PUMP_MODULE_OVERLAY = new SimpleOverlayRenderer("overlay/elevator/pump");
		MINING_MODULE_OVERLAY = new SimpleOverlayRenderer("overlay/elevator/mining");
		ASSEMBLER_MODULE_OVERLAY = new SimpleOverlayRenderer("overlay/elevator/assembler");
	}

	public static void register(TextureMap textureMap) {

	}

	public static void preInit() {
		TextureUtils.addIconRegister(GTQTSTextures::register);
	}
}

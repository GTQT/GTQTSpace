package meowmel.gtqtspace.api.unifications.materials;

import gregtech.api.unification.material.Material;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_SMALL_GEAR;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.unification.material.info.MaterialIconSet.ROUGH;
import static gregtech.api.util.GTUtility.gregtechId;
import static meowmel.gtqtspace.api.GTQTSValue.gtqtspaceId;

public class FirstDegreeMaterials {
	public FirstDegreeMaterials() {
	}

	private static int startId = 0;
	private static final int END_ID = startId + 300;

	private static int getMaterialsId() {
		if (startId < END_ID) {
			return startId++;
		}
		throw new ArrayIndexOutOfBoundsException();
	}

	public static void register() {
		//陨铁
		GTQTSpaceMaterials.MeteoricIron = new Material.Builder(getMaterialsId(), gtqtspaceId("meteoric_iron"))
				.ingot().dust().ore()
				.color(0x8B6914).iconSet(METALLIC)
				.build()
				.setTooltips("来自地外的陨石，记录着宇宙的信息");

		//戴斯
		GTQTSpaceMaterials.Desh = new Material.Builder(getMaterialsId(), gtqtspaceId("desh"))
				.ingot().dust().ore()
				.color(0xFF7256).iconSet(METALLIC)
				.build()
				.setTooltips("无法通过仪器检测成分！");

		//铌钽铁矿 columbite
		GTQTSpaceMaterials.Columbite = new Material.Builder(getMaterialsId(), gtqtspaceId("columbite"))
				.ingot().dust().ore()
				.components(Iron, 1, Tantalum, 1,Niobium,1,Manganese,1,Oxygen,6)
				.color(0xFF7256)
				.iconSet(METALLIC)
				.build();

		//灰镓矿 gallite CuGaS2
		GTQTSpaceMaterials.Gallite = new Material.Builder(getMaterialsId(), gtqtspaceId("gallite"))
				.ingot().dust().ore()
				.components(Copper, 1, Gallium, 1, Selenium, 2)
				.color(0xFF7256)
				.iconSet(METALLIC)
				.build();

		startId=100;
		//月球岩石
		GTQTSpaceMaterials.MoonStone = new Material.Builder(getMaterialsId(), gtqtspaceId("moon_stone"))
				.dust()
				.color(0xE0E0E0).iconSet(ROUGH)
				.build();

		//火星岩石
		GTQTSpaceMaterials.MarsStone = new Material.Builder(getMaterialsId(), gtqtspaceId("mars_stone"))
				.dust()
				.color(0xE0E0E0).iconSet(ROUGH)
				.build();

		//金星岩石
		GTQTSpaceMaterials.VenusStone = new Material.Builder(getMaterialsId(), gtqtspaceId("venus_stone"))
				.dust()
				.color(0xE0E0E0).iconSet(ROUGH)
				.build();
	}
}

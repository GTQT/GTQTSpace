package meowmel.gtqtspace.api.multiblock;

import meowmel.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.client.resources.I18n;

public enum SatelliteSeniorUpdates {
	EMPTY (""),
	VEIN_SENSOR("metaitem.satelliteprimaryfunction1.name"),
	ATMOSPHERE_SENSOR("metaitem.satelliteprimaryfunction2.name"),
	ORBIT_SENSOR("metaitem.satelliteprimaryfunction3.name"),
	DEEP_SKY_SENSOR("metaitem.satelliteprimaryfunction4.name"),
	COSMIC_PARTICLES_SENSOR("metaitem.satelliteprimaryfunction5.name");

	final String translationKey;

	SatelliteSeniorUpdates(String unlocalizedName) {
		this.translationKey = unlocalizedName;
	}

	public static SatelliteSeniorUpdates getSeniorUpdateFromID(int id) {
		return SatelliteSeniorUpdates.values()[id - 1];
	}

	public String getName() {
		return I18n.format(translationKey);
	}

	public int getID() {
		return this.ordinal() + 1;
	}
}

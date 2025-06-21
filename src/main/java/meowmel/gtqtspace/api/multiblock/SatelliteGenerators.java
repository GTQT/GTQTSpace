package meowmel.gtqtspace.api.multiblock;

import meowmel.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.client.resources.I18n;

public enum SatelliteGenerators {
	EMPTY (""),
	BASIC_CHEMICAL_ENGINE("metaitem.combustionengine.name"),
	ADVANCED_CHEMICAL_ENGINE("metaitem.advcombustionengine.name");

	final String translationKey;

	SatelliteGenerators(String unlocalizedName) {
		this.translationKey = unlocalizedName;
	}

	public static SatelliteGenerators getGeneratorFromID(int id) {
		return SatelliteGenerators.values()[id - 1];
	}

	public String getName() {
		return I18n.format(translationKey);
	}

	public int getID() {
		return this.ordinal() + 1;
	}
}

package meowmel.gtqtspace.api.unifications;

import gregtech.api.unification.material.properties.OreProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.ConfigHolder;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import meowmel.gtqtspace.api.unifications.ore.GTQTSOrePrefix;

import static gregtech.api.unification.material.Materials.*;
import static meowmel.gtqtspace.api.unifications.GTQTSMaterialFlags.GENERATE_DENSE_PLATE;
import static meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials.*;

public class OrePrefixAdditions {
    public static void init() {
        if (ConfigHolder.worldgen.allUniqueStoneTypes) {
            GTQTSOrePrefix.oreMoon.addSecondaryMaterial(new MaterialStack(MoonStone, 144));
            GTQTSOrePrefix.oreMars.addSecondaryMaterial(new MaterialStack(MarsStone, 144));
            GTQTSOrePrefix.oreVenus.addSecondaryMaterial(new MaterialStack(VenusStone, 144));
        }
        MetaItems.addOrePrefix(GTQTSOrePrefix.densePlate);


    }
}

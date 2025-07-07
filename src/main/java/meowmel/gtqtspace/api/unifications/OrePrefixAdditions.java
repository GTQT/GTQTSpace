package meowmel.gtqtspace.api.unifications;

import gregtech.common.items.MetaItems;
import meowmel.gtqtspace.api.unifications.ore.GTQTSOrePrefix;

public class OrePrefixAdditions {
    public static void init() {
        MetaItems.addOrePrefix(GTQTSOrePrefix.oreMoon);
        MetaItems.addOrePrefix(GTQTSOrePrefix.oreMars);
        MetaItems.addOrePrefix(GTQTSOrePrefix.oreVenus);
    }
}

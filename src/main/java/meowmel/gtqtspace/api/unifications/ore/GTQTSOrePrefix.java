package meowmel.gtqtspace.api.unifications.ore;

import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.unification.ore.OrePrefix.Conditions.hasOreProperty;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;

public class GTQTSOrePrefix {
    public static final OrePrefix oreMoon = new OrePrefix("oreMoon", -1, null, MaterialIconType.ore,
            ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreMars = new OrePrefix("oreMars", -1, null, MaterialIconType.ore,
            ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreVenus = new OrePrefix("oreVenus", -1, null, MaterialIconType.ore,
            ENABLE_UNIFICATION, hasOreProperty);
}

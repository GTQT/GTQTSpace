package meowmel.gtqtspace.api.unifications.ore;

import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import meowmel.gtqtspace.api.unifications.GTQTSMaterialIconType;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasOreProperty;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static meowmel.gtqtspace.api.unifications.GTQTSMaterialFlags.GENERATE_DENSE_PLATE;

public class GTQTSOrePrefix {
    public static final OrePrefix oreMoon = new OrePrefix("oreMoon", -1, null, MaterialIconType.ore,
            ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreMars = new OrePrefix("oreMars", -1, null, MaterialIconType.ore,
            ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreVenus = new OrePrefix("oreVenus", -1, null, MaterialIconType.ore,
            ENABLE_UNIFICATION, hasOreProperty);

    //超致密板
    public static final OrePrefix densePlate = new OrePrefix("densePlate", M * 36, null, GTQTSMaterialIconType.densePlate, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_DENSE_PLATE));
}

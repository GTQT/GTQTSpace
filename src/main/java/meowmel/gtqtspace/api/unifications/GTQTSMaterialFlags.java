package meowmel.gtqtspace.api.unifications;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;

public class GTQTSMaterialFlags {
    public static final MaterialFlag GENERATE_DENSE_PLATE = new MaterialFlag.Builder("generate_dense_plate")
            .requireProps(PropertyKey.INGOT)
            .build();
}

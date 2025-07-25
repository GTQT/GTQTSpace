package meowmel.gtqtspace.api.unifications.materials;

import gregtech.api.unification.material.Material;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;

import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static meowmel.gtqtspace.api.GTQTSValue.gtqtspaceId;
import static meowmel.gtqtspace.api.unifications.GTQTSElements.Dilithium;

public class ElementMaterials {
    //Elements文件中注册的元素符号在此处注册单质

    private static int startId = 0;
    private static final int END_ID = startId + 200;

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        //双锂 Dilithium
        GTQTSpaceMaterials.Dilithium = new Material.Builder(getMaterialsId(), gtqtspaceId("dilithium"))
                .gem()
                .element(Dilithium)
                .color(0xFF7256)
                .iconSet(METALLIC)
                .build();
    }
}

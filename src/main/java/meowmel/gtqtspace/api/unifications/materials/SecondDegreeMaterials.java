package meowmel.gtqtspace.api.unifications.materials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static meowmel.gtqtspace.api.GTQTSValue.gtqtspaceId;

public class SecondDegreeMaterials {
    private static int startId = 500;
    private static final int END_ID = startId + 500;
    public SecondDegreeMaterials() {
    }

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        // TAZ 合金 (高熔点钛锆基合金)
        GTQTSpaceMaterials.TAZAlloy = new Material.Builder(getMaterialsId(), gtqtspaceId("taz_alloy"))
                .ingot()
                .fluid()
                .color(0x5D8AA8)  // 航空蓝
                .iconSet(BRIGHT)
                .components(
                        Titanium, 6,    // 基体 (60%)
                        Tungsten, 2,    // 强化耐热性 (20%)
                        Zirconium, 1,  // 耐腐蚀 (10%)
                        Rhenium, 1      // 高温强度 (10%)
                )
                .flags(EXT_METAL, GENERATE_FRAME, GENERATE_GEAR)
                .blast(b -> b
                        .temp(5200, BlastProperty.GasTier.HIGHER)
                        .blastStats(VA[LuV], 42 * SECOND)
                        .vacuumStats(VA[IV], 18 * SECOND))
                .build()
                .setTooltips("高熔点钛锆基合金");

        // SAZ 合金 (超导铌基合金)
        GTQTSpaceMaterials.SAZAlloy = new Material.Builder(getMaterialsId(), gtqtspaceId("saz_alloy"))
                .ingot()
                .fluid()
                .color(0x87CEEB)  // 天蓝色
                .iconSet(SHINY)
                .components(
                        Niobium, 4,     // 超导基体 (40%)
                        Tin, 3,         // 超导增强 (30%)
                        Zirconium, 2,  // 结构稳定 (20%)
                        Vanadium, 1    // 临界场提升 (10%)
                )
                .flags(EXT_METAL, GENERATE_FINE_WIRE)
                .blast(b -> b
                        .temp(3200, BlastProperty.GasTier.MID)
                        .blastStats(VA[IV], 65 * SECOND)
                        .vacuumStats(VA[EV], 25 * SECOND))
                .build()
                .setTooltips("超导铌基合金");

        // IAZ 合金 (辐照抗性合金)
        GTQTSpaceMaterials.IAZAlloy = new Material.Builder(getMaterialsId(), gtqtspaceId("iaz_alloy"))
                .ingot()
                .fluid()
                .color(0x228B22)  // 森林绿
                .iconSet(METALLIC)
                .components(
                        Iron, 5,        // 基体 (50%)
                        Chrome, 3,    // 抗腐蚀 (30%)
                        Zirconium, 1,   // 中子吸收 (10%)
                        Yttrium, 1      // 缺陷修复 (10%)
                )
                .flags(EXT_METAL, GENERATE_ROTOR)
                .blast(b -> b
                        .temp(4100, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[IV], 58 * SECOND)
                        .vacuumStats(VA[EV], 22 * SECOND))
                .build()
                .setTooltips("辐照抗性合金");

        // CAZ 合金 (耐腐蚀锆合金)
        GTQTSpaceMaterials.CAZAlloy = new Material.Builder(getMaterialsId(), gtqtspaceId("caz_alloy"))
                .ingot()
                .fluid()
                .color(0xF0E68C)  // 卡其色
                .iconSet(DULL)
                .components(
                        Zirconium, 8,  // 基体 (80%)
                        Hafnium, 1,    // 耐酸强化 (10%)
                        Palladium, 1   // 钝化催化 (10%)
                )
                .flags(NO_SMASHING, GENERATE_FOIL)
                .blast(b -> b
                        .temp(2900, BlastProperty.GasTier.LOW)
                        .blastStats(VA[EV], 75 * SECOND)
                        .vacuumStats(VA[HV], 30 * SECOND))
                .build()
                .setTooltips("耐腐蚀锆合金");
    }
}

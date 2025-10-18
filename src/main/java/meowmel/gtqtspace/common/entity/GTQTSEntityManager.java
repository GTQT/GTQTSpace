package meowmel.gtqtspace.common.entity;

import meowmel.gtqtspace.GTQTSpace;
import meowmel.gtqtspace.common.entity.weapon.missile.EntityClusterMissile;
import meowmel.gtqtspace.common.entity.weapon.missile.EntityExplosiveMissile;
import meowmel.gtqtspace.common.entity.weapon.missile.EntityIncendiaryMissile;
import meowmel.gtqtspace.common.entity.weapon.missile.EntityMachineGunBullet;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import static meowmel.gtqtspace.GTQTSpace.MODID;

public class GTQTSEntityManager {
    public static void init() {
        // 注册实体
        EntityRegistry.registerModEntity(
                new ResourceLocation(MODID, "custom_vehicle"),
                EntityCustomVehicle.class,
                "CustomVehicle",
                1,  // 实体ID
                GTQTSpace.instance,
                64,  // 追踪范围
                1,   // 更新频率
                true // 发送速度更新
        );

        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "explosivemissile"),
                EntityExplosiveMissile.class, "ExplosiveMissile", 10, MODID, 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "incendiarymissile"),
                EntityIncendiaryMissile.class, "IncendiaryMissile", 11, MODID, 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "machinegunbullet"),
                EntityMachineGunBullet.class, "MachineGunBullet", 12, MODID, 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "clustermissile"),
                EntityClusterMissile.class, "ClusterMissile", 13, MODID, 64, 1, true);
    }
}

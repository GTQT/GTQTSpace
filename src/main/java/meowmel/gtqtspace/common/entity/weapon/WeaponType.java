package meowmel.gtqtspace.common.entity.weapon;

import meowmel.gtqtspace.common.entity.weapon.missile.*;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.function.BiFunction;

public enum WeaponType {
    //机炮
    MACHINE_GUN("machine_gun", "机炮",
            EntityMachineGunBullet::new),
    //高爆弹
    EXPLOSIVE_MISSILE("explosive_missile", "高爆弹",
            EntityExplosiveMissile::new),
    //火箭弹
    INCENDIARY_MISSILE("incendiary_missile", "火箭弹",
            EntityIncendiaryMissile::new),
    //集束导弹
    CLUSTER_MISSILE("cluster_missile", "集束导弹",
            EntityClusterMissile::new);
    /*
    ARMOR_PIERCING_MISSILE("armor_piercing_missile", "Armor Piercing Missile",
            (world, launcher) -> new EntityArmorPiercingMissile(world, launcher)),

    EMP_MISSILE("emp_missile", "EMP Missile",
            (world, launcher) -> new EntityEMPMissile(world, launcher)),



     */

    private final String id;
    private final String displayName;
    private final BiFunction<World, Entity, EntityMissileBase> factory;

    WeaponType(String id, String displayName, BiFunction<World, Entity, EntityMissileBase> factory) {
        this.id = id;
        this.displayName = displayName;
        this.factory = factory;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public EntityMissileBase createMissile(World world, Entity launcher) {
        return factory.apply(world, launcher);
    }

    public static WeaponType byId(String id) {
        for (WeaponType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return MACHINE_GUN;
    }

    public static WeaponType[] getWeapons() {
        return values();
    }
}
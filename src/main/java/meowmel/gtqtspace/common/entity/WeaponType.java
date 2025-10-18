package meowmel.gtqtspace.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.function.BiFunction;

public enum WeaponType {
    EXPLOSIVE_MISSILE("explosive_missile", "Explosive Missile",
            EntityExplosiveMissile::new),

    INCENDIARY_MISSILE("incendiary_missile", "Incendiary Missile",
            EntityIncendiaryMissile::new);

    /*
    ARMOR_PIERCING_MISSILE("armor_piercing_missile", "Armor Piercing Missile",
            (world, launcher) -> new EntityArmorPiercingMissile(world, launcher)),

    EMP_MISSILE("emp_missile", "EMP Missile",
            (world, launcher) -> new EntityEMPMissile(world, launcher)),

    CLUSTER_MISSILE("cluster_missile", "Cluster Missile",
            (world, launcher) -> new EntityClusterMissile(world, launcher));

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
        return EXPLOSIVE_MISSILE; // 默认返回爆炸导弹
    }

    public static WeaponType[] getWeapons() {
        return values();
    }
}
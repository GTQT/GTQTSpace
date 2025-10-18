package meowmel.gtqtspace.common.entity.weapon;


public class WeaponConfig {
    private final int maxAmmo;
    private final int cooldownTicks;
    private final float damage;
    private final float speed;

    public WeaponConfig(int maxAmmo, int cooldownTicks, float damage, float speed) {
        this.maxAmmo = maxAmmo;
        this.cooldownTicks = cooldownTicks;
        this.damage = damage;
        this.speed = speed;
    }

    // Getters
    public int getMaxAmmo() { return maxAmmo; }
    public int getCooldownTicks() { return cooldownTicks; }
    public float getDamage() { return damage; }
    public float getSpeed() { return speed; }

    // 预设配置
    public static WeaponConfig getConfig(WeaponType weaponType) {
        switch (weaponType) {
            case MACHINE_GUN:
                return new WeaponConfig(200, 1, 0.5f, 1f);
            case EXPLOSIVE_MISSILE:
                return new WeaponConfig(10, 20, 5.0f, 2.0f);
            case INCENDIARY_MISSILE:
                return new WeaponConfig(8, 25, 3.0f, 1.8f);
            case CLUSTER_MISSILE:
                return new WeaponConfig(2, 100, 5.0f, 2.0f);

            /*
                case ARMOR_PIERCING_MISSILE:
                return new WeaponConfig(6, 30, 8.0f, 2.5f);
            case EMP_MISSILE:
                return new WeaponConfig(4, 40, 1.0f, 1.5f);
            case CLUSTER_MISSILE:
                return new WeaponConfig(3, 60, 4.0f, 1.6f);
                */
            default:
                return new WeaponConfig(10, 20, 5.0f, 2.0f);
        }
    }
}
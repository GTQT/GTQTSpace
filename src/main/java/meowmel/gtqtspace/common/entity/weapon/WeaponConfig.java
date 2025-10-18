package meowmel.gtqtspace.common.entity.weapon;


public class WeaponConfig {
    private final int maxAmmo;
    private final int cooldownTicks;

    public WeaponConfig(int maxAmmo, int cooldownTicks) {
        this.maxAmmo = maxAmmo;
        this.cooldownTicks = cooldownTicks;
    }

    // Getters
    public int getMaxAmmo() { return maxAmmo; }
    public int getCooldownTicks() { return cooldownTicks; }

    // 预设配置
    public static WeaponConfig getConfig(WeaponType weaponType) {
        switch (weaponType) {
            case MACHINE_GUN:
                return new WeaponConfig(200, 1);
            case EXPLOSIVE_MISSILE:
                return new WeaponConfig(10, 20);
            case INCENDIARY_MISSILE:
                return new WeaponConfig(8, 25);
            case CLUSTER_MISSILE:
                return new WeaponConfig(2, 100);
            case GUIDED_MISSILE:
                return new WeaponConfig(5, 20);
            case ARMOR_PIERCING_MISSILE:
                return new WeaponConfig(4, 30);
            case NUCLEAR_MISSILE:
                return new WeaponConfig(1, 100);

            /*
                case ARMOR_PIERCING_MISSILE:
                return new WeaponConfig(6, 30, 8.0f, 2.5f);
            case EMP_MISSILE:
                return new WeaponConfig(4, 40, 1.0f, 1.5f);
            case CLUSTER_MISSILE:
                return new WeaponConfig(3, 60, 4.0f, 1.6f);
                */
            default:
                return new WeaponConfig(10, 20);
        }
    }
}
package zmaster587.advancedRocketry.api.fuel;

import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FuelRegistry {

    public static final FuelRegistry instance = new FuelRegistry();

    /**
     * @param type       {@link FuelType} to register with
     * @param fluid      fluid to register
     * @param multiplier amount of fuel points 1mb is worth
     */
    public void registerFuel(@Nonnull FuelType type, Fluid fluid, float multiplier) {
        FuelEntry entry = new FuelEntry(fluid, multiplier);
        type.addFuel(entry);
    }

    /**
     * @param type  {@link FuelType} registry to check in
     * @param fluid Fluid to check
     * @return true if the fluid has been registered as {@link FuelType} fuel
     */
    public boolean isFuel(@Nullable FuelType type, @Nullable Fluid fluid) {
        if (type == null || fluid == null)
            return false;
        return type.isFuel(fluid);
    }

    /**
     * @param type  {@link FuelType} registry to check in
     * @param fluid fluid to check against
     * @return the amount of fuel points one millibucket of this fluid is worth
     */
    public float getMultiplier(@Nullable FuelType type, @Nullable Fluid fluid) {
        if (type == null || fluid == null)
            return 0;
        return type.getMultiplier(fluid);
    }

    public enum FuelType {
        LIQUID_MONOPROPELLANT,        //Used in ground to space rockets
        LIQUID_BIPROPELLANT,
        LIQUID_OXIDIZER,
        NUCLEAR_WORKING_FLUID,
        ION,        //Used in satellites
        WARP,        //Used in interstellar missions
        IMPULSE;    //Used in interplanetary missions

        // 使用ConcurrentHashMap存储燃料条目，Key为流体对象
        private final ConcurrentHashMap<Fluid, FuelEntry> fuelMap = new ConcurrentHashMap<>();
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        /**
         * @param entry FuelEntry to add
         */
        public void addFuel(@Nonnull FuelEntry entry) {
            lock.writeLock().lock();
            try {
                // 防止重复注册同一流体
                if (fuelMap.containsKey(entry.fuel)) {
                    return; // 或选择覆盖：fuelMap.put(entry.fuel, entry);
                }
                entry.type = this;
                fuelMap.put(entry.fuel, entry);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * @return true if the passed fluid is a registered fuel
         */
        public boolean isFuel(@Nullable Fluid fluid) {
            if (fluid == null) return false;
            lock.readLock().lock();
            try {
                return fuelMap.containsKey(fluid);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * @return Fuel multiplier for the fluid, 0 if not registered
         */
        public float getMultiplier(Fluid fluid) {
            if (fluid == null) return 0;
            lock.readLock().lock();
            try {
                FuelEntry entry = fuelMap.get(fluid);
                return entry != null ? entry.multiplier : 0;
            } finally {
                lock.readLock().unlock();
            }
        }
    }

    private static class FuelEntry {

        private final Fluid fuel;
        private final float multiplier;
        private FuelType type;

        /**
         * @param fuel       ItemStack or Fluid to register as fuel
         * @param multiplier how many fuel points one unit of this object is worth
         */
        public FuelEntry(@Nonnull Fluid fuel, float multiplier) {
            this.fuel = fuel;
            this.multiplier = multiplier;
        }

        /**
         * 强化空指针检查
         */
        public boolean fuelMatches(@Nullable Fluid fluid) {
            return fluid != null && fluid.equals(this.fuel);
        }
    }
}
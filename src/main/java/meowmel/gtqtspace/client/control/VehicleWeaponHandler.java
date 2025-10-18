package meowmel.gtqtspace.client.control;

import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import meowmel.gtqtspace.common.entity.WeaponConfig;
import meowmel.gtqtspace.common.entity.WeaponType;
import meowmel.gtqtspace.common.network.MessageLaunchMissile;
import meowmel.gtqtspace.common.network.PacketDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashMap;
import java.util.Map;

public class VehicleWeaponHandler {

    // 武器状态
    private static WeaponType selectedWeapon = WeaponType.EXPLOSIVE_MISSILE;
    private static boolean weaponEnabled = true;

    // 冷却和连发系统
    private static final Map<WeaponType, Long> lastFireTimes = new HashMap<>();
    private static boolean lastFiring = false;
    private static boolean lastRightClick = false; // 添加右键状态记录
    private static boolean burstMode = false;
    private static int burstCount = 0;
    private static final int MAX_BURST_COUNT = 3;
    private static long lastBurstTime = 0;
    private static final long BURST_DELAY = 50;

    // 添加连发模式专用的冷却时间记录
    private static long burstSequenceStartTime = 0;
    private static boolean inBurstSequence = false;

    static {
        for (WeaponType weapon : WeaponType.values()) {
            lastFireTimes.put(weapon, 0L);
        }
    }

    /**
     * 处理武器输入
     */
    public static void handleWeaponInput(EntityCustomVehicle vehicle, EntityPlayer player) {
        if (vehicle == null || player == null || !weaponEnabled) return;

        // 检查鼠标状态
        boolean firing = Mouse.isButtonDown(0);
        boolean rightClick = Mouse.isButtonDown(1);

        // 处理连发模式切换（右键点击瞬间）
        if (rightClick && !lastRightClick) {
            burstMode = !burstMode;
            burstCount = 0; // 重置连发计数
            inBurstSequence = false; // 重置连发序列状态
            System.out.println("Burst mode: " + (burstMode ? "ON" : "OFF"));
        }
        lastRightClick = rightClick;

        long currentTime = System.currentTimeMillis();

        // 检查连发序列是否已经结束冷却
        boolean canStartNewBurst = !inBurstSequence || getRemainingBurstCooldown() <= 0;

        // 单发模式的冷却检查
        boolean canFireSingle = getRemainingCooldown(selectedWeapon) <= 0;

        // 处理连发模式中的连续发射
        if (burstMode && burstCount > 0 && currentTime - lastBurstTime >= BURST_DELAY) {
            if (vehicle.canLaunchMissile(selectedWeapon)) {
                launchWeapon(vehicle, player, selectedWeapon);
                burstCount--;
                lastBurstTime = currentTime;

                // 如果连发结束，设置连发序列冷却
                if (burstCount == 0) {
                    lastFireTimes.put(selectedWeapon, currentTime);
                    inBurstSequence = false; // 结束连发序列
                }
            }
        }

        // 单发模式或连发模式开始
        if (firing && !lastFiring && burstCount == 0 && vehicle.canLaunchMissile(selectedWeapon)) {
            if (burstMode) {
                // 连发模式：检查是否可以开始新的连发序列
                if (canStartNewBurst) {
                    burstCount = MAX_BURST_COUNT;
                    inBurstSequence = true;
                    burstSequenceStartTime = currentTime;
                    launchWeapon(vehicle, player, selectedWeapon);
                    burstCount--;
                    lastBurstTime = currentTime;
                }
            } else {
                // 单发模式：检查冷却时间并发射一次
                if (canFireSingle) {
                    launchWeapon(vehicle, player, selectedWeapon);
                    lastFireTimes.put(selectedWeapon, currentTime);
                }
            }
        }

        // 处理武器切换
        handleWeaponSwitch();
        handleNumberKeySwitch();

        lastFiring = firing;
    }

    /**
     * 发射武器
     */
    private static void launchWeapon(EntityCustomVehicle vehicle, EntityPlayer player, WeaponType weaponType) {
        // 发送发射导弹的网络包，包含武器类型ID
        PacketDispatcher.sendToServer(new MessageLaunchMissile(vehicle.getEntityId(), weaponType.getId()));

        System.out.println("Launching " + weaponType.getDisplayName() + " from vehicle ID: " + vehicle.getEntityId() +
                ", Burst mode: " + burstMode + ", Remaining burst: " + burstCount +
                ", In burst sequence: " + inBurstSequence);
    }

    /**
     * 处理武器切换（鼠标滚轮）
     */
    private static void handleWeaponSwitch() {
        int wheel = Mouse.getDWheel();
        if (wheel != 0) {
            WeaponType[] weapons = WeaponType.values();
            int currentIndex = selectedWeapon.ordinal();

            if (wheel > 0) {
                // 向上滚动：前一个武器
                currentIndex = (currentIndex - 1 + weapons.length) % weapons.length;
            } else {
                // 向下滚动：下一个武器
                currentIndex = (currentIndex + 1) % weapons.length;
            }

            selectedWeapon = weapons[currentIndex];
            System.out.println("Weapon switched to: " + selectedWeapon.getDisplayName());
        }
    }

    /**
     * 处理数字键快速切换
     */
    private static void handleNumberKeySwitch() {
        WeaponType[] weapons = WeaponType.values();
        for (int i = 0; i < Math.min(weapons.length, 9); i++) {
            if (Keyboard.isKeyDown(Keyboard.KEY_1 + i)) {
                if (selectedWeapon != weapons[i]) {
                    selectedWeapon = weapons[i];
                    System.out.println("Quick switch to: " + selectedWeapon.getDisplayName());
                }
                break;
            }
        }
    }

    /**
     * 获取指定武器的剩余冷却时间
     */
    public static long getRemainingCooldown(WeaponType weaponType) {
        long currentTime = System.currentTimeMillis();
        long lastFireTime = lastFireTimes.getOrDefault(weaponType, 0L);
        long cooldown = WeaponConfig.getConfig(weaponType).getCooldownTicks() * 50L; // ticks to milliseconds
        return Math.max(0, cooldown - (currentTime - lastFireTime));
    }

    /**
     * 获取连发序列的剩余冷却时间
     */
    public static long getRemainingBurstCooldown() {
        if (!inBurstSequence) return 0;

        long currentTime = System.currentTimeMillis();
        long cooldown = WeaponConfig.getConfig(selectedWeapon).getCooldownTicks() * 50L;
        return Math.max(0, cooldown - (currentTime - burstSequenceStartTime));
    }

    /**
     * 获取当前选中武器的冷却进度
     */
    public static float getCooldownProgress() {
        // 如果在连发序列中，使用连发序列的冷却进度
        if (inBurstSequence) {
            long remaining = getRemainingBurstCooldown();
            long total = WeaponConfig.getConfig(selectedWeapon).getCooldownTicks() * 50L;
            return Math.min(1.0f, (float) remaining / total);
        } else {
            // 否则使用普通冷却进度
            long remaining = getRemainingCooldown(selectedWeapon);
            long total = WeaponConfig.getConfig(selectedWeapon).getCooldownTicks() * 50L;
            return Math.min(1.0f, (float) remaining / total);
        }
    }

    /**
     * 启用/禁用武器系统
     */
    public static void setWeaponEnabled(boolean enabled) {
        weaponEnabled = enabled;
    }

    /**
     * 获取当前选中的武器
     */
    public static WeaponType getSelectedWeapon() {
        return selectedWeapon;
    }

    /**
     * 设置当前武器
     */
    public static void setSelectedWeapon(WeaponType weapon) {
        selectedWeapon = weapon;
    }

    /**
     * 获取连发模式状态
     */
    public static boolean isBurstMode() {
        return burstMode;
    }

    /**
     * 设置连发模式
     */
    public static void setBurstMode(boolean burst) {
        burstMode = burst;
    }

    /**
     * 获取连发状态
     */
    public static int getBurstCount() {
        return burstCount;
    }

    /**
     * 获取最大连发数量
     */
    public static int getMaxBurstCount() {
        return MAX_BURST_COUNT;
    }

    /**
     * 是否在连发序列中
     */
    public static boolean isInBurstSequence() {
        return inBurstSequence;
    }

    /**
     * 重置武器状态
     */
    public static void resetWeaponState() {
        lastFiring = false;
        lastRightClick = false; // 重置右键状态
        for (WeaponType weapon : WeaponType.values()) {
            lastFireTimes.put(weapon, 0L);
        }
        selectedWeapon = WeaponType.EXPLOSIVE_MISSILE;
        weaponEnabled = true;
        burstMode = false;
        burstCount = 0;
        inBurstSequence = false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player != null && player.getRidingEntity() instanceof EntityCustomVehicle vehicle) {
                handleWeaponInput(vehicle, player);
            }
        }
    }
}
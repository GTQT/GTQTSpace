package meowmel.gtqtspace.client.control;

import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class VehicleKeyHandler {

    private static boolean lastAccelerating = false;
    private static float lastPitch = 0;
    private static float lastYaw = 0;
    private static long lastSendTime = 0;
    private static final long SEND_INTERVAL = 50; // 每50ms发送一次，确保持续输入

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player != null && player.getRidingEntity() instanceof EntityCustomVehicle) {
                EntityCustomVehicle vehicle = (EntityCustomVehicle) player.getRidingEntity();
                handleInput(vehicle);
            }
        }
    }

    public static void handleInput(EntityCustomVehicle vehicle) {
        if (vehicle == null) return;

        // 检查按键状态
        boolean accelerating = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        float pitch = 0;
        float yaw = 0;

        // W/S键控制俯仰
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            pitch = -1.0f; // 向上抬头
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            pitch = 1.0f; // 向下低头
        }

        // A/D键控制偏航
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            yaw = -1.0f; // 左转
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            yaw = 1.0f; // 右转
        }

        long currentTime = System.currentTimeMillis();

        // 三种情况发送输入：
        // 1. 输入状态变化
        // 2. 定期发送（确保持续输入）
        // 3. 有任意按键按下时
        boolean inputChanged = accelerating != lastAccelerating || pitch != lastPitch || yaw != lastYaw;
        boolean shouldSendPeriodic = currentTime - lastSendTime > SEND_INTERVAL;
        boolean hasInput = accelerating || pitch != 0 || yaw != 0;

        if (inputChanged || (shouldSendPeriodic && hasInput)) {
            vehicle.updateInputs(accelerating, pitch, yaw);
            lastAccelerating = accelerating;
            lastPitch = pitch;
            lastYaw = yaw;
            lastSendTime = currentTime;

            // 调试输出
            if (inputChanged) {
                //System.out.println("Input changed - Accel: " + accelerating + ", Pitch: " + pitch + ", Yaw: " + yaw);
            }
        }
    }

    // 重置输入状态（当停止控制时调用）
    public static void resetInputState() {
        lastAccelerating = false;
        lastPitch = 0;
        lastYaw = 0;
        lastSendTime = 0;
    }
}
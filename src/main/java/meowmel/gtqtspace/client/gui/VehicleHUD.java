package meowmel.gtqtspace.client.gui;

import meowmel.gtqtspace.client.control.VehicleWeaponHandler;
import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import meowmel.gtqtspace.common.entity.WeaponType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VehicleHUD extends Gui {

    private final Minecraft mc;

    // 颜色常量
    private static final int COLOR_GREEN = 0xFF00FF00;
    private static final int COLOR_RED = 0xFFFF0000;
    private static final int COLOR_YELLOW = 0xFFFFFF00;
    private static final int COLOR_WHITE = 0xFFFFFFFF;
    private static final int COLOR_CYAN = 0xFF00FFFF;

    // 新的速度上限相关
    private static final float MAX_SPEED_MS = 100.0f; // 5.0 * 20 = 100 m/s

    public VehicleHUD() {
        this.mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        Entity ridingEntity = mc.player.getRidingEntity();
        if (!(ridingEntity instanceof EntityCustomVehicle)) return;

        EntityCustomVehicle vehicle = (EntityCustomVehicle) ridingEntity;
        ScaledResolution res = new ScaledResolution(mc);

        renderVehicleHUD(vehicle, res);
    }

    private void renderVehicleHUD(EntityCustomVehicle vehicle, ScaledResolution res) {
        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();

        // 启用透明度和混合
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 绘制中央方向指示器（放大版）
        renderDirectionIndicator(vehicle, screenWidth, screenHeight);

        // 绘制速度表
        renderSpeedometer(vehicle, screenWidth, screenHeight);

        // 绘制武器信息（包含所有武器列表）
        renderWeaponInfo(vehicle, screenWidth, screenHeight);

        // 绘制姿态指示器
        renderAttitudeIndicator(vehicle, screenWidth, screenHeight);

        // 绘制数据面板
        renderDataPanel(vehicle, screenWidth, screenHeight);

        // 绘制警告信息
        renderWarnings(vehicle, screenWidth, screenHeight);
    }

    private void renderDirectionIndicator(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        // 绘制俯仰线 - 增加俯仰范围
        float pitch = vehicle.rotationPitch;
        int pitchOffset = (int) (pitch * 2.0); // 增加俯仰灵敏度

        // 绘制水平线
        for (int i = -180; i <= 180; i += 90) {
            int alpha = 255 - Math.abs(i);
            int color = (alpha << 24) | (COLOR_GREEN & 0xFFFFFF);
            if (Math.abs(i) <= 20) {
                color = COLOR_CYAN;
            }
            // 更长的水平线
            drawHorizontalLine(centerX - 40, centerX + 40, centerY + pitchOffset + i, color);

            // 在线侧显示角度
            if (i != 0) { // 不在中心线显示角度
                String angleText = String.valueOf(Math.abs(i/2)); // 显示实际角度值
                if (i < 0) {
                    drawString(mc.fontRenderer, angleText, centerX - 50, centerY + pitchOffset + i - 3, color);
                } else {
                    drawString(mc.fontRenderer, angleText, centerX + 45, centerY + pitchOffset + i - 3, color);
                }
            }
        }

        // 绘制偏航标记
        float yaw = vehicle.rotationYaw % 360;
        if (yaw < 0) yaw += 360;

        String direction = getDirectionFromYaw(yaw);
        String yawText = String.format("%.0f° %s", yaw, direction);

        // 在指示器上方显示偏航
        drawCenteredString(mc.fontRenderer, yawText, centerX, centerY - 70, COLOR_CYAN);

        // 绘制方向标记 (N, E, S, W) - 保持大间距
        drawCenteredString(mc.fontRenderer, "N", centerX, centerY - 60, COLOR_CYAN);
        drawCenteredString(mc.fontRenderer, "S", centerX, centerY + 60, COLOR_CYAN);
        drawCenteredString(mc.fontRenderer, "W", centerX - 60, centerY, COLOR_CYAN);
        drawCenteredString(mc.fontRenderer, "E", centerX + 60, centerY, COLOR_CYAN);

        // 绘制中心十字准星
        drawCrosshair(centerX, centerY + pitchOffset, 8, COLOR_CYAN);
    }

    private void renderWeaponInfo(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int panelWidth = 120;
        int panelHeight = 120; // 增加高度以容纳武器列表
        int x = screenWidth - panelWidth - 5;
        int y = 15;

        // 武器信息面板背景
        drawRect(x - 2, y - 2, x + panelWidth + 2, y + panelHeight + 2, 0x80000000);

        // 标题
        drawString(mc.fontRenderer, "武器系统", x, y, COLOR_RED);

        // 获取当前选中武器
        WeaponType selectedWeapon = VehicleWeaponHandler.getSelectedWeapon();
        boolean burstMode = VehicleWeaponHandler.isBurstMode();
        int burstCount = VehicleWeaponHandler.getBurstCount();

        // 当前武器信息
        String weaponName = selectedWeapon.getDisplayName();
        int ammo = vehicle.getMissileAmmo(selectedWeapon);
        int maxAmmo = vehicle.getMaxMissileAmmo(selectedWeapon);
        float cooldownProgress = vehicle.getMissileCooldownProgress(selectedWeapon);
        int cooldownTicks = vehicle.getMissileCooldown(selectedWeapon);

        // 武器名称和连发模式指示
        String modeText = burstMode ? "连发" : "单发";
        int modeColor = burstMode ? COLOR_YELLOW : COLOR_GREEN;
        drawString(mc.fontRenderer, weaponName, x, y + 12, COLOR_WHITE);
        drawString(mc.fontRenderer, modeText, x + 70, y + 12, modeColor);

        // 弹药数量
        String ammoText = ammo + "/" + maxAmmo;
        int ammoColor = getAmmoColor(ammo, maxAmmo);
        drawString(mc.fontRenderer, "弹药: " + ammoText, x, y + 24, ammoColor);

        // 连发计数显示
        if (burstMode) {
            String burstText = "连发: " + burstCount + "/" + VehicleWeaponHandler.getMaxBurstCount();
            drawString(mc.fontRenderer, burstText, x, y + 36, COLOR_YELLOW);
        }

        // 冷却状态
        int coolDownY = burstMode ? 48 : 36;
        if (cooldownProgress > 0) {
            drawString(mc.fontRenderer, String.format("冷却: %.1fs", cooldownTicks / 20.0),
                    x, y + coolDownY, COLOR_YELLOW);

            // 冷却条
            int barWidth = 80;
            int barHeight = 4;
            int barY = y + coolDownY + 10;

            // 背景
            drawRect(x, barY, x + barWidth, barY + barHeight, 0xFF444444);
            // 进度
            int progressWidth = (int)(barWidth * (1 - cooldownProgress));
            drawRect(x, barY, x + progressWidth, barY + barHeight, getCoolDownColor(cooldownProgress));
        } else {
            drawString(mc.fontRenderer, "就绪", x, y + coolDownY, COLOR_GREEN);
        }

        // 绘制所有武器列表
        renderWeaponList(vehicle, x, y + (burstMode ? 70 : 58), panelWidth);
    }

    /**
     * 渲染武器列表
     */
    private void renderWeaponList(EntityCustomVehicle vehicle, int x, int y, int width) {
        drawString(mc.fontRenderer, "武器列表:", x, y, COLOR_YELLOW);
        y += 10;

        WeaponType[] weapons = WeaponType.values();
        WeaponType selectedWeapon = VehicleWeaponHandler.getSelectedWeapon();

        for (int i = 0; i < weapons.length; i++) {
            WeaponType weapon = weapons[i];
            int ammo = vehicle.getMissileAmmo(weapon);
            int maxAmmo = vehicle.getMaxMissileAmmo(weapon);
            int cooldown = vehicle.getMissileCooldown(weapon);

            // 选择颜色
            int textColor;
            if (weapon == selectedWeapon) {
                textColor = COLOR_CYAN; // 青色，当前选中
            } else if (ammo == 0) {
                textColor = 0xFF666666; // 灰色，无弹药
            } else if (cooldown > 0) {
                textColor = COLOR_YELLOW; // 黄色，冷却中
            } else {
                textColor = COLOR_WHITE; // 白色，可用
            }

            // 武器条目
            String weaponText = String.format("%d. %s: %d/%d",
                    i + 1,
                    weapon.getDisplayName(),
                    ammo,
                    maxAmmo
            );

            // 冷却指示
            if (cooldown > 0 && weapon != selectedWeapon) {
                weaponText += String.format(" (%.1fs)", cooldown / 20.0f);
            }

            drawString(mc.fontRenderer, weaponText, x, y, textColor);
            y += 8;
        }
    }

    private void renderSpeedometer(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int panelWidth = 100;
        int x = screenWidth - panelWidth - 5;
        int y = screenHeight - 60;

        // 速度表背景
        drawRect(x - 2, y - 2, x + panelWidth + 2, y + 45, 0x80000000);

        // 标题
        drawString(mc.fontRenderer, "速度", x, y, COLOR_GREEN);

        // 当前速度
        double speed = vehicle.getCurrentSpeed() * 20; // 转换为 m/s
        String speedText = String.format("%.1f m/s", speed);
        drawString(mc.fontRenderer, speedText, x, y + 12, getSpeedTextColor(speed));

        // 速度条
        int barWidth = 80;
        int barHeight = 6;
        int barX = x + 5;
        int barY = y + 25;

        double speedPercent = Math.min(speed / MAX_SPEED_MS, 1.0);

        // 背景
        drawRect(barX, barY, barX + barWidth, barY + barHeight, 0xFF222222);

        // 速度条
        int speedWidth = (int) (barWidth * speedPercent);
        if (speedWidth > 0) {
            drawRect(barX, barY, barX + speedWidth, barY + barHeight, getSpeedColor(speedPercent));
        }
    }

    private void renderAttitudeIndicator(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int panelWidth = 100;
        int x = 10;
        int y = screenHeight - 100;

        // 姿态指示器背景
        drawRect(x - 2, y - 2, x + panelWidth + 2, y + 70, 0x80000000);

        // 标题
        drawString(mc.fontRenderer, "姿态", x, y, COLOR_CYAN);

        // 姿态信息
        float pitch = vehicle.rotationPitch;
        float yaw = vehicle.rotationYaw % 360;
        if (yaw < 0) yaw += 360;

        drawString(mc.fontRenderer, String.format("俯仰: %.1f°", pitch), x, y + 12, getPitchColor(pitch));
        drawString(mc.fontRenderer, String.format("偏航: %.1f°", yaw), x, y + 24, COLOR_CYAN);

        // 简化姿态指示器
        int indicatorX = x + 50;
        int indicatorY = y + 50;
        int radius = 20;

        // 绘制简化姿态球
        drawCircle(indicatorX, indicatorY, radius, 0x66666666);

        // 根据俯仰和偏航绘制内部线
        drawAttitudeLines(vehicle, indicatorX, indicatorY, radius);
    }

    private void renderDataPanel(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int panelWidth = 120;
        int x = 10;
        int y = 15;

        // 数据面板背景
        drawRect(x - 2, y - 2, x + panelWidth + 2, y + 50, 0x80000000);

        // 标题
        drawString(mc.fontRenderer, "数据", x, y, COLOR_GREEN);

        // 高度信息
        int altitude = (int) vehicle.posY;
        drawString(mc.fontRenderer, String.format("高度: %d m", altitude), x, y + 12, getAltitudeColor(altitude));

        // 坐标信息
        drawString(mc.fontRenderer, String.format("坐标: %d,%d,%d",
                        (int) vehicle.posX, (int) vehicle.posY, (int) vehicle.posZ),
                x, y + 24, COLOR_YELLOW);

        // 输入状态
        boolean isAccelerating = vehicle.dataManager.get(EntityCustomVehicle.ACCELERATING);
        String accelState = isAccelerating ? "加速中" : "巡航";
        int stateColor = isAccelerating ? COLOR_RED : COLOR_GREEN;
        drawString(mc.fontRenderer, "状态: " + accelState, x, y + 36, stateColor);
    }

    private void renderWarnings(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int centerX = screenWidth / 2;
        int y = screenHeight - 25;

        // 检查警告条件
        double speed = vehicle.getCurrentSpeed() * 20;
        boolean highSpeed = speed > MAX_SPEED_MS * 0.8;
        boolean lowAltitude = vehicle.posY < 20;
        boolean criticalAltitude = vehicle.posY < 5;

        if (highSpeed) {
            String warning = "警告: 速度过高!";
            drawCenteredString(mc.fontRenderer, warning, centerX, y, COLOR_RED);
            y += 12;
        }

        if (criticalAltitude) {
            String warning = "紧急: 高度极低!";
            drawCenteredString(mc.fontRenderer, warning, centerX, y, 0xFFFF3300);
            y += 12;
        } else if (lowAltitude) {
            String warning = "警告: 高度过低!";
            drawCenteredString(mc.fontRenderer, warning, centerX, y, COLOR_YELLOW);
            y += 12;
        }
    }

    // 辅助方法

    private String getDirectionFromYaw(float yaw) {
        if (yaw >= 337.5 || yaw < 22.5) return "北";
        if (yaw >= 22.5 && yaw < 67.5) return "东北";
        if (yaw >= 67.5 && yaw < 112.5) return "东";
        if (yaw >= 112.5 && yaw < 157.5) return "东南";
        if (yaw >= 157.5 && yaw < 202.5) return "南";
        if (yaw >= 202.5 && yaw < 247.5) return "西南";
        if (yaw >= 247.5 && yaw < 292.5) return "西";
        return "西北";
    }

    private int getSpeedColor(double speedPercent) {
        if (speedPercent < 0.5) return COLOR_GREEN;
        if (speedPercent < 0.8) return COLOR_YELLOW;
        return COLOR_RED;
    }

    private int getSpeedTextColor(double speed) {
        if (speed < MAX_SPEED_MS * 0.5) return COLOR_GREEN;
        if (speed < MAX_SPEED_MS * 0.8) return COLOR_YELLOW;
        return COLOR_RED;
    }

    private int getAmmoColor(int ammo, int maxAmmo) {
        if (ammo == 0) return COLOR_RED;
        if (ammo <= maxAmmo * 0.3) return COLOR_YELLOW;
        return COLOR_GREEN;
    }

    private int getCoolDownColor(float progress) {
        if (progress > 0.7f) return COLOR_RED;
        if (progress > 0.3f) return COLOR_YELLOW;
        return COLOR_GREEN;
    }

    private int getPitchColor(float pitch) {
        if (Math.abs(pitch) > 60) return COLOR_RED;
        if (Math.abs(pitch) > 30) return COLOR_YELLOW;
        return COLOR_GREEN;
    }

    private int getAltitudeColor(int altitude) {
        if (altitude < 10) return COLOR_RED;
        if (altitude < 50) return COLOR_YELLOW;
        return COLOR_GREEN;
    }

    private void drawCircle(int x, int y, int radius, int color) {
        // 简化的圆形绘制
        for (int i = 0; i < 360; i += 15) {
            double rad = Math.toRadians(i);
            int x1 = x + (int) (radius * Math.cos(rad));
            int y1 = y + (int) (radius * Math.sin(rad));
            int x2 = x + (int) (radius * Math.cos(rad + Math.toRadians(15)));
            int y2 = y + (int) (radius * Math.sin(rad + Math.toRadians(15)));
            drawLine(x1, y1, x2, y2, color);
        }
    }

    private void drawCrosshair(int x, int y, int size, int color) {
        drawHorizontalLine(x - size, x + size, y, color);
        drawVerticalLine(x, y - size, y + size, color);
    }

    private void drawLine(int x1, int y1, int x2, int y2, int color) {
        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(r, g, b, a);

        net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)x1, (double)y1, 0.0D).endVertex();
        bufferbuilder.pos((double)x2, (double)y2, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private void drawAttitudeLines(EntityCustomVehicle vehicle, int centerX, int centerY, int radius) {
        float pitch = vehicle.rotationPitch;
        float yaw = vehicle.rotationYaw;

        // 地平线（根据俯仰倾斜）
        double pitchRad = Math.toRadians(pitch);
        int horizonY = centerY + (int)(radius * Math.sin(pitchRad));

        // 绘制简化地平线
        drawHorizontalLine(centerX - radius, centerX + radius, horizonY, COLOR_CYAN);

        // 绘制偏航指示器
        double yawRad = Math.toRadians(yaw);
        int indicatorX = centerX + (int)(radius * 0.7 * Math.sin(yawRad));
        int indicatorY = centerY - (int)(radius * 0.7 * Math.cos(yawRad));

        // 偏航指示三角形
        drawTriangle(indicatorX, indicatorY, 4, COLOR_RED);
    }

    private void drawTriangle(int x, int y, int size, int color) {
        int[] xPoints = {x, x - size, x + size};
        int[] yPoints = {y - size, y + size, y + size};

        net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;
        GlStateManager.color(r, g, b, a);

        bufferbuilder.begin(6, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)xPoints[0], (double)yPoints[0], 0.0D).endVertex();
        bufferbuilder.pos((double)xPoints[1], (double)yPoints[1], 0.0D).endVertex();
        bufferbuilder.pos((double)xPoints[2], (double)yPoints[2], 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
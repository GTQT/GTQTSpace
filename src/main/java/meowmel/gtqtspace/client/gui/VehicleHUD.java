package meowmel.gtqtspace.client.gui;


import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static meowmel.gtqtspace.GTQTSpace.MODID;

@SideOnly(Side.CLIENT)
public class VehicleHUD extends Gui {

    private final Minecraft mc;

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

        // 绘制中央方向指示器
        renderDirectionIndicator(vehicle, screenWidth, screenHeight);

        // 绘制速度表
        renderSpeedometer(vehicle, screenWidth, screenHeight);

        // 绘制姿态指示器
        renderAttitudeIndicator(vehicle, screenWidth, screenHeight);

        // 绘制数据面板
        renderDataPanel(vehicle, screenWidth, screenHeight);

        // 绘制警告信息
        renderWarnings(vehicle, screenWidth, screenHeight);
    }

    private void renderDirectionIndicator(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2 - 20;

        // 绘制俯仰线
        float pitch = vehicle.rotationPitch;
        int pitchOffset = (int) (pitch * 2); // 缩放因子

        // 绘制水平线（俯仰指示）
        drawHorizontalLine(centerX - 60, centerX + 60, centerY + pitchOffset, 0xFF00FF00);

        // 绘制偏航标记
        float yaw = vehicle.rotationYaw % 360;
        if (yaw < 0) yaw += 360;

        String direction = getDirectionFromYaw(yaw);
        String yawText = String.format("%.0f° %s", yaw, direction);

        // 在指示器上方显示偏航
        drawCenteredString(mc.fontRenderer, yawText, centerX, centerY - 80, 0xFF00FF00);

        // 绘制方向标记 (N, E, S, W)
        drawCenteredString(mc.fontRenderer, "N", centerX, centerY - 50, 0xFF00FF00);
        drawCenteredString(mc.fontRenderer, "S", centerX, centerY + 40, 0xFF00FF00);
        drawCenteredString(mc.fontRenderer, "W", centerX - 50, centerY, 0xFF00FF00);
        drawCenteredString(mc.fontRenderer, "E", centerX + 50, centerY, 0xFF00FF00);
    }

    private void renderSpeedometer(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int x = screenWidth - 120;
        int y = screenHeight - 80;

        // 速度表背景
        drawRect(x - 5, y - 5, x + 115, y + 65, 0x80000000);

        // 标题
        drawString(mc.fontRenderer, "速度信息", x, y, 0xFF00FF00);

        // 当前速度
        double speed = vehicle.getCurrentSpeed() * 20; // 转换为 m/s (近似)
        drawString(mc.fontRenderer, String.format("速度: %.1f m/s", speed), x, y + 15, 0xFFFFFFFF);

        // 速度条
        int barWidth = 100;
        int barHeight = 8;
        double speedPercent = Math.min(speed / 50.0, 1.0); // 假设最大速度50m/s

        // 背景条
        drawRect(x, y + 30, x + barWidth, y + 30 + barHeight, 0xFF333333);

        // 速度条
        int speedWidth = (int) (barWidth * speedPercent);
        int color = getSpeedColor(speedPercent);
        drawRect(x, y + 30, x + speedWidth, y + 30 + barHeight, color);

        // 运动向量
        drawString(mc.fontRenderer, String.format("向量: %.1f, %.1f, %.1f",
                        vehicle.motionX * 20, vehicle.motionY * 20, vehicle.motionZ * 20),
                x, y + 45, 0xFFFFFF00);
    }

    private void renderAttitudeIndicator(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int x = 20;
        int y = screenHeight - 150;

        // 姿态指示器背景
        drawRect(x - 5, y - 5, x + 115, y + 115, 0x80000000);

        // 标题
        drawString(mc.fontRenderer, "飞行姿态", x, y, 0xFF00FF00);

        // 俯仰角度
        float pitch = vehicle.rotationPitch;
        drawString(mc.fontRenderer, String.format("俯仰: %.1f°", pitch), x, y + 15, 0xFFFFFFFF);

        // 偏航角度
        float yaw = vehicle.rotationYaw % 360;
        if (yaw < 0) yaw += 360;
        drawString(mc.fontRenderer, String.format("偏航: %.1f°", yaw), x, y + 30, 0xFFFFFFFF);

        // 简单的姿态球
        int sphereX = x + 50;
        int sphereY = y + 80;
        int sphereRadius = 30;

        // 绘制姿态球外圈
        drawCircle(sphereX, sphereY, sphereRadius, 0xFF00FF00);

        // 根据俯仰和偏航绘制内部线
        drawAttitudeLines(vehicle, sphereX, sphereY, sphereRadius);
    }

    private void renderDataPanel(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int x = 20;
        int y = 20;

        // 数据面板背景
        drawRect(x - 5, y - 5, x + 150, y + 80, 0x80000000);

        // 标题
        drawString(mc.fontRenderer, "飞行数据", x, y, 0xFF00FF00);

        // 高度信息
        int altitude = (int) vehicle.posY;
        drawString(mc.fontRenderer, String.format("高度: %d m", altitude), x, y + 15, 0xFFFFFFFF);

        // 坐标信息
        drawString(mc.fontRenderer, String.format("坐标: %d, %d, %d",
                        (int) vehicle.posX, (int) vehicle.posY, (int) vehicle.posZ),
                x, y + 30, 0xFFFFFF00);

        // 输入状态
        String accelState = vehicle.dataManager.get(EntityCustomVehicle.ACCELERATING) ? "加速中" : "巡航";
        drawString(mc.fontRenderer, "状态: " + accelState, x, y + 45,
                vehicle.dataManager.get(EntityCustomVehicle.ACCELERATING) ? 0xFFFF0000 : 0xFF00FF00);

        // 乘客数量
        int passengerCount = vehicle.getPassengers().size();
        drawString(mc.fontRenderer, String.format("乘员: %d/%d", passengerCount, 2),
                x, y + 60, 0xFFFFFFFF);
    }

    private void renderWarnings(EntityCustomVehicle vehicle, int screenWidth, int screenHeight) {
        int centerX = screenWidth / 2;
        int y = screenHeight - 40;

        // 检查警告条件
        double speed = vehicle.getCurrentSpeed() * 20;
        boolean highSpeed = speed > 40;
        boolean lowAltitude = vehicle.posY < 10;

        if (highSpeed) {
            String warning = TextFormatting.RED + "警告: 速度过高!";
            drawCenteredString(mc.fontRenderer, warning, centerX, y, 0xFFFF0000);
        }

        if (lowAltitude) {
            String warning = TextFormatting.YELLOW + "警告: 高度过低!";
            drawCenteredString(mc.fontRenderer, warning, centerX, y + (highSpeed ? 12 : 0), 0xFFFFFF00);
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
        if (speedPercent < 0.5) return 0xFF00FF00; // 绿色
        if (speedPercent < 0.8) return 0xFFFFFF00; // 黄色
        return 0xFFFF0000; // 红色
    }

    private void drawCircle(int x, int y, int radius, int color) {
        // 简单的圆形绘制（实际项目中可以使用更精细的方法）
        for (int i = 0; i < 360; i += 10) {
            double rad = Math.toRadians(i);
            int x1 = x + (int) (radius * Math.cos(rad));
            int y1 = y + (int) (radius * Math.sin(rad));
            int x2 = x + (int) (radius * Math.cos(rad + Math.toRadians(10)));
            int y2 = y + (int) (radius * Math.sin(rad + Math.toRadians(10)));
            drawLine(x1, y1, x2, y2, color);
        }
    }

    private void drawLine(int x1, int y1, int x2, int y2, int color) {
        // 简单的线绘制
        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(f, f1, f2, f3);

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

        // 绘制地平线
        drawHorizontalLine(centerX - radius, centerX + radius, horizonY, 0xFF00FFFF);

        // 绘制偏航指示器
        double yawRad = Math.toRadians(yaw);
        int indicatorX = centerX + (int)(radius * 0.7 * Math.sin(yawRad));
        int indicatorY = centerY - (int)(radius * 0.7 * Math.cos(yawRad));

        // 偏航指示三角形
        drawTriangle(indicatorX, indicatorY, 5, 0xFFFF0000);
    }

    private void drawTriangle(int x, int y, int size, int color) {
        // 简单的三角形绘制
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
package meowmel.gtqtspace.client.render;

import meowmel.gtqtspace.client.model.ModelCustomVehicle;
import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import static meowmel.gtqtspace.GTQTSpace.MODID;

public class RenderCustomVehicle extends Render<EntityCustomVehicle> {

    private final ModelBase model = new ModelCustomVehicle();

    public RenderCustomVehicle(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.8F;
        this.shadowOpaque = 0.5F;
    }

    @Override
    public void doRender(EntityCustomVehicle entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();

        // 移动到实体位置
        GlStateManager.translate(x, y + 0.5, z); // 稍微向上偏移，让飞船看起来在空中

        // 应用偏航旋转（左右转）
        GlStateManager.rotate(180.0F + entityYaw, 0.0F, 1.0F, 0.0F);

        // 应用俯仰旋转（上下转）- 这是关键！
        // 使用插值让旋转更平滑
        float interpolatedPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        GlStateManager.rotate(-interpolatedPitch, 1.0F, 0.0F, 0.0F);

        // 可选：根据运动状态添加一些视觉效果
        if (entity.isMoving()) {
            // 当移动时，可以添加轻微的摇晃效果
            float shake = MathHelper.sin(entity.ticksExisted * 0.3F) * 0.05F;
            GlStateManager.rotate(shake, 0.0F, 0.0F, 1.0F);
        }

        // 绑定纹理
        this.bindEntityTexture(entity);

        // 渲染模型
        this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        // 调试渲染：显示朝向线
        if (entity.world.isRemote && entity.getControllingPassenger() != null) {
            renderDirectionLine(entity, partialTicks);
        }

        GlStateManager.popMatrix();

        // 渲染名称标签等
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    // 渲染方向指示线（调试用）
    private void renderDirectionLine(EntityCustomVehicle entity, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.glLineWidth(3.0F);

        // 计算前方方向
        float yawRad = (float) Math.toRadians(-entity.rotationYaw);
        float pitchRad = (float) Math.toRadians(-entity.rotationPitch);

        double forwardX = Math.sin(yawRad) * Math.cos(pitchRad);
        double forwardY = Math.sin(pitchRad);
        double forwardZ = Math.cos(yawRad) * Math.cos(pitchRad);

        // 绘制方向线
        net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(3, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(0, 0, 0).color(0, 1, 0, 1).endVertex(); // 起点（绿色）
        buffer.pos(forwardX * 5, forwardY * 5, forwardZ * 5).color(0, 1, 0, 1).endVertex(); // 终点

        tessellator.draw();

        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCustomVehicle entity) {
        return new ResourceLocation(MODID, "textures/entities/custom_vehicle.png");
    }

    // 可选：添加更多渲染功能
    @Override
    protected void renderName(EntityCustomVehicle entity, double x, double y, double z) {
        // 只在玩家看向实体时显示名称
        if (this.canRenderName(entity)) {
            double distance = this.renderManager.getDistanceToCamera(entity.posX, entity.posY, entity.posZ);
            if (distance <= 16.0D) {
                y += 1.5; // 将名称标签放在实体上方
                this.renderLivingLabel(entity, entity.getCustomNameTag(), x, y, z, 16);
            }
        }
    }
}
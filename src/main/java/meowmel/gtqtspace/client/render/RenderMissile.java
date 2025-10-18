package meowmel.gtqtspace.client.render;

import meowmel.gtqtspace.common.entity.EntityMissileBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import static meowmel.gtqtspace.GTQTSpace.MODID;

public class RenderMissile extends Render<EntityMissileBase> {

    // 简单的导弹模型（长方体）
    private final net.minecraft.client.model.ModelBase model;

    public RenderMissile(RenderManager renderManager) {
        super(renderManager);
        this.model = new SimpleMissileModel();
        this.shadowSize = 0.5F;
    }

    @Override
    public void doRender(EntityMissileBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();

        // 移动到实体位置
        GlStateManager.translate(x, y + 0.25, z);

        // 应用旋转（偏航和俯仰）
        GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);

        // 根据速度轻微缩放（高速时看起来更长）
        float speedScale = MathHelper.clamp(entity.getSpeed() / 2.0f, 1.0f, 1.5f);
        GlStateManager.scale(0.5f * speedScale, 0.5f, 0.5f);

        // 绑定纹理
        this.bindEntityTexture(entity);

        // 渲染模型
        this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        GlStateManager.popMatrix();

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMissileBase entity) {
        return new ResourceLocation(MODID, "textures/entity/missile.png");
    }

    // 简单的导弹模型（长方体）
    private static class SimpleMissileModel extends net.minecraft.client.model.ModelBase {
        private final net.minecraft.client.model.ModelRenderer missileBody;

        public SimpleMissileModel() {
            this.textureWidth = 32;
            this.textureHeight = 32;

            this.missileBody = new net.minecraft.client.model.ModelRenderer(this, 0, 0);
            this.missileBody.addBox(-1F, -1F, -4F, 2, 2, 8); // 细长的长方体
            this.missileBody.setRotationPoint(0F, 0F, 0F);

            // 添加尾翼
            net.minecraft.client.model.ModelRenderer fin1 = new net.minecraft.client.model.ModelRenderer(this, 0, 10);
            fin1.addBox(-2F, -0.5F, 2F, 4, 1, 2);
            fin1.setRotationPoint(0F, 0F, 0F);
            this.missileBody.addChild(fin1);

            net.minecraft.client.model.ModelRenderer fin2 = new net.minecraft.client.model.ModelRenderer(this, 0, 13);
            fin2.addBox(-0.5F, -2F, 2F, 1, 4, 2);
            fin2.setRotationPoint(0F, 0F, 0F);
            this.missileBody.addChild(fin2);
        }

        @Override
        public void render(net.minecraft.entity.Entity entity, float limbSwing, float limbSwingAmount,
                           float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            this.missileBody.render(scale);
        }
    }
}
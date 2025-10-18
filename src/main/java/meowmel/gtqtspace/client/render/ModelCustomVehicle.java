package meowmel.gtqtspace.client.render;

import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCustomVehicle extends ModelBase {
    private final ModelRenderer body;
    private final ModelRenderer nose;
    private final ModelRenderer wingLeft;
    private final ModelRenderer wingRight;
    private final ModelRenderer tail;

    public ModelCustomVehicle() {
        textureWidth = 64;
        textureHeight = 64;

        // 主体 - 重新设计为更适合太空飞船的形状
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-4F, -2F, -8F, 8, 4, 16); // 更长的机身
        body.setRotationPoint(0F, 0F, 0F);
        body.setTextureSize(64, 64);

        // 机头 - 添加锥形机头
        nose = new ModelRenderer(this, 0, 20);
        nose.addBox(-2F, -1.5F, -12F, 4, 3, 4); // 锥形机头
        nose.setRotationPoint(0F, 0F, 0F);
        nose.setTextureSize(64, 64);

        // 左翼
        wingLeft = new ModelRenderer(this, 32, 0);
        wingLeft.addBox(-12F, -0.5F, -4F, 8, 1, 8); // 水平机翼
        wingLeft.setRotationPoint(0F, 0F, 0F);
        wingLeft.setTextureSize(64, 64);

        // 右翼
        wingRight = new ModelRenderer(this, 32, 9);
        wingRight.addBox(4F, -0.5F, -4F, 8, 1, 8); // 水平机翼
        wingRight.setRotationPoint(0F, 0F, 0F);
        wingRight.setTextureSize(64, 64);

        // 尾翼 - 垂直尾翼
        tail = new ModelRenderer(this, 0, 27);
        tail.addBox(-1F, -6F, 6F, 2, 6, 2); // 垂直尾翼
        tail.setRotationPoint(0F, 0F, 0F);
        tail.setTextureSize(64, 64);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        // 设置模型的旋转角度
        // 注意：这里我们直接使用实体的旋转角度
        EntityCustomVehicle vehicle = (EntityCustomVehicle) entity;

        // 应用偏航旋转（左右转）- 反向
        this.body.rotateAngleY = -vehicle.rotationYaw * (float)Math.PI / 180F;
        this.nose.rotateAngleY = -vehicle.rotationYaw * (float)Math.PI / 180F;
        this.wingLeft.rotateAngleY = -vehicle.rotationYaw * (float)Math.PI / 180F;
        this.wingRight.rotateAngleY = -vehicle.rotationYaw * (float)Math.PI / 180F;
        this.tail.rotateAngleY = -vehicle.rotationYaw * (float)Math.PI / 180F;

        // 应用俯仰旋转（上下转）- 反向
        this.body.rotateAngleX = -vehicle.rotationPitch * (float)Math.PI / 180F;
        this.nose.rotateAngleX = -vehicle.rotationPitch * (float)Math.PI / 180F;
        this.wingLeft.rotateAngleX = -vehicle.rotationPitch * (float)Math.PI / 180F;
        this.wingRight.rotateAngleX = -vehicle.rotationPitch * (float)Math.PI / 180F;
        this.tail.rotateAngleX = -vehicle.rotationPitch * (float)Math.PI / 180F;

        // 渲染所有部件
        body.render(scale);
        nose.render(scale);
        wingLeft.render(scale);
        wingRight.render(scale);
        tail.render(scale);
    }

    // 设置部件旋转的辅助方法
    public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
package meowmel.gtqtspace.api.utils;

import meowmel.gtqtspace.GTQTSConfig;
import meowmel.gtqtspace.GTQTSpace;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class SkyRenderUtil {
    static final ResourceLocation barnardaloopTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/barnardaloop.png");
    static final ResourceLocation lmcTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/lmc.png");
    static final ResourceLocation smcTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/smc.png");
    static final ResourceLocation andromedaTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/andromeda.png");
    static final ResourceLocation eta_carinaeTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/eta_carinae.png");
    static final ResourceLocation pleiadesTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/pleiades.png");
    static final ResourceLocation triangulumTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/triangulum.png");

    static final ResourceLocation[] galaxyTextures = new ResourceLocation[6];
    static {
        for(int i = 0; i < 6; ++i) {
            galaxyTextures[i] = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/" + "milky_way" + "/" + "milky_way" + "_" + (i + 1) + ".png");
        }
    }
    public static void GalaxyRender() {

        ///////////////////////////////银河系背景生成///////////////////////////////
        if (GTQTSConfig.Render.BackgroundRender) {
            float x = 50.0F;
            float y = -90.0F;
            renderImage(galaxyTextures[0], (-4.0F + x) % 360.0F, y, 0.0F, 60.0F);
            renderImage(galaxyTextures[1], (58.0F + x) % 360.0F, y, 0.0F, 60.0F);
            renderImage(galaxyTextures[2], (120.0F + x) % 360.0F, y, 0.0F, 60.2F);
            renderImage(galaxyTextures[3], (182.0F + x) % 360.0F, y, 0.0F, 60.0F);
            renderImage(galaxyTextures[4], (244.0F + x) % 360.0F, y, 0.0F, 60.2F);
            renderImage(galaxyTextures[5], (306.0F + x) % 360.0F, y, 0.0F, 60.0F);

            renderImage(smcTexture, -70.0F, -140.0F, 20.0F, 5.0F);
            renderImage(andromedaTexture, -70.0F, -150.0F, 20.0F, 4.0F);
            renderImage(eta_carinaeTexture, 35.0F, 120.0F, 0.0F, 20.0F);
            renderImage(pleiadesTexture, -75.0F, 120.0F, 0.0F, 6.0F);
            renderImage(barnardaloopTexture, -25.0F, 160.0F, 0.0F, 6.0F);
            renderImage(lmcTexture, 80.0F, -120.0F, -55.0F, 3.0F);
            renderImage(triangulumTexture, 50.0F, -160.0F, -55.0F, 3.0F);
        }
    }

    /**
     * 渲染图像方法
     * 该方法用于在指定位置和大小下渲染一个图像，可以添加阴影效果
     *
     * @param image 图像资源位置
     * @param x     X轴旋转角度
     * @param y     Y轴旋转角度
     * @param z     Z轴旋转角度
     * @param size  图像的大小
     */
    protected static void renderImage(ResourceLocation image, float x, float y, float z, float size) {
        // 保存当前的GL状态
        GL11.glPushMatrix();
        // 获取Tessellator实例，用于绘制几何图形
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();
        // 围绕X轴旋转
        GL11.glRotatef(x, 0.0F, 1.0F, 0.0F);
        // 围绕Y轴旋转
        GL11.glRotatef(y, 1.0F, 0.0F, 0.0F);
        // 围绕Z轴旋转
        GL11.glRotatef(z, 0.0F, 0.0F, 1.0F);
        // 设置颜色和透明度
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1);
        // 绑定图像资源
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(image);
        // 开始绘制带纹理的四边形
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-size, -100.0, size).tex(0.0, 1.0).endVertex();
        worldRenderer.pos(size, -100.0, size).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(size, -100.0, -size).tex(1.0, 0.0).endVertex();
        worldRenderer.pos(-size, -100.0, -size).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        // 恢复之前保存的GL状态
        GL11.glPopMatrix();
    }

    public static void renderStars()
    {
        final Random var1 = new Random(10842L);
        final Tessellator var2 = Tessellator.getInstance();
        var2.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

        for (int var3 = 0; var3 < (GTQTSConfig.Render.moreStars ? 8000 : 2000); ++var3)
        {
            double var4 = var1.nextFloat() * 2.0F - 1.0F;
            double var6 = var1.nextFloat() * 2.0F - 1.0F;
            double var8 = var1.nextFloat() * 2.0F - 1.0F;
            final double var10 = 0.07F + var1.nextFloat() * 0.06F;
            double var12 = var4 * var4 + var6 * var6 + var8 * var8;

            if (var12 < 1.0D && var12 > 0.01D)
            {
                var12 = 1.0D / Math.sqrt(var12);
                var4 *= var12;
                var6 *= var12;
                var8 *= var12;
                final double var14 = var4 * (GTQTSConfig.Render.moreStars ? var1.nextDouble() * 50D + 75D : 50.0D);
                final double var16 = var6 * (GTQTSConfig.Render.moreStars ? var1.nextDouble() * 50D + 75D : 50.0D);
                final double var18 = var8 * (GTQTSConfig.Render.moreStars ? var1.nextDouble() * 50D + 75D : 50.0D);
                final double var20 = Math.atan2(var4, var8);
                final double var22 = Math.sin(var20);
                final double var24 = Math.cos(var20);
                final double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
                final double var28 = Math.sin(var26);
                final double var30 = Math.cos(var26);
                final double var32 = var1.nextDouble() * Math.PI * 2.0D;
                final double var34 = Math.sin(var32);
                final double var36 = Math.cos(var32);

                for (int var38 = 0; var38 < 4; ++var38)
                {
                    final double var39 = 0.0D;
                    final double var41 = ((var38 & 2) - 1) * var10;
                    final double var43 = ((var38 + 1 & 2) - 1) * var10;
                    final double var47 = var41 * var36 - var43 * var34;
                    final double var49 = var43 * var36 + var41 * var34;
                    final double var53 = var47 * var28 + var39 * var30;
                    final double var55 = var39 * var28 - var47 * var30;
                    final double var57 = var55 * var22 - var49 * var24;
                    final double var61 = var49 * var22 + var55 * var24;
                    var2.getBuffer().pos(var14 + var57, var16 + var53, var18 + var61).endVertex();
                }
            }
        }

        var2.draw();
    }
}

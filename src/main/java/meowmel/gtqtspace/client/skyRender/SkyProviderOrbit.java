package meowmel.gtqtspace.client.skyRender;

import meowmel.gtqtspace.GTQTSConfig;
import meowmel.gtqtspace.GTQTSpace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class SkyProviderOrbit extends IRenderHandler
{

    private static final ResourceLocation moonTexture = new ResourceLocation(GTQTSpace.MODID, "textures/gui/planets/moon.png");
    private static final ResourceLocation sunTexture = new ResourceLocation(GTQTSpace.MODID, "textures/gui/planets/sun.png");

    private static final ResourceLocation barnardaloopTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/barnardaloop.png");
    private static final ResourceLocation lmcTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/lmc.png");
    private static final ResourceLocation smcTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/smc.png");
    private static final ResourceLocation andromedaTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/andromeda.png");
    private static final ResourceLocation eta_carinaeTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/eta_carinae.png");
    private static final ResourceLocation pleiadesTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/pleiades.png");
    private static final ResourceLocation triangulumTexture = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/triangulum.png");

    protected static final ResourceLocation[] galaxyTextures = new ResourceLocation[6];

    public static boolean displayListsInitialized = false;
    public static int starGLCallList;
    public static int glSkyList;
    public static int glSkyList2;

    private final ResourceLocation planetToRender;
    private final boolean renderMoon;
    private final boolean renderSun;
    public float spinAngle = 0;
    public float spinDeltaPerTick = 0;
    private float prevPartialTicks = 0;
    private long prevTick;

    public SkyProviderOrbit(ResourceLocation planet, boolean renderMoon, boolean renderSun,boolean renderStars)
    {
        this.planetToRender = planet;
        this.renderMoon = renderMoon;
        this.renderSun = renderSun;

        if (!displayListsInitialized)
        {
            initializeDisplayLists();
        }

    }
    private final Minecraft minecraft = FMLClientHandler.instance().getClient();
    boolean displayGalaxyImg;
    private void initializeDisplayLists()
    {
        if (GTQTSConfig.Render.StarsRender) {
            starGLCallList = GLAllocation.generateDisplayLists(3);

            GL11.glPushMatrix();
            GL11.glNewList(SkyProviderOrbit.starGLCallList, GL11.GL_COMPILE);
            this.renderStars();
            GL11.glEndList();
            GL11.glPopMatrix();
            final Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder worldRenderer = tessellator.getBuffer();
            SkyProviderOrbit.glSkyList = SkyProviderOrbit.starGLCallList + 1;
            GL11.glNewList(SkyProviderOrbit.glSkyList, GL11.GL_COMPILE);
            final byte byte2 = 64;
            final int i = 256 / byte2 + 2;
            float f = 16F;

            for (int j = -byte2 * i; j <= byte2 * i; j += byte2) {
                for (int l = -byte2 * i; l <= byte2 * i; l += byte2) {
                    worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
                    worldRenderer.pos(j + 0, f, l + 0).endVertex();
                    worldRenderer.pos(j + byte2, f, l + 0).endVertex();
                    worldRenderer.pos(j + byte2, f, l + byte2).endVertex();
                    worldRenderer.pos(j + 0, f, l + byte2).endVertex();
                    tessellator.draw();
                }
            }

            GL11.glEndList();
            SkyProviderOrbit.glSkyList2 = SkyProviderOrbit.starGLCallList + 2;
            GL11.glNewList(SkyProviderOrbit.glSkyList2, GL11.GL_COMPILE);
            f = -16F;
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

            for (int k = -byte2 * i; k <= byte2 * i; k += byte2) {
                for (int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2) {
                    worldRenderer.pos(k + byte2, f, i1 + 0).endVertex();
                    worldRenderer.pos(k + 0, f, i1 + 0).endVertex();
                    worldRenderer.pos(k + 0, f, i1 + byte2).endVertex();
                    worldRenderer.pos(k + byte2, f, i1 + byte2).endVertex();
                }
            }

            tessellator.draw();
            GL11.glEndList();
        }
        displayListsInitialized = true;
    }
    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc)
    {

        if (!this.displayGalaxyImg) {
            for(int i = 0; i < 6; ++i) {
                galaxyTextures[i] = new ResourceLocation(GTQTSpace.MODID, "textures/environment/background/" + "milky_way" + "/" + "milky_way" + "_" + (i + 1) + ".png");
            }

            this.displayGalaxyImg = true;
        }


        final float var20 = 400.0F + (float) this.minecraft.player.posY / 2F;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GlStateManager.disableRescaleNormal();
        final Vec3d var2 = this.minecraft.world.getSkyColor(this.minecraft.getRenderViewEntity(), partialTicks);
        float var3 = (float) var2.x;
        float var4 = (float) var2.y;
        float var5 = (float) var2.z;
        float var8;

        if (this.minecraft.gameSettings.anaglyph)
        {
            final float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
            final float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
            var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
            var3 = var6;
            var4 = var7;
            var5 = var8;
        }

        GL11.glColor3f(var3, var4, var5);
        final Tessellator var23 = Tessellator.getInstance();
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor3f(var3, var4, var5);
        GL11.glCallList(SkyProviderOrbit.glSkyList);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.disableStandardItemLighting();
        final float[] var24 = this.minecraft.world.provider.calcSunriseSunsetColors(this.minecraft.world.getCelestialAngle(partialTicks), partialTicks);
        float var9;
        float var10;
        float var11;
        float var12;

        if (var24 != null)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(MathHelper.sin(this.minecraft.world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            var8 = var24[0];
            var9 = var24[1];
            var10 = var24[2];
            float var13;

            if (this.minecraft.gameSettings.anaglyph)
            {
                var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
                var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
                var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
                var8 = var11;
                var9 = var12;
                var10 = var13;
            }

            BufferBuilder worldRenderer = var23.getBuffer();
            worldRenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(0.0D, 100.0D, 0.0D).color(var8, var9, var10, var24[3]).endVertex();
            final byte var26 = 16;

            for (int var27 = 0; var27 <= var26; ++var27)
            {
                var13 = var27 * (float) Math.PI * 2F / var26;
                final float var14 = MathHelper.sin(var13);
                final float var15 = MathHelper.cos(var13);
                worldRenderer.pos(var14 * 120.0F, var15 * 120.0F, -var15 * 40.0F * var24[3]).color(var24[0], var24[1], var24[2], 0.0F).endVertex();
            }

            var23.draw();
            GL11.glPopMatrix();
            GL11.glShadeModel(GL11.GL_FLAT);
        }

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glPushMatrix();
        var8 = 1.0F - this.minecraft.world.getRainStrength(partialTicks);
        var9 = 0.0F;
        var10 = 0.0F;
        var11 = 0.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, var8);
        GL11.glTranslatef(var9, var10, var11);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);

        // Code for rendering spinning spacestations
        float deltaTick = partialTicks - this.prevPartialTicks;
        // while (deltaTick < 0F) deltaTick += 1.0F;
        this.prevPartialTicks = partialTicks;
        long curTick = this.minecraft.world.getTotalWorldTime();
        int tickDiff = (int) (curTick - this.prevTick);
        this.prevTick = curTick;
        if (tickDiff > 0 && tickDiff < 20)
        {
            deltaTick += tickDiff;
        }
        this.spinAngle = this.spinAngle - this.spinDeltaPerTick * deltaTick;
        while (this.spinAngle < -180F)
        {
            this.spinAngle += 360F;
        }
        GL11.glRotatef(this.spinAngle, 0.0F, 1.0F, 0.0F);

        // At 0.8, these will look bright against a black sky - allows some
        // headroom for them to
        // look even brighter in outer dimensions (further from the sun)
        GL11.glColor4f(0.8F, 0.8F, 0.8F, 0.8F);
        GL11.glCallList(SkyProviderOrbit.starGLCallList);

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPushMatrix();
        float celestialAngle = this.minecraft.world.getCelestialAngle(partialTicks);
        GL11.glRotatef(celestialAngle * 360.0F, 1.0F, 0.0F, 0.0F);


        ///////////////////////////////星星生成///////////////////////////////

        ///////////////////////////////银河系背景生成///////////////////////////////
        if (GTQTSConfig.Render.BackgroundRender) {
            float x = 50.0F;
            float y = -90.0F;
            this.renderImage(galaxyTextures[0], (-4.0F + x) % 360.0F, y, 0.0F, 60.0F);
            this.renderImage(galaxyTextures[1], (58.0F + x) % 360.0F, y, 0.0F, 60.0F);
            this.renderImage(galaxyTextures[2], (120.0F + x) % 360.0F, y, 0.0F, 60.2F);
            this.renderImage(galaxyTextures[3], (182.0F + x) % 360.0F, y, 0.0F, 60.0F);
            this.renderImage(galaxyTextures[4], (244.0F + x) % 360.0F, y, 0.0F, 60.2F);
            this.renderImage(galaxyTextures[5], (306.0F + x) % 360.0F, y, 0.0F, 60.0F);

            this.renderImage(smcTexture, -70.0F, -140.0F, 20.0F, 5.0F);
            this.renderImage(andromedaTexture, -70.0F, -150.0F, 20.0F, 4.0F);
            this.renderImage(eta_carinaeTexture, 35.0F, 120.0F, 0.0F, 20.0F);
            this.renderImage(pleiadesTexture, -75.0F, 120.0F, 0.0F, 6.0F);
            this.renderImage(barnardaloopTexture, -25.0F, 160.0F, 0.0F, 6.0F);
            this.renderImage(lmcTexture, 80.0F, -120.0F, -55.0F, 3.0F);
            this.renderImage(triangulumTexture, 50.0F, -160.0F, -55.0F, 3.0F);
        }
        ///////////////////////////////太阳生成///////////////////////////////
        // 如果渲染太阳的标志位为真，则执行以下渲染逻辑
        if (this.renderSun)
        {
            // 设置blend功能，用于透明度处理
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            // 禁用纹理，以便渲染纯色的太阳光晕
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            // 设置渲染颜色，这里为黑色，因为太阳光晕是通过透明度来表现的
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
            // 定义太阳光晕的大小
            var12 = 8.0F;
            // 获取BufferBuilder对象，用于绘制图形
            BufferBuilder worldRenderer = var23.getBuffer();
            // 开始绘制四边形，用于太阳光晕的内层
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
            worldRenderer.pos(-var12, 99.9D, -var12).endVertex();
            worldRenderer.pos(var12, 99.9D, -var12).endVertex();
            worldRenderer.pos(var12, 99.9D, var12).endVertex();
            worldRenderer.pos(-var12, 99.9D, var12).endVertex();
            // 绘制完成，调用draw方法渲染
            var23.draw();
            // 重新启用纹理，为接下来的太阳本体渲染做准备
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            // 调整blend功能设置，以便太阳本体可以正确显示
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            // 恢复颜色设置为白色，用于太阳本体的正常渲染
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            // 定义太阳本体的大小
            var12 = 28.0F;
            // 绑定太阳纹理，准备绘制太阳本体
            this.minecraft.renderEngine.bindTexture(SkyProviderOrbit.sunTexture);
            // 开始绘制带有纹理的四边形，用于太阳本体
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(-var12*0.5, 100.0D, -var12*0.5).tex(0.0D, 0.0D).endVertex();
            worldRenderer.pos(var12*0.5, 100.0D, -var12*0.5).tex(1.0D, 0.0D).endVertex();
            worldRenderer.pos(var12*0.5, 100.0D, var12*0.5).tex(1.0D, 1.0D).endVertex();
            worldRenderer.pos(-var12*0.5, 100.0D, var12*0.5).tex(0.0D, 1.0D).endVertex();
            // 完成绘制，调用draw方法渲染太阳本体
            var23.draw();
        }
        ///////////////////////////////月球生成///////////////////////////////
        if (this.renderMoon)
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
            var12 = 11.3F;
            BufferBuilder worldRenderer = var23.getBuffer();
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
            worldRenderer.pos(-var12, -99.9D, var12).endVertex();
            worldRenderer.pos(var12, -99.9D, var12).endVertex();
            worldRenderer.pos(var12, -99.9D, -var12).endVertex();
            worldRenderer.pos(-var12, -99.9D, -var12).endVertex();
            var23.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            var12 = 40.0F;
            this.minecraft.renderEngine.bindTexture(SkyProviderOrbit.moonTexture);
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

            worldRenderer.pos(-var12*0.4, -100.0D, var12*0.4).tex(0, 0).endVertex();
            worldRenderer.pos(var12*0.4, -100.0D, var12*0.4).tex(1, 0).endVertex();
            worldRenderer.pos(var12*0.4, -100.0D, -var12*0.4).tex(1, 1).endVertex();
            worldRenderer.pos(-var12*0.4, -100.0D, -var12*0.4).tex(0, 1).endVertex();
            var23.draw();
        }

        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        ///////////////////////////////地球生成///////////////////////////////
        if (this.planetToRender != null)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -var20 / 10, 0.0F);
            float scale = 100 * (0.3F - var20 / 10000.0F);
            scale = Math.max(scale, 0.2F);
            GL11.glScalef(scale, 0.0F, scale);
            GL11.glTranslatef(0.0F, -var20, 0.0F);
            GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            this.minecraft.renderEngine.bindTexture(this.planetToRender);

            var10 = 1.0F;
            final float alpha = 0.5F;
            GL11.glColor4f(Math.min(alpha, 1.0F), Math.min(alpha, 1.0F), Math.min(alpha, 1.0F), Math.min(alpha, 1.0F));
            BufferBuilder worldRenderer = var23.getBuffer();
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(-var10*6, 0, var10*6).tex(0D, 1.0).endVertex();
            worldRenderer.pos(var10*6, 0, var10*6).tex(1.0, 1.0).endVertex();
            worldRenderer.pos(var10*6, 0, -var10*6).tex(1.0, 0D).endVertex();
            worldRenderer.pos(-var10*6, 0, -var10*6).tex(0D, 0D).endVertex();
            var23.draw();
            GL11.glPopMatrix();
        }




        /////////////////////////////////////////////////

        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_ALPHA_TEST);

        GL11.glColor3f(0.0F, 0.0F, 0.0F);

        GlStateManager.enableRescaleNormal();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glDepthMask(true);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_BLEND);
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
    protected void renderImage(ResourceLocation image, float x, float y, float z, float size) {
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
    private void renderStars()
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
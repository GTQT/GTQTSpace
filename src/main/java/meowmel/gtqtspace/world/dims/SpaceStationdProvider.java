package meowmel.gtqtspace.world.dims;

import meowmel.gtqtspace.GTQTSpace;
import meowmel.gtqtspace.client.skyRender.SkyProviderOrbit;
import meowmel.gtqtspace.world.worldgen.Chuckgenerator.SpaceStationdGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static meowmel.gtqtspace.world.worldgen.GTQTSDimensionType.SPACE_STATION_TYPE;

public class SpaceStationdProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return SPACE_STATION_TYPE;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new SpaceStationdGenerator(world);
    }

    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return -50;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return new Vec3d(0, 0, 0);
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        return new Vec3d(0, 0, 0);
    }

    @Override
    public long getWorldTime() {

        return super.getWorldTime();
    }

    @Override
    public boolean isDaytime() {

        return super.isDaytime();
    }

    @Override
    public void updateWeather() {
    }

    @Override
    public void calculateInitialWeather() {
    }

    @Nullable
    @Override
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {

        return super.calcSunriseSunsetColors(celestialAngle, partialTicks);
    }

    public int getRespawnDimension(net.minecraft.entity.player.EntityPlayerMP player) {
        return 50;
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public net.minecraftforge.client.IRenderHandler getSkyRenderer() {
        super.setSkyRenderer(new SkyProviderOrbit(new ResourceLocation(GTQTSpace.MODID, "textures/gui/celestialbodies/earth.png"), true, true, true));
        return super.getSkyRenderer();
    }
}
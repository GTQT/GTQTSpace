package meowmel.gtqtspace.common.metatileentities.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

import static gregtech.api.GTValues.V;
import static net.minecraft.tileentity.TileEntity.INFINITE_EXTENT_AABB;

public class MetaTileEntityWindGenerator extends MultiblockWithDisplayBase implements IFastRenderMetaTileEntity {
    int speed;
    private IEnergyContainer energyContainer;

    public MetaTileEntityWindGenerator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("speed", speed);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        speed = data.getInteger("speed");
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityWindGenerator(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        if (isStructureFormed()) {
            this.energyContainer.addEnergy((long) (V[3] * Math.pow(2, speed)) * getWeatherFactor());
        }
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (speed == 0) {
            textList.add(new TextComponentTranslation("当前维度不能使用风力发电机！"));
        }

    }


    public void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation(">>发电量：" + (V[4] * Math.pow(2, speed)) * getWeatherFactor()));
        textList.add(new TextComponentTranslation(">>天气增幅：" + getWeatherFactor()));
        textList.add(new TextComponentTranslation(">>维度增幅：" + speed));
    }

    private int getWeatherFactor() {
        // 考虑更多天气情况
        if (getWorld().isRaining()) {
            return 2;
        } else if (getWorld().isThundering()) {
            return 3; // 雷暴天气
        } else {
            return 1; // 正常天气
        }
    }

    private IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));

        switch (this.getWorld().provider.getDimension()) {
            case 52: {
                speed = 2;
                break;
            }
            case 53: {
                speed = 4;
                break;
            }
            case -1, 1: {
                speed = 0;
                break;
            }
            case 0:
            default: {
                speed = 1;
                break;
            }
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("AA   AA", "B     B", "B     B", "AAAAAAA", "       ")
                .aisle("AAAAAAA", " AAAAA ", " AAAAA ", "AAAAAAA", "       ")
                .aisle(" AAAAA ", " AB BA ", " AB BA ", "AAB BAA", "  B B  ")
                .aisle(" AAAAA ", " A   A ", " A   A ", "AA   AA", "       ")
                .aisle(" AAAAA ", " AB BA ", " AB BA ", "AAB BAA", "  B B  ")
                .aisle("AAAAAAA", " ACCCA ", " AASAA ", "AAAAAAA", "       ")
                .aisle("AA   AA", "B     B", "B     B", "AAAAAAA", "       ")
                .where('S', selfPredicate())
                .where(' ', any())
                .where('B', states(getFrameState()))
                .where('C',
                        states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID))
                                .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                                .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                )
                .where('A', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID)))
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public boolean isGlobalRenderer() {
        return true;
    }

    //渲染模型的位置
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true, true);
    }
}

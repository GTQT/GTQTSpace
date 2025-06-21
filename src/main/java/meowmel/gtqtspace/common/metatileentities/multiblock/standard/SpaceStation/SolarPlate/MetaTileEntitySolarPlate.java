package meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SolarPlate;


import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing1;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.GTValues.*;
import static meowmel.gtqtspace.api.predicate.TiredTraceabilityPredicate.CP_SP_CASING;

public class MetaTileEntitySolarPlate extends MultiblockWithDisplayBase{

    private IEnergyContainer energyContainer;
    boolean isSpaceStation;
    boolean connect;
    int tier;

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("tier", tier);
        data.setBoolean("connect",connect);
        data.setBoolean("isSpaceStation",isSpaceStation);
        return super.writeToNBT(data);
    }


    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        tier=data.getInteger("tier");
        connect =data.getBoolean("connect");
        isSpaceStation =data.getBoolean("isSpaceStation");
    }
    public MetaTileEntitySolarPlate(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        if(!connect)this.energyContainer.addEnergy(geteu());
    }
    public long geteu()
    {
        return isStructureFormed()?(long) (V[4] *Math.pow(2,tier-1) * (isSpaceStation?2:1)* (checkNaturalLighting()?2:1)):0;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySolarPlate(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCC")
                .aisle("CSSSC")
                .aisle("CSSSC")
                .aisle("CSSSC")
                .aisle("CCXCC")
                .where('X', selfPredicate())
                .where('S', CP_SP_CASING.get())
                .where('C', states(GTQTSMetaBlocks.multiblockCasing1.getState(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_CASING))
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMaxGlobalLimited(1)))
                .build();
    }
    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (!checkNaturalLighting()) {
            textList.add(new TextComponentTranslation("夜间模式"));
        }
        if (!isSpaceStation) {
            textList.add(new TextComponentTranslation("效能模式"));
        }
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("我不是戴森球", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.spa.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.spa.tooltip.2"));
    }
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.tire", tier));
        textList.add(new TextComponentTranslation("连接模式：%s", connect));
        textList.add(new TextComponentTranslation("发电量：%s "+VN[GTUtility.getTierByVoltage(geteu())], geteu()));
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        isSpaceStation=this.getWorld().provider.getDimension()==50;

        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        Object tier = context.get("SPTieredStats");
        this.tier = GTQTUtil.getOrDefault(() -> tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tier).getIntTier(),
                0);
        this.writeCustomData(GTQTValue.UPDATE_TIER3, buf -> buf.writeInt(this.tier));
    }
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER3){
            this.tier = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE3){
            this.writeCustomData(GTQTValue.UPDATE_TIER3,buf1 -> buf1.writeInt(this.tier));
        }
    }
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.SOLAR_PLATE_CASING;
    }
    private boolean isWorkingEnabled() {
        return isStructureFormed();
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                this.isWorkingEnabled());
    }
    public boolean checkNaturalLighting() {

        if (!this.getWorld().isDaytime())
            return false;
        for (BlockPos pos : BlockPos.getAllInBox(this.getPos().up(8).offset(this.frontFacing.rotateY(), 3),
                this.getPos().up(8).offset(this.getFrontFacing().rotateYCCW(), 3).offset(this.getFrontFacing().getOpposite(), 6))) {
            if (!this.getWorld().canSeeSky(pos.up())) {
                return false;
            }
        }
        return true;
    }
}
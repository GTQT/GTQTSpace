package meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.hatch;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.AbilityInstances;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import meowmel.gtqtspace.api.multiblock.GTQTSMultiblockAbility;
import meowmel.gtqtspace.api.multiblock.ISpaceElevatorProvider;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.MetaTileEntitySpaceElevator;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules.MetaTileEntitySpaceElevatorModules;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.List;

//这里是电梯本体需要安装的模块
public class MetaTileEntityElevatorProviderHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<ISpaceElevatorProvider>, ISpaceElevatorProvider {

    MetaTileEntitySpaceElevatorModules elevatorReceiver;

    public MetaTileEntityElevatorProviderHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 9);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityElevatorProviderHatch(metaTileEntityId);
    }

    @Override
    public void registerAbilities(AbilityInstances abilityInstances) {
        abilityInstances.add(this);
    }

    @Override
    public MultiblockAbility<ISpaceElevatorProvider> getAbility() {
        return GTQTSMultiblockAbility.SpaceElevatorProvider_MULTIBLOCK_ABILITY;
    }

    @Override
    public void update() {
        super.update();
        if (this.getController() == null) return;
        if (elevatorReceiver == null && this.getController().isStructureFormed()) {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().offset(getFrontFacing()));
            if (mte instanceof MetaTileEntityElevatorReceiverHatch hatch) {
                if (hatch.getController() != null && hatch.getController().isStructureFormed()) {
                    elevatorReceiver = hatch.getModules();
                    elevatorReceiver.setSpaceElevator(this);
                }
            } else elevatorReceiver = null;
        } else if (this.getOffsetTimer() % 20 == 0) elevatorReceiver.setSpaceElevator(this);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 180, 240)
                .widget(new DynamicLabelWidget(7, 7, () -> "电梯链路发送仓"))
                .image(4, 20, 172, 136, GuiTextures.DISPLAY)
                .widget((new AdvancedTextWidget(8, 24, this::addDisplayText, 16777215)).setMaxWidthLimit(180))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 160);
        return builder.build(this.getHolder(), entityPlayer);
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("坐标: " + this.getPos()));
        textList.add(new TextComponentString("面向: " + this.getFrontFacing()));
        textList.add(new TextComponentString("正在获取: " + this.getPos().offset(getFrontFacing())));
        if (elevatorReceiver != null)
            textList.add(new TextComponentString("链接设备: " + elevatorReceiver.getMetaName()));
    }

    public MetaTileEntitySpaceElevator getSpaceElevator() {
        return (MetaTileEntitySpaceElevator) this.getController();
    }

    public void sentWorkingDisabled() {
        if (elevatorReceiver != null) {
            elevatorReceiver.sentWorkingDisabled();
        }
    }

    public void sentWorkingEnabled() {
        if (elevatorReceiver != null) {
            elevatorReceiver.sentWorkingEnabled();
        }
    }

    @Override
    public MetaTileEntitySpaceElevatorModules getModule() {
        return elevatorReceiver == null ? null : elevatorReceiver;
    }

    @Override
    public int getMotorTier() {
        if (getSpaceElevator() == null) return 0;
        return getSpaceElevator().getMotorTier();
    }

    @Override
    public IEnergyContainer getEnergyContainerForModules() {
        if (getSpaceElevator() == null) return null;
        return getSpaceElevator().getEnergyContainerForModules();
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return getSpaceElevator().getComputationProvider();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            Textures.OPTICAL_DATA_ACCESS_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

}

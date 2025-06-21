package meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.hatch;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
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
import meowmel.gtqtspace.api.multiblock.ISpaceElevatorReceiver;
import meowmel.gtqtspace.api.multiblock.SpaceModulesType;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.MetaTileEntitySpaceElevator;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules.MetaTileEntitySpaceElevatorModules;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.List;

//这里是电梯模块需要安装的模块
public class MetaTileEntityElevatorReceiverHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<ISpaceElevatorReceiver>, ISpaceElevatorReceiver {
    MetaTileEntitySpaceElevator elevator;
    ISpaceElevatorProvider elevatorProvider;

    public MetaTileEntityElevatorReceiverHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 9);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityElevatorReceiverHatch(metaTileEntityId);
    }

    @Override
    public void registerAbilities(AbilityInstances abilityInstances) {
        abilityInstances.add(this);
    }

    @Override
    public MultiblockAbility<ISpaceElevatorReceiver> getAbility() {
        return GTQTSMultiblockAbility.SpaceElevatorReceiver_MULTIBLOCK_ABILITY;
    }

    @Override
    public void update() {
        super.update();
        if (this.getController() == null) return;
        if (elevator != null && elevatorProvider != null) return;
        if (this.getController().isStructureFormed()) {
            BlockPos offsetPos = this.getPos().offset(getFrontFacing());
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), offsetPos);
            if (mte instanceof MetaTileEntityElevatorProviderHatch hatch) {
                if (elevator == null) {
                    elevator = hatch.getSpaceElevator();
                }
                if (elevatorProvider == null) {
                    elevatorProvider = hatch;
                }
            }
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 180, 240)
                .widget(new DynamicLabelWidget(7, 7, () -> "电梯链路接收仓"))
                .image(4, 20, 172, 136, GuiTextures.DISPLAY)
                .widget((new AdvancedTextWidget(8, 24, this::addDisplayText, 16777215)).setMaxWidthLimit(180))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 160);
        return builder.build(this.getHolder(), entityPlayer);
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("坐标: " + this.getPos()));
        textList.add(new TextComponentString("面向: " + this.getFrontFacing()));
        textList.add(new TextComponentString("正在获取: " + this.getPos().offset(getFrontFacing())));
        if (elevator != null)
            textList.add(new TextComponentString("链接电梯: " + elevator.getMotorTier()));
    }

    public MetaTileEntitySpaceElevatorModules getModules() {
        return (MetaTileEntitySpaceElevatorModules) this.getController();
    }

    @Override
    public ISpaceElevatorProvider getSpaceProvider() {
        return elevatorProvider;
    }

    @Override
    public void setSpaceElevator(ISpaceElevatorProvider provider) {
        getModules().setSpaceElevator(provider);
    }

    @Override
    public void sentWorkingDisabled() {
        getModules().sentWorkingDisabled();
    }

    @Override
    public void sentWorkingEnabled() {
        getModules().sentWorkingEnabled();
    }

    @Override
    public SpaceModulesType getModuleType() {
        return getModules().getModuleType();
    }

    @Override
    public String getNameForDisplayCount() {
        return this.getController().getMetaName() + ".display_count";
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            Textures.OPTICAL_DATA_ACCESS_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

}


package keqing.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.hatch;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.AbilityInstances;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.GTUtility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtspace.api.multiblock.GTQTSMultiblockAbility;
import keqing.gtqtspace.api.multiblock.ISpaceElevatorProvider;
import keqing.gtqtspace.api.multiblock.ISpaceElevatorReceiver;
import net.minecraft.util.ResourceLocation;

//这里是电梯模块需要安装的模块
public class MetaTileEntityElevatorReceiverHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<ISpaceElevatorReceiver> {
    ISpaceElevatorProvider elevatorProvider;

    public MetaTileEntityElevatorReceiverHatch(ResourceLocation metaTileEntityId) {
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
    public MultiblockAbility<ISpaceElevatorReceiver> getAbility() {
        return GTQTSMultiblockAbility.SpaceElevatorReceiver_MULTIBLOCK_ABILITY;
    }

    @Override
    public void update() {
        super.update();
        if (this.getController().isStructureFormed()) {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().offset(getFrontFacing()));
            if (mte instanceof MetaTileEntityElevatorProviderHatch hatch) {
                elevatorProvider = hatch.getSpaceElevator();
            }
        }
        elevatorProvider = null;
    }

    public ISpaceElevatorReceiver getModules() {
        return (ISpaceElevatorReceiver) this.getController();
    }

    public IEnergyContainer getEnergyContainerForModules() {
        return elevatorProvider.getEnergyContainerForModules();
    }

    public IOpticalComputationProvider getComputationProvider() {
        return elevatorProvider.getComputationProvider();
    }

    public int getMotorTier() {
        return elevatorProvider.getMotorTier();
    }
}


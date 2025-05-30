package keqing.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.hatch;

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

//这里是电梯本体需要安装的模块
public class MetaTileEntityElevatorProviderHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<ISpaceElevatorProvider> {

    ISpaceElevatorReceiver elevatorReceiver;

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
        if(this.getController()==null)return;
        if (this.getController().isStructureFormed()) {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().offset(getFrontFacing()));
            if (mte instanceof MetaTileEntityElevatorReceiverHatch hatch) {
                elevatorReceiver = hatch.getModules();
                elevatorReceiver.setSpaceElevator(getSpaceElevator());
            }
        }
        elevatorReceiver = null;
    }

    public ISpaceElevatorProvider getSpaceElevator() {
        return (ISpaceElevatorProvider) this.getController();
    }

    public void sentWorkingDisabled() {
        if(elevatorReceiver!=null)
        {
            elevatorReceiver.sentWorkingEnabled();
        }
    }

    public void sentWorkingEnabled() {
        if(elevatorReceiver!=null)
        {
            elevatorReceiver.sentWorkingEnabled();
        }
    }
}

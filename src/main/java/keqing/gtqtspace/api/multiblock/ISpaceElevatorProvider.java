package keqing.gtqtspace.api.multiblock;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IOpticalComputationProvider;

public interface ISpaceElevatorProvider {
    int getMotorTier();

    IEnergyContainer getEnergyContainerForModules();

    boolean amIInTheList(ISpaceElevatorReceiver receiver);

    IOpticalComputationProvider getComputationProvider();
}

package meowmel.gtqtspace.api.multiblock;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IOpticalComputationProvider;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules.MetaTileEntitySpaceElevatorModules;

//这里是电梯需要实现的接口
public interface ISpaceElevatorProvider {
    //返回电梯 磁轨加速器 等级
    int getMotorTier();
    //返回电梯 能源仓 等级
    IEnergyContainer getEnergyContainerForModules();
    //返回电梯 算力仓 等级
    IOpticalComputationProvider getComputationProvider();

    void sentWorkingDisabled();
    void sentWorkingEnabled();

    MetaTileEntitySpaceElevatorModules getModule();
}

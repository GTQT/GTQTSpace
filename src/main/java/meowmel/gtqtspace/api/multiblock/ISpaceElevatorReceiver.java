package meowmel.gtqtspace.api.multiblock;

import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.MetaTileEntitySpaceElevator;

//这里是模块需要实现的接口
public interface ISpaceElevatorReceiver {

    //获取模块的 电梯主体
    ISpaceElevatorProvider getSpaceProvider();

    //设置模块的 电梯主体
    void setSpaceElevator(ISpaceElevatorProvider provider);

    //向模块发送全部关机指令
    void sentWorkingDisabled();

    //向模块发送全部开机指令
    void sentWorkingEnabled();

    //改为 Type+Tier
    SpaceModulesType getModuleType();

    String getNameForDisplayCount();
}

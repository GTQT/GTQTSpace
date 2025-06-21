package meowmel.gtqtspace.integration.provider;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import meowmel.gtqtspace.GTQTSpace;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules.MetaTileEntitySpaceElevatorModules;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SpaceModulerInfoProvider implements IProbeInfoProvider{

    @Override
    public String getID() {
        return GTQTSpace.MODID + ":energy_container_provider";
    }


    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        if (iBlockState.getBlock().hasTileEntity(iBlockState)) {
            TileEntity te = world.getTileEntity(iProbeHitData.getPos());
            IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                if (mte instanceof MetaTileEntitySpaceElevatorModules modules) {
                    IEnergyContainer capability = modules.getEnergyContainer();
                    long maxStorage =capability.getEnergyCapacity();
                    long stored = capability.getEnergyStored();
                    if (maxStorage == 0) return; // do not add empty max storage progress bar
                    horizontalPane.progress(capability.getEnergyStored(), maxStorage, iProbeInfo.defaultProgressStyle()
                            .numberFormat(entityPlayer.isSneaking() || stored < 10000 ?
                                    NumberFormat.COMMAS :
                                    NumberFormat.COMPACT)
                            .suffix(" / " + (entityPlayer.isSneaking() || maxStorage < 10000 ?
                                    ElementProgress.format(maxStorage, NumberFormat.COMMAS, " EU") :
                                    ElementProgress.format(maxStorage, NumberFormat.COMPACT, "EU")))
                            .filledColor(0xFFEEE600)
                            .alternateFilledColor(0xFFEEE600)
                            .borderColor(0xFF555555));
                }
            }
        }

    }
}

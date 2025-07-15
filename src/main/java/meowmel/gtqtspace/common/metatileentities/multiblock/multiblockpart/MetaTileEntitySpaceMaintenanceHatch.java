package meowmel.gtqtspace.common.metatileentities.multiblock.multiblockpart;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.DummyCleanroom;
import gregtech.api.metatileentity.multiblock.ICleanroomReceiver;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntitySterileCleaningMaintenanceHatch;
import meowmel.gtqtspace.api.multiblock.GTQTSpaceMultiblockController;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class MetaTileEntitySpaceMaintenanceHatch extends MetaTileEntitySterileCleaningMaintenanceHatch {

    public MetaTileEntitySpaceMaintenanceHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySpaceMaintenanceHatch(metaTileEntityId);
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);
        if (controllerBase instanceof GTQTSpaceMultiblockController controller) {
            if (controller.isInSpace()) {
                ICleanroomReceiver cleanroomReceiver = (ICleanroomReceiver) controllerBase;
                cleanroomReceiver.setCleanroom(DummyCleanroom.createForTypes(CLEANED_TYPES));
            }
        }
    }

    @Override
    public int getTier() {
        return GTValues.IV;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        tooltip.add(TextFormatting.GREEN + I18n.format("真空微重力环境："));
        tooltip.add(TextFormatting.GRAY + I18n.format("在太空中使用可直接获得无菌超净效果"));
        tooltip.add(TextFormatting.GRAY + I18n.format("需要多方块类型：真空微重力多方块"));
        super.addInformation(stack, player, tooltip, advanced);
    }
}

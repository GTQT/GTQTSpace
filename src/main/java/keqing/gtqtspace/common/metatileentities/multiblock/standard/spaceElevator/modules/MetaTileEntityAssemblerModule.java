package keqing.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import keqing.gtqtspace.api.multiblock.SpaceModulesType;
import keqing.gtqtspace.client.textures.GTQTSTextures;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSpaceElevatorCasing;
import keqing.gtqtspace.common.metatileentities.GTQTSMetaTileEntities;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetaTileEntityAssemblerModule extends MetaTileEntitySpaceElevatorModules{

    public MetaTileEntityAssemblerModule(ResourceLocation metaTileEntityId, int tier, SpaceModulesType type) {
        super(metaTileEntityId, tier, type);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityAssemblerModule(this.metaTileEntityId, this.tier, this.type);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("C", "C", "C", "C", "C")
                .aisle("H", "C", "C", "S", "C")
                .where('S', selfPredicate())
                .where('C', states(GTQTSMetaBlocks.spaceElevatorCasing.getState(GTQTSpaceElevatorCasing.ElevatorCasingType.BASIC_CASING))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                )
                .where('H', metaTileEntities(GTQTSMetaTileEntities.ELEVATOR_RECEIVER_HATCH))
                .build();
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        for (EnumFacing renderSide : EnumFacing.HORIZONTALS) {
            if (renderSide == getFrontFacing()) {
                getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                        true);
            } else
                GTQTSTextures.ASSEMBLER_MODULE_OVERLAY.renderSided(renderSide, renderState, translation, pipeline);
        }
    }
}

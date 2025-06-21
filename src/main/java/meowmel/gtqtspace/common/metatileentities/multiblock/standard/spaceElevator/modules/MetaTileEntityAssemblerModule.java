package meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.utils.TooltipHelper;
import meowmel.gtqtspace.api.multiblock.SpaceModulesType;
import meowmel.gtqtspace.client.textures.GTQTSTextures;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSpaceElevatorCasing;
import meowmel.gtqtspace.common.metatileentities.GTQTSMetaTileEntities;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityAssemblerModule extends MetaTileEntitySpaceElevatorModules {

    public MetaTileEntityAssemblerModule(ResourceLocation metaTileEntityId, int tier, SpaceModulesType type) {
        super(metaTileEntityId, tier, type);
        this.recipeMapWorkable.setParallelLimit((int) Math.pow(4, this.tier));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityAssemblerModule(this.metaTileEntityId, this.tier, this.type);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("H", "C", "C", "C", "C")
                .aisle("C", "C", "C", "S", "C")
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

    @Override
    public void addInformation(ItemStack stack, World world, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁把我的装配线发送到天上去了", new Object[0]));
        super.addInformation(stack, world, tooltip, advanced);
    }
}

package meowmel.gtqtspace.common.metatileentities.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.utils.GTQTUtil;
import meowmel.gtqtspace.api.multiblock.GTQTSpaceMultiblockController;
import meowmel.gtqtspace.client.textures.GTQTSTextures;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static meowmel.gtqtspace.api.predicate.TiredTraceabilityPredicate.*;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.CAZ_CASING;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.CAZ_HEAT_VENT;

public class MetaTileEntityIndustrialCutter extends GTQTSpaceMultiblockController {

    private int motorCasingTier;
    private int conveyorCasingTier;

    /* ------------------------------- MetaTileEntity constructors ------------------------------- */
    public MetaTileEntityIndustrialCutter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.CUTTER_RECIPES,
                RecipeMaps.LATHE_RECIPES
        });
        this.recipeMapWorkable = new IndustrialCutterRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialCutter(metaTileEntityId);
    }

    /* ----------------------------- Create MetaTileEntity structure ----------------------------- */
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type1 = context.get("MotorCasingTieredStats");
        Object type2 = context.get("ConveyorCasingTieredStats");
        this.motorCasingTier = GTQTUtil.getOrDefault(
                () -> type1 instanceof WrappedIntTired,
                () -> ((WrappedIntTired) type1).getIntTier(), 0
        );
        this.conveyorCasingTier = GTQTUtil.getOrDefault(
                () -> type2 instanceof WrappedIntTired,
                () -> ((WrappedIntTired) type2).getIntTier(), 0
        );
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.motorCasingTier = 0;
        this.conveyorCasingTier = 0;
        // this.length = 0;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCC", "CCC#C", "  C#C")
                .aisle("CCCCC", "CCOMC", "  C#C")
                .aisle("CCCCC", "CSC#C", "  C#C")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .setMinGlobalLimited(12)
                        .or(this.autoAbilities(true, true, true, true, true, false, false)))
                .where('M', MOTOR_CASING.get())
                .where('O', CONVEYOR_CASING.get())
                .where('#', air())
                .where(' ', any())
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTSMetaBlocks.multiblockCasing.getState(CAZ_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart texture) {
        return GTQTSTextures.CAZ_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.CUTTER_OVERLAY;
    }

    /* ------------------------------- MetaTileEntity Descriptions ------------------------------- */
    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtspace.machine.industrial_cutter.tooltip.1"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_cutter.tooltip.2"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_cutter.tooltip.3"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_cutter.tooltip.4"));
    }


    /* ---------------------------------- MetaTileEntity Logics ---------------------------------- */
    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class IndustrialCutterRecipeLogic extends SpaceMultiblockRecipeLogic {

        public IndustrialCutterRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected double getOverclockingDurationFactor() {
            return this.getMaxVoltage() >= GTValues.V[8] ? 0.25 : 0.5;
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (Math.floor(maxProgress * Math.pow(0.8, motorCasingTier))));
        }

        @Override
        public int getParallelLimit() {
            return 16 * conveyorCasingTier;
        }


    }
}

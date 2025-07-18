package meowmel.gtqtspace.common.metatileentities.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.utils.GTQTUtil;
import meowmel.gtqtspace.api.multiblock.GTQTSpaceMultiblockController;
import meowmel.gtqtspace.api.recipes.GTQTSpaceRecipeMaps;
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

import static meowmel.gtqtspace.api.predicate.TiredTraceabilityPredicate.CONVEYOR_CASING;
import static meowmel.gtqtspace.api.predicate.TiredTraceabilityPredicate.ROBOT_ARM_CASING;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.IAZ_CASING;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.IAZ_HEAT_VENT;

public class MetaTileEntityIndustrialAssembler extends GTQTSpaceMultiblockController {

    private int robotArmCasingTier;
    private int conveyorCasingTier;

    /* ------------------------------- MetaTileEntity constructors ------------------------------- */
    public MetaTileEntityIndustrialAssembler(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.ASSEMBLER_RECIPES,
                GTQTSpaceRecipeMaps.PRECISION_COMPONENT_ASSEMBLY
        });
        this.recipeMapWorkable = new IndustrialAssemblerRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialAssembler(metaTileEntityId);
    }

    /* ----------------------------- Create MetaTileEntity structure ----------------------------- */
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type1 = context.get("RobotArmCasingTieredStats");
        Object type2 = context.get("ConveyorCasingTieredStats");
        this.robotArmCasingTier = GTQTUtil.getOrDefault(
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
        this.robotArmCasingTier = 0;
        this.conveyorCasingTier = 0;
        // this.length = 0;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCC", "CCCCCC", "CCCCCC", "CCCCCC")
                .aisle("CCCCCC", "CRPPPC", "CPPPPC", "CCGGGC")
                .aisle("CCCCCC", "CMMMMC", "CP###C", "CCGGGC")
                .aisle("CCCCCC", "CSRRRC", "CCGGGC", "CCGGGC")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .setMinGlobalLimited(12)
                        .or(this.autoAbilities(true, true, true, true, true, false, false)))
                .where('P', states(this.getPipeCasingState()))
                .where('G', states(this.getGlassCasingState()))
                .where('R', ROBOT_ARM_CASING.get())
                .where('M', CONVEYOR_CASING.get())
                .where('#', air())
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTSMetaBlocks.multiblockCasing.getState(IAZ_CASING);
    }

    private IBlockState getPipeCasingState() {
        return GTQTSMetaBlocks.multiblockCasing.getState(IAZ_HEAT_VENT);
    }

    private IBlockState getGlassCasingState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart texture) {
        return GTQTSTextures.IAZ_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PROCESSING_ARRAY_OVERLAY;
    }

    /* ------------------------------- MetaTileEntity Descriptions ------------------------------- */
    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtspace.machine.industrial_assembler.tooltip.1"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_assembler.tooltip.2"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_assembler.tooltip.3"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_assembler.tooltip.4"));
    }


    /* ---------------------------------- MetaTileEntity Logics ---------------------------------- */
    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class IndustrialAssemblerRecipeLogic extends SpaceMultiblockRecipeLogic {

        public IndustrialAssemblerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected double getOverclockingDurationFactor() {
            return this.getMaxVoltage() >= GTValues.V[8] ? 0.25 : 0.5;
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (Math.floor(maxProgress * Math.pow(0.5, conveyorCasingTier))));
        }

        @Override
        public int getParallelLimit() {
            return 16 * robotArmCasingTier;
        }

    }
}

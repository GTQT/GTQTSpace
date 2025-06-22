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
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
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

public class MetaTileEntityIndustrialAutoclave extends MultiMapMultiblockController {

    private int pumpCasingTier;
    private int pistonCasingTier;

    /* ------------------------------- MetaTileEntity constructors ------------------------------- */
    public MetaTileEntityIndustrialAutoclave(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.AUTOCLAVE_RECIPES,
                GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES,
                GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES,
                GTQTcoreRecipeMaps.POLYMERIZATION_RECIPES
        });
        this.recipeMapWorkable = new IndustrialAutoclaveRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialAutoclave(metaTileEntityId);
    }

    /* ----------------------------- Create MetaTileEntity structure ----------------------------- */
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type1 = context.get("PumpCasingTieredStats");
        Object type2 = context.get("PistonCasingTieredStats");
        this.pumpCasingTier = GTQTUtil.getOrDefault(
                () -> type1 instanceof WrappedIntTired,
                () -> ((WrappedIntTired) type1).getIntTier(), 0
        );
        this.pistonCasingTier = GTQTUtil.getOrDefault(
                () -> type2 instanceof WrappedIntTired,
                () -> ((WrappedIntTired) type2).getIntTier(), 0
        );
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.pumpCasingTier = 0;
        this.pistonCasingTier = 0;
        // this.length = 0;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CCC ", " CGC ", " CGC ", " CGC ", " CCC ")
                .aisle("CCCCC", "C###C", "C###C", "C###C", "CCCCC")
                .aisle("CCQCC", "G#B#G", "G#P#G", "G#B#G", "CCCCC")
                .aisle("CCCCC", "C###C", "C###C", "C###C", "CCCCC")
                .aisle(" CCC ", " CSC ", " CGC ", " CGC ", " CCC ")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .setMinGlobalLimited(12)
                        .or(this.autoAbilities(true, true, true, true, true, true, false)))
                .where('B', states(this.getPipeCasingState()))
                .where('G', states(this.getGlassState()))
                .where('P', PUMP_CASING.get())
                .where('Q', PISTON_CASING.get())
                .where('#', air())
                .where(' ', any())
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTSMetaBlocks.multiblockCasing.getState(CAZ_CASING);
    }

    private IBlockState getPipeCasingState() {
        return GTQTSMetaBlocks.multiblockCasing.getState(CAZ_HEAT_VENT);
    }

    private IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart texture) {
        return GTQTSTextures.CAZ_CASING;
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
        tooltip.add(I18n.format("gtqtspace.machine.industrial_autoclave.tooltip.1"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_autoclave.tooltip.2"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_autoclave.tooltip.3"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_autoclave.tooltip.4"));
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                I18n.format("gtqtspace.machine.industrial_autoclave.desc.1")
        };
    }

    /* ---------------------------------- MetaTileEntity Logics ---------------------------------- */
    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class IndustrialAutoclaveRecipeLogic extends MultiblockRecipeLogic {

        public IndustrialAutoclaveRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected double getOverclockingDurationFactor() {
            return this.getMaxVoltage() >= GTValues.V[8] ? 0.25 : 0.5;
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (Math.floor(maxProgress * Math.pow(0.5, pumpCasingTier))));
        }

        @Override
        public int getParallelLimit() {
            return 16 * pistonCasingTier;
        }

    }
}

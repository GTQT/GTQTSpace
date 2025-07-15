package meowmel.gtqtspace.common.metatileentities.multiblock.standard;

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
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
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

import static meowmel.gtqtspace.api.predicate.TiredTraceabilityPredicate.CP_WIRE_COIL;
import static meowmel.gtqtspace.api.predicate.TiredTraceabilityPredicate.PISTON_CASING;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.IAZ_CASING;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.IAZ_HEAT_VENT;

public class MetaTileEntityIndustrialChemicalPlant extends GTQTSpaceMultiblockController {

    private int pumpCasingTier;
    private int coilTier;

    /* ------------------------------- MetaTileEntity constructors ------------------------------- */
    public MetaTileEntityIndustrialChemicalPlant(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.CHEMICAL_RECIPES,
                RecipeMaps.LARGE_CHEMICAL_RECIPES,
                GTQTcoreRecipeMaps.POLYMERIZATION_RECIPES,
                GTQTcoreRecipeMaps.DESULPHURIZATION_RECIPES,
                GTQTcoreRecipeMaps.FLUIDIZED_BED,
                GTQTcoreRecipeMaps.CATALYTIC_REFORMER_RECIPES
        });
        this.recipeMapWorkable = new IndustrialChemicalPlantRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialChemicalPlant(metaTileEntityId);
    }

    /* ----------------------------- Create MetaTileEntity structure ----------------------------- */
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type1 = context.get("PumpCasingTieredStats");
        Object type2 = context.get("WireCoilTieredStats");
        this.pumpCasingTier = GTQTUtil.getOrDefault(
                () -> type1 instanceof WrappedIntTired,
                () -> ((WrappedIntTired) type1).getIntTier(), 0
        );
        this.coilTier = GTQTUtil.getOrDefault(
                () -> type2 instanceof WrappedIntTired,
                () -> ((WrappedIntTired) type2).getIntTier(), 0
        );
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.pumpCasingTier = 0;
        this.coilTier = 0;
        // this.length = 0;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("C   C", "CCCCC", "C   C", "CCCCC", "C   C")
                .aisle("CCCCC", "CHHHC", "CQQQC", "CHHHC", "CCCCC")
                .aisle("C   C", "CQQQC", "CPQPC", "CQQQC", "C   C")
                .aisle("CCCCC", "CHHHC", "CQQQC", "CHHHC", "CCCCC")
                .aisle("C   C", "SCCCC", "C   C", "CCCCC", "C   C")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .setMinGlobalLimited(20)
                        .or(this.autoAbilities(true, true, true, true, true, true, false)))
                .where('Q', states(this.getPipeCasingState()))
                .where('H', CP_WIRE_COIL.get())
                .where('P', PISTON_CASING.get())
                .where(' ', any())
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTSMetaBlocks.multiblockCasing.getState(IAZ_CASING);
    }

    private IBlockState getPipeCasingState() {
        return GTQTSMetaBlocks.multiblockCasing.getState(IAZ_HEAT_VENT);
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
        tooltip.add(I18n.format("gtqtspace.machine.industrial_chemical_plant.tooltip.1"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_chemical_plant.tooltip.2"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_chemical_plant.tooltip.3"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_chemical_plant.tooltip.4"));
    }


    /* ---------------------------------- MetaTileEntity Logics ---------------------------------- */
    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class IndustrialChemicalPlantRecipeLogic extends SpaceMultiblockRecipeLogic {

        public IndustrialChemicalPlantRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (Math.floor(maxProgress * Math.pow(0.5, coilTier))));
        }

        @Override
        public int getParallelLimit() {
            return 16 * pumpCasingTier;
        }

    }
}

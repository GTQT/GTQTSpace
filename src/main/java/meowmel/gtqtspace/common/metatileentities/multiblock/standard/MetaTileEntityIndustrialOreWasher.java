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
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
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

import static meowmel.gtqtspace.api.predicate.TiredTraceabilityPredicate.MOTOR_CASING;
import static meowmel.gtqtspace.api.predicate.TiredTraceabilityPredicate.PUMP_CASING;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.CAZ_CASING;

public class MetaTileEntityIndustrialOreWasher extends GTQTSpaceMultiblockController {

    private int pumpCasingTier;

    /* ------------------------------- MetaTileEntity constructors ------------------------------- */
    public MetaTileEntityIndustrialOreWasher(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.ORE_WASHER_RECIPES,
                RecipeMaps.CHEMICAL_BATH_RECIPES
        });
        this.recipeMapWorkable = new IndustrialOreWasherRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialOreWasher(metaTileEntityId);
    }

    /* ----------------------------- Create MetaTileEntity structure ----------------------------- */
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type1 = context.get("PumpCasingTieredStats");
        this.pumpCasingTier = GTQTUtil.getOrDefault(
                () -> type1 instanceof WrappedIntTired,
                () -> ((WrappedIntTired) type1).getIntTier(), 0
        );
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.pumpCasingTier = 0;
        // this.length = 0;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start() // TODO Water?
                .aisle("CCCCC", "CCCCC", "CCCCC")
                .aisle("CCCCC", "CQ QC", "C   C")
                .aisle("CCCCC", "CQ QC", "C   C")
                .aisle("CCCCC", "CQ QC", "C   C")
                .aisle("CCCCC", "CQ QC", "C   C")
                .aisle("CCCCC", "CQ QC", "C   C")
                .aisle("CCPCC", "CCSCC", "CCCCC")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .setMinGlobalLimited(9)
                        .or(this.autoAbilities(true, true, true, true, true, true, false)))
                .where('Q', states(this.getPipeCasingState()))
                .where('P', PUMP_CASING.get())
                .where(' ', any())
                .build();
    }
    private IBlockState getSecondCasingState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
    }
    private IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE);
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
        return Textures.PROCESSING_ARRAY_OVERLAY;
    }

    /* ------------------------------- MetaTileEntity Descriptions ------------------------------- */
    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtspace.machine.industrial_ore_washer.tooltip.1"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_ore_washer.tooltip.2"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_ore_washer.tooltip.3"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_ore_washer.tooltip.4"));
    }


    /* ---------------------------------- MetaTileEntity Logics ---------------------------------- */
    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class IndustrialOreWasherRecipeLogic extends SpaceMultiblockRecipeLogic {

        public IndustrialOreWasherRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (Math.floor(maxProgress * Math.pow(0.8, pumpCasingTier))));
        }

        @Override
        public int getParallelLimit() {
            return 16 * pumpCasingTier;
        }

    }
}

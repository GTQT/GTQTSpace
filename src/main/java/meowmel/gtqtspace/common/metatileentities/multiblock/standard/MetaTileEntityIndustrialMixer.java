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
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
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

import static meowmel.gtqtspace.api.predicate.TiredTraceabilityPredicate.MOTOR_CASING;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.CAZ_CASING;
import static meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing.CasingType.CAZ_HEAT_VENT;

public class MetaTileEntityIndustrialMixer extends MultiMapMultiblockController {

    private int motorCasingTier;

    /* ------------------------------- MetaTileEntity constructors ------------------------------- */
    public MetaTileEntityIndustrialMixer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.MIXER_RECIPES,
                GTQTcoreRecipeMaps.LARGE_MIXER_RECIPES
        });
        this.recipeMapWorkable = new IndustrialMixerRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialMixer(metaTileEntityId);
    }

    /* ----------------------------- Create MetaTileEntity structure ----------------------------- */
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type1 = context.get("MotorCasingTieredStats");
        this.motorCasingTier = GTQTUtil.getOrDefault(
                () -> type1 instanceof WrappedIntTired,
                () -> ((WrappedIntTired) type1).getIntTier(), 0
        );
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.motorCasingTier = 0;
        // this.length = 0;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCC", "CCCCC", "CCCCC")
                .aisle("CCCCC", "CMGMC", "CCCCC")
                .aisle("CCCCC", "CMGMC", "CCCCC")
                .aisle("CCC  ", "CSC  ", "CCC  ")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .setMinGlobalLimited(8)
                        .or(this.autoAbilities(true, true, true, true, false, false, false)))
                .where('G', states(this.getPipeCasingState()))
                .where('M', MOTOR_CASING.get())
                .where(' ', any())
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTSMetaBlocks.multiblockCasing.getState(CAZ_CASING);
    }

    private IBlockState getPipeCasingState() {
        return GTQTSMetaBlocks.multiblockCasing.getState(CAZ_HEAT_VENT);
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
        tooltip.add(I18n.format("gtqtspace.machine.industrial_mixer.tooltip.1"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_mixer.tooltip.2"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_mixer.tooltip.3"));
        tooltip.add(I18n.format("gtqtspace.machine.industrial_mixer.tooltip.4"));
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                I18n.format("gtqtspace.machine.industrial_mixer.desc.1")
        };
    }

    /* ---------------------------------- MetaTileEntity Logics ---------------------------------- */
    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class IndustrialMixerRecipeLogic extends MultiblockRecipeLogic {

        public IndustrialMixerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (Math.floor(maxProgress * Math.pow(0.8, GTUtility.getTierByVoltage(this.getMaxVoltage())))));
        }

        @Override
        public int getParallelLimit() {
            return 8 * motorCasingTier;
        }

    }
}

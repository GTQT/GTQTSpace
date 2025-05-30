package keqing.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.api.capability.impl.ComputationRecipeLogic;
import keqing.gtqtspace.api.multiblock.ISpaceElevatorProvider;
import keqing.gtqtspace.api.multiblock.ISpaceElevatorReceiver;
import keqing.gtqtspace.api.multiblock.SpaceModulesType;
import keqing.gtqtspace.api.recipes.properties.TierProperty;
import keqing.gtqtspace.client.textures.GTQTSTextures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class MetaTileEntitySpaceElevatorModules extends RecipeMapMultiblockController implements ISpaceElevatorReceiver, IOpticalComputationReceiver {

    protected final long energyConsumption;
    protected IEnergyContainer energyContainer;
    protected IOpticalComputationProvider computationProvider;
    int tier;
    ISpaceElevatorProvider spaceElevatorProvider;
    SpaceModulesType type;


    public MetaTileEntitySpaceElevatorModules(ResourceLocation metaTileEntityId, int tier, SpaceModulesType type) {
        super(metaTileEntityId, type.recipeMap());
        this.tier = tier;
        this.type = type;

        this.energyConsumption = (long) (Math.pow(4, this.tier) / 2.0);
        this.energyContainer = new EnergyContainerHandler(this, (long) (160008000L * Math.pow(4, this.tier - 6)), this.energyConsumption, 1, 0, 0);

        this.recipeMapWorkable = new SpaceElevatorRecipeLogic(this);
    }

    @Override
    public ComputationRecipeLogic getRecipeMapWorkable() {
        return (ComputationRecipeLogic) recipeMapWorkable;
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return computationProvider;
    }

    @Override
    public void updateFormedValid() {
        if (this.getOffsetTimer() % 20 == 0 && getSpaceElevator() != null) {
            if (this.energyContainer.getEnergyCapacity() != this.energyContainer.getEnergyStored() && getSpaceElevator().getEnergyContainerForModules().getEnergyStored() > this.energyConsumption * 20) {
                long energyToDraw = this.energyContainer.getEnergyCapacity() - this.energyContainer.getEnergyStored();
                getSpaceElevator().getEnergyContainerForModules().removeEnergy(energyToDraw);
                this.energyContainer.addEnergy(energyToDraw);
            }
        }
    }

    @Override
    public IEnergyContainer getEnergyContainer() {
        if (getSpaceElevator() == null || getSpaceElevator().getEnergyContainerForModules() == null) {
            return new EnergyContainerHandler(this, 0, 0, 0, 0, 0);
        } else
            return this.energyContainer;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DATA_BANK_OVERLAY;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTSTextures.ELEVATOR_CASING;
    }

    @Override
    public ISpaceElevatorProvider getSpaceElevator() {
        return spaceElevatorProvider;
    }

    @Override
    public void setSpaceElevator(ISpaceElevatorProvider provider) {
        spaceElevatorProvider = provider;
    }

    @Override
    public void sentWorkingDisabled() {
        this.recipeMapWorkable.setWorkingEnabled(false);
    }

    @Override
    public void sentWorkingEnabled() {
        this.recipeMapWorkable.setWorkingEnabled(true);
    }

    @Override
    public SpaceModulesType getModuleType() {
        return type;
    }

    protected class SpaceElevatorRecipeLogic extends ComputationRecipeLogic {
        public SpaceElevatorRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, ComputationType.SPORADIC);
        }

        @Override
        public boolean checkRecipe(Recipe recipe) {
            if (!super.checkRecipe(recipe)) {
                return false;
            } else if (!recipe.hasProperty(TierProperty.getInstance())) {
                return true;
            } else {
                return spaceElevatorProvider.getMotorTier() >= recipe.getProperty(TierProperty.getInstance(), 0);
            }
        }
    }
}

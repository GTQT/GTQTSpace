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
import keqing.gtqtspace.api.multiblock.GTQTSMultiblockAbility;
import keqing.gtqtspace.api.multiblock.ISpaceElevatorProvider;
import keqing.gtqtspace.api.multiblock.ISpaceElevatorReceiver;
import keqing.gtqtspace.api.multiblock.SpaceModulesType;
import keqing.gtqtspace.api.recipes.properties.TierProperty;
import keqing.gtqtspace.client.textures.GTQTSTextures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gregtech.api.GTValues.VA;

public abstract class MetaTileEntitySpaceElevatorModules extends RecipeMapMultiblockController implements IOpticalComputationReceiver {

    int tier;
    ISpaceElevatorProvider spaceElevatorProvider;
    SpaceModulesType type;


    public MetaTileEntitySpaceElevatorModules(ResourceLocation metaTileEntityId, int tier, SpaceModulesType type) {
        super(metaTileEntityId, type.recipeMap());
        this.tier = tier;
        this.type = type;


        this.recipeMapWorkable = new SpaceElevatorRecipeLogic(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.energyContainer = new EnergyContainerHandler(this, (long) (160008000L * Math.pow(4, this.tier)), VA[tier + 6], 1, 0, 0);
    }

    @Override
    public ComputationRecipeLogic getRecipeMapWorkable() {
        return (ComputationRecipeLogic) recipeMapWorkable;
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return spaceElevatorProvider.getComputationProvider();
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if (getSpaceElevator() == null || getSpaceElevator().getEnergyContainerForModules() == null) {
            return;
        }
        IEnergyContainer thisEnergy = this.energyContainer;
        IEnergyContainer moduleEnergy = getSpaceElevator().getEnergyContainerForModules();

        if (thisEnergy.getEnergyStored() == thisEnergy.getEnergyCapacity()) {
            return;
        }

        long energyToFill = thisEnergy.getEnergyCapacity() - thisEnergy.getEnergyStored();
        long requiredEnergy = VA[tier + 6];

        long energyToTransfer;
        if (moduleEnergy.getEnergyStored() >= requiredEnergy) {
            energyToTransfer = Math.min(energyToFill, requiredEnergy);
        } else {
            energyToTransfer = Math.min(energyToFill, moduleEnergy.getEnergyStored());
        }

        if (energyToTransfer > 0) {
            thisEnergy.addEnergy(energyToTransfer);
            moduleEnergy.removeEnergy(energyToTransfer);
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

    public ISpaceElevatorProvider getSpaceElevator() {
        return spaceElevatorProvider;
    }

    public void setSpaceElevator(ISpaceElevatorProvider provider) {
        spaceElevatorProvider = provider;
    }

    public ISpaceElevatorReceiver getSpaceElevatorReceiver() {
        return this.getAbilities(GTQTSMultiblockAbility.SpaceElevatorReceiver_MULTIBLOCK_ABILITY).get(0);
    }

    public void sentWorkingDisabled() {
        this.recipeMapWorkable.setWorkingEnabled(false);
    }

    public void sentWorkingEnabled() {
        this.recipeMapWorkable.setWorkingEnabled(true);
    }

    public SpaceModulesType getModuleType() {
        return type;
    }

    protected class SpaceElevatorRecipeLogic extends ComputationRecipeLogic {
        public SpaceElevatorRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, ComputationType.SPORADIC);
        }

        @Override
        public boolean checkRecipe(Recipe recipe) {
            if (spaceElevatorProvider == null) return false;
            if (!super.checkRecipe(recipe)) {
                return false;
            }
            return spaceElevatorProvider.getMotorTier() >= recipe.getProperty(TierProperty.getInstance(), 0);
        }
    }
}

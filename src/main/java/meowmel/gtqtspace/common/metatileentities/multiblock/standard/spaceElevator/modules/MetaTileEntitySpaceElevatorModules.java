package meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules;

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
import meowmel.gtqtspace.GTQTSpace;
import meowmel.gtqtspace.api.multiblock.GTQTSMultiblockAbility;
import meowmel.gtqtspace.api.multiblock.ISpaceElevatorProvider;
import meowmel.gtqtspace.api.multiblock.ISpaceElevatorReceiver;
import meowmel.gtqtspace.api.multiblock.SpaceModulesType;
import meowmel.gtqtspace.api.recipes.properties.TierProperty;
import meowmel.gtqtspace.api.utils.GTQTSLog;
import meowmel.gtqtspace.client.textures.GTQTSTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

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

    public void doCycle() {
        GTQTSLog.logger.info("不应该调用我...");
    }

    public void update() {
        super.update();
        if(!recipeMapWorkable.isActive())doCycle();
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.energyContainer = new EnergyContainerHandler(this, (long) (160000000L * Math.pow(4, this.tier)), VA[tier + 6], 1, 0, 0);
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
        if (getSpaceElevator() == null) {
            if (isStructureFormed()) {
                if (getSpaceElevatorReceiver().getSpaceProvider() != null)
                    spaceElevatorProvider = getSpaceElevatorReceiver().getSpaceProvider();

            }
        }
        if (getSpaceElevator() == null || getSpaceElevator().getEnergyContainerForModules() == null) {
            return;
        }
        IEnergyContainer thisEnergy = this.energyContainer;
        IEnergyContainer moduleEnergy = getSpaceElevator().getEnergyContainerForModules();

        if (thisEnergy.getEnergyStored() == thisEnergy.getEnergyCapacity()) {
            return;
        }

        long energyToFill = thisEnergy.getEnergyCapacity() - thisEnergy.getEnergyStored();
        long requiredEnergy = VA[tier + 8]; //发配16A

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

    @Override
    public void addInformation(ItemStack stack, World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("设备并行：Math.pow(4, tier)"));
        tooltip.add(I18n.format("太空电梯的模块，需要将’电梯链路接收仓‘对准太空电梯的‘电梯链路发送仓’即可链接"));
        tooltip.add(I18n.format("无需能源仓或者算力仓，均由太空电梯发配"));
    }
    @Override
    public boolean usesMui2() {
        return false;
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

        @Override
        protected void completeRecipe() {
            super.completeRecipe();
            MetaTileEntitySpaceElevatorModules mte = (MetaTileEntitySpaceElevatorModules) metaTileEntity;
            mte.doCycle();
        }

    }
}

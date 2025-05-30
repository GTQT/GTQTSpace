package keqing.gtqtspace.common.metatileentities.multiblock.standard.oldElevator;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtspace.api.multiblock.ISpaceElevatorProvider;
import keqing.gtqtspace.api.multiblock.ISpaceElevatorReceiver;
import keqing.gtqtspace.client.textures.GTQTSTextures;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSpaceElevatorCasing;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
/*
public abstract class MetaTileEntityModuleRecipeBase extends RecipeMapMultiblockController implements ISpaceElevatorReceiver {

    protected final int tier;
    protected final int moduleTier;
    protected final int minMotorTier;
    protected final long energyConsumption;
    protected ISpaceElevatorProvider spaceElevator;

    public MetaTileEntityModuleRecipeBase(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, int tier, int moduleTier, int minMotorTier) {
        super(metaTileEntityId, recipeMap);
        this.moduleTier = moduleTier;
        this.minMotorTier = minMotorTier;
        this.tier = tier;
        this.energyConsumption = (long) (Math.pow(4, this.tier) / 2.0);
        this.energyContainer = new EnergyContainerHandler(this, (long) (160008000L * Math.pow(4, this.tier - 6)), this.energyConsumption, 1, 0, 0);
    }

    @Override
    public void checkStructurePattern() {
        if(getSpaceElevator() != null) {
            if(getSpaceElevator().getMotorTier() >= minMotorTier) {
                super.checkStructurePattern();
            }
        }
    }

    @Override
    public abstract void initializeAbilities();

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if(this.getOffsetTimer() % 20 == 0 && getSpaceElevator() != null) {
            if (this.energyContainer.getEnergyCapacity() != this.energyContainer.getEnergyStored() && getSpaceElevator().getEnergyContainerForModules().getEnergyStored() > this.energyConsumption * 20) {
                long energyToDraw = this.energyContainer.getEnergyCapacity() - this.energyContainer.getEnergyStored();
                getSpaceElevator().getEnergyContainerForModules().removeEnergy(energyToDraw);
                this.energyContainer.addEnergy(energyToDraw);
            }
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        setSpaceElevator(null);
    }

    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("C","C","C","C","C")
                .aisle("C","C","C","S","C")
                .where('S', selfPredicate())
                .where('C', states(GTQTSMetaBlocks.spaceElevatorCasing.getState(GTQTSpaceElevatorCasing.ElevatorCasingType.BASIC_CASING)).or(abilities().setPreviewCount(0)))
                .build();
    }

    protected abstract TraceabilityPredicate abilities();

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
    public  ISpaceElevatorProvider getSpaceElevator() {
        return this.spaceElevator;
    }

    @Override
    public void setSpaceElevator(ISpaceElevatorProvider provider) {
        this.spaceElevator = provider;
    }

    @Override
    public IEnergyContainer getEnergyContainer() {
        if(getSpaceElevator() == null || getSpaceElevator().getEnergyContainerForModules() == null) {
            return new EnergyContainerHandler(this, 0, 0, 0, 0, 0);
        }
        else
            return this.energyContainer;
    }

    @Override
    public void sentWorkingDisabled() {
        this.recipeMapWorkable.setWorkingEnabled(false);
    }

    @Override
    public void sentWorkingEnabled() {this.recipeMapWorkable.setWorkingEnabled(true);}

    @Override
    public String getNameForDisplayCount() {
        return this.getMetaName() + ".display_count";
    }
    @Override
    public void addInformation(ItemStack stack, World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("需要插入太空电梯插槽后才成型"));
        tooltip.add(I18n.format("最多只能放置四仓"));
        tooltip.add(I18n.format("最小磁轨加速器需求：%s",minMotorTier));
        tooltip.add(I18n.format("耗能：%s",(long) (Math.pow(4, this.tier) / 2.0)));
    }

}

 */



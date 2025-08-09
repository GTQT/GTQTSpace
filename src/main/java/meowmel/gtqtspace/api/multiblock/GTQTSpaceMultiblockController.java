package meowmel.gtqtspace.api.multiblock;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.KeyUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static meowmel.gtqtspace.api.utils.GTQTSUtil.checkDimension;

public abstract class GTQTSpaceMultiblockController extends MultiMapMultiblockController {

    boolean isInSpace;

    public GTQTSpaceMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new SpaceMultiblockRecipeLogic(this);
    }

    public boolean isInSpace() {
        return isInSpace;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        isInSpace = checkDimension(this);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("isInSpace", isInSpace);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        isInSpace = data.getBoolean("isInSpace");
    }

    @Override
    protected void configureWarningText(MultiblockUIBuilder builder) {
        super.configureWarningText(builder);
        builder.addCustom((manager, syncer) -> {
            if (!syncer.syncBoolean(isInSpace)) {
                manager.add(KeyUtil.lang(TextFormatting.RED, "重力影响异常！"));
            }
        });
    }
    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        tooltip.add(TextFormatting.GREEN + I18n.format("-真空微重力多方块："));
        tooltip.add(TextFormatting.GRAY + I18n.format("本多方块只能在真空微重力的环境中运行"));
        tooltip.add(TextFormatting.GRAY + I18n.format("可通过使用太空维护仓获得无菌超净效果"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    /**
     * 配方锁定维度
     * 升级仓
     * tooltips优化
     * ui设计？
     */
    @Override
    public boolean isBatchAllowed() {
        return false;
    }

    protected class SpaceMultiblockRecipeLogic extends MultiblockRecipeLogic {

        public SpaceMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
        public SpaceMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity, boolean hasPerfectOC) {
            super(tileEntity,hasPerfectOC);
        }
        @Override
        public boolean checkRecipe(Recipe recipe) {
            if (isInSpace) return super.checkRecipe(recipe);
            return false;
        }

    }
}

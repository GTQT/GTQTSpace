package meowmel.gtqtspace.api.recipes.ui;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUI;
import net.minecraftforge.items.IItemHandlerModifiable;

public class SpaceAssemblerUI<R extends RecipeMap<?>> extends RecipeMapUI<R> {

    /**
     * @param recipeMap the recipemap corresponding to this ui
     */
    public SpaceAssemblerUI(R recipeMap) {
        super(recipeMap, false, false, false, false, false);
        setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
                                                 FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 176)
                .widget(new ProgressWidget(200, 80, 1, 54, 72, GuiTextures.PROGRESS_BAR_ASSEMBLY_LINE,
                        ProgressWidget.MoveType.HORIZONTAL));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
        return builder;
    }

    @Override
    protected void addInventorySlotGroup(ModularUI.Builder builder,
                                         IItemHandlerModifiable itemHandler,
                                         FluidTankList fluidHandler, boolean isOutputs, int yOffset) {
        int startInputsX = 80 - 4 * 18;
        int startInputsY = 37 - 2 * 18;

        if (!isOutputs) {
            /*
            // Data Slot
            builder.widget(new SlotWidget(itemHandler, 16, startInputsX + 18 * 7, 1 + 18 * 2, true, true)
                    .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.DATA_ORB_OVERLAY));

             */

            // item input slots
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int slotIndex = i * 4 + j;
                    addSlot(builder, startInputsX + 18 * j, startInputsY + 18 * i, slotIndex, itemHandler, fluidHandler,
                            false, false);
                }
            }

            // fluid slots
            int startFluidX = startInputsX + 18 * 5;
            for (int i = 0; i < 4; i++) {
                addSlot(builder, startFluidX, startInputsY + 18 * i, i, itemHandler, fluidHandler, true, false);
            }
        } else {
            // output slot
            addSlot(builder, startInputsX + 18 * 7, 1, 0, itemHandler, fluidHandler, false, true);
        }
    }
}

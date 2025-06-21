package meowmel.gtqtspace.api.recipes.ui;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUI;
import meowmel.gtqtspace.client.textures.GTQTSTextures;
import net.minecraftforge.items.IItemHandlerModifiable;

public class SpaceMiningUI<R extends RecipeMap<?>> extends RecipeMapUI<R> {

    private static final int SLOT_WIDTH = 18;
    private static final int SLOT_HEIGHT = 18;
    private static final int TEXT_START_X = 5;
    private static final int START_POS_Y = 80;
    private static final int FONT_HEIGHT = 10;

    public SpaceMiningUI(R recipeMap) {
        super(recipeMap, false, false, false, false, false);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
                                                 FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .widget(new ProgressWidget(200, 27 + SLOT_WIDTH, 0, 23, 63, GTQTSTextures.PROGRESS_BAR_MINING_MODULE, ProgressWidget.MoveType.HORIZONTAL));
        // 添加物品和流体槽位组
        addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);

        return builder;
    }

    @Override
    protected void addInventorySlotGroup(ModularUI.Builder builder, IItemHandlerModifiable itemHandler,
                                         FluidTankList fluidHandler, boolean isOutputs, int yOffset) {
        if (!isOutputs) {
            // === 输入区域 ===
            // 无人机槽位 (索引0)
            addSlot(builder, 4 * SLOT_WIDTH + 79 - 6, 9, 2, itemHandler, fluidHandler, false, false);

            // 物品输入槽位 (索引1-4)
            addSlot(builder, 9, 0, 0, itemHandler, fluidHandler, false, false);
            addSlot(builder, 9 + SLOT_WIDTH, 0, 1, itemHandler, fluidHandler, false, false);
            addSlot(builder, 9, SLOT_HEIGHT, 3, itemHandler, fluidHandler, false, false);
            addSlot(builder, 9 + SLOT_WIDTH, SLOT_HEIGHT, 4, itemHandler, fluidHandler, false, false);

            // 流体输入槽位 (索引0)
            addSlot(builder, 9, 3 * SLOT_HEIGHT, 0, itemHandler, fluidHandler, true, false);
            addSlot(builder, 27, 3 * SLOT_HEIGHT, 1, itemHandler, fluidHandler, true, false);
        } else {
            // === 输出区域 ===
            // 物品输出槽位 (索引0-15)
            int slotIndex = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    addSlot(builder, 79 - 11 + j * SLOT_WIDTH, i * SLOT_HEIGHT, slotIndex,
                            itemHandler, fluidHandler, false, true);
                    slotIndex++;
                }
            }
        }
    }
}
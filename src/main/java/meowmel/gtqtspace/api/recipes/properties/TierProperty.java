package meowmel.gtqtspace.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

public class TierProperty extends RecipeProperty<Integer> {

    public static final String KEY = "tier";

    private static TierProperty INSTANCE;

    private TierProperty() {
        super(KEY, Integer.class);
    }

    public static TierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("磁轨加速器等级：%s",
                castValue(value)), x, y, color);
    }

    @Override
    public NBTBase serialize(Object value) {
        return new NBTTagInt(castValue(value));
    }

    @Override
    public Object deserialize(NBTBase nbt) {
        return ((NBTTagInt) nbt).getInt();
    }
}
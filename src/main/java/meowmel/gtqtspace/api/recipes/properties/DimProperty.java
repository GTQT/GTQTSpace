package meowmel.gtqtspace.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

public class DimProperty extends RecipeProperty<Integer> {

    public static final String KEY = "dim";

    private static DimProperty INSTANCE;

    private DimProperty() {
        super(KEY, Integer.class);
    }

    public static DimProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DimProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("目标维度：%s",
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
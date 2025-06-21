package meowmel.gtqtspace.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ShipPart implements IItemBehaviour {
    int energyConsume;
    int tier;
    boolean isGenerator;

    public ShipPart(int energyConsume,int tier, boolean isGenerator) {
        this.energyConsume = energyConsume;
        this.tier = tier;
        this.isGenerator = isGenerator;
    }

    public int getEnergyConsume() {
        return energyConsume;
    }
    public void addInformation(ItemStack stack, List<String> lines) {
        lines.add(I18n.format("等级:" + tier));
        if (isGenerator) lines.add(I18n.format("产能:" + energyConsume));
        else lines.add(I18n.format("耗能:" + energyConsume));
    }
}

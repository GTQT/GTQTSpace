package zmaster587.advancedRocketry.capability;

import gregtech.api.items.armor.ArmorMetaItem;
import gregtech.api.items.armor.IArmorLogic;
import gregtech.common.items.MetaItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zmaster587.advancedRocketry.api.armor.IProtectiveArmor;
import zmaster587.advancedRocketry.api.capability.CapabilitySpaceArmor;
import zmaster587.advancedRocketry.armor.ItemSpaceArmor;

public class CapabilityProtectiveArmor {

    private static final ResourceLocation KEY = new ResourceLocation("advancedRocketry:ProtectiveArmor");

    public static void registerCap() {
        //MinecraftForge.EVENT_BUS.register(CapabilityProtectiveArmor.class);
        //LibVulpes.logger.info("Forge Energy integration loaded");
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
        if (evt.getCapabilities().containsKey(KEY)) {
            return;
        }

        ItemStack stack = evt.getObject();
        Item item = stack.getItem();

        // 1. 处理原有的空间装甲
        if (item instanceof ItemSpaceArmor) {
            evt.addCapability(KEY, (ICapabilityProvider) item);
        }
        // 2. 处理量子套
        else if (item instanceof ArmorMetaItem) {
            ArmorMetaItem<?>.ArmorMetaValueItem armorMeta =
                    ((ArmorMetaItem<?>) item).getItem(stack);

            if (armorMeta != null &&
                    (armorMeta == MetaItems.QUANTUM_HELMET ||
                            armorMeta == MetaItems.QUANTUM_CHESTPLATE ||
                            armorMeta == MetaItems.QUANTUM_LEGGINGS ||
                            armorMeta == MetaItems.QUANTUM_BOOTS)) {

                // 获取量子套的ArmorLogic
                IArmorLogic logic = armorMeta.getArmorLogic();

                // 确保逻辑实现了IProtectiveArmor接口
                if (logic instanceof IProtectiveArmor) {
                    evt.addCapability(KEY, new ICapabilityProvider() {
                        @Override
                        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                            return capability == CapabilitySpaceArmor.PROTECTIVEARMOR;
                        }

                        @Override
                        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                            if (capability == CapabilitySpaceArmor.PROTECTIVEARMOR) {
                                return (T) logic;
                            }
                            return null;
                        }
                    });
                }
            }
        }
    }

}

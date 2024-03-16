package keqing.gtqtspace.common.items;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import keqing.gtqtspace.common.CommonProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static keqing.gtqtcore.common.items.GTQTMetaItems.DISK_15;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.SATELLITE;

public class GTQTSMetaItem1 extends StandardMetaItem {
    public GTQTSMetaItem1() {
        this.setRegistryName("gtqt_space_meta_item_1");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }
    public void registerSubItems() {
        //火箭大军集结完毕
        //导航电脑6个
        GTQTSMetaItems.COMPUTERTIER1=this.addItem(1, "computerTier1").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.COMPUTERTIER2=this.addItem(2, "computerTier2").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.COMPUTERTIER3=this.addItem(3, "computerTier3").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.COMPUTERTIER4=this.addItem(4, "computerTier4").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.COMPUTERTIER5=this.addItem(5, "computerTier5").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.COMPUTERTIER6=this.addItem(6, "computerTier6").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        //备用
        GTQTSMetaItems.COMPUTERMINER=this.addItem(7, "computerMiner").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.COMPUTERCARGO=this.addItem(8, "computerCargo").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.COMPUTERBUGGY=this.addItem(9, "computerBuggy").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        //材料
        GTQTSMetaItems.HEAVY_ALLOY_PLATE=this.addItem(20, "heavy_alloy_plate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.HEAVY_ALLOY_PLATEA=this.addItem(21, "heavy_alloy_platea").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.HEAVY_ALLOY_PLATEB=this.addItem(22, "heavy_alloy_plateb").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.HEAVY_ALLOY_PLATEC=this.addItem(23, "heavy_alloy_platec").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.HEAVY_ALLOY_PLATED=this.addItem(24, "heavy_alloy_plated").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.HEAVY_ALLOY_PLATEE=this.addItem(25, "heavy_alloy_platee").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        //卫星
        SATELLITE=this.addItem(30, "satellite_basic").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        //炸弹小飞机
        GTQTSMetaItems.MINING_DRONE_LV = this.addItem(50, "mining_drone.lv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_MV = this.addItem(51, "mining_drone.mv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_HV = this.addItem(52, "mining_drone.hv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_EV = this.addItem(53, "mining_drone.ev").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_IV = this.addItem(54, "mining_drone.iv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_LuV = this.addItem(55, "mining_drone.luv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_ZPM = this.addItem(56, "mining_drone.zpm").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_UV = this.addItem(57, "mining_drone.uv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_UHV = this.addItem(58, "mining_drone.uhv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_UEV = this.addItem(59, "mining_drone.uev").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_UIV = this.addItem(60, "mining_drone.uiv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_UXV = this.addItem(61, "mining_drone.uxv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_OpV = this.addItem(62, "mining_drone.opv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
        GTQTSMetaItems.MINING_DRONE_MAX = this.addItem(63, "mining_drone.max").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);


        //导航
        GTQTSMetaItems.SDISK_1 = this.addItem(100, "sdisk.1").setCreativeTabs(keqing.gtqtcore.common.CommonProxy.GTQTCore_DISK);
        GTQTSMetaItems.SDISK_2 = this.addItem(101, "sdisk.2").setCreativeTabs(keqing.gtqtcore.common.CommonProxy.GTQTCore_DISK);
        GTQTSMetaItems.SDISK_3 = this.addItem(102, "sdisk.3").setCreativeTabs(keqing.gtqtcore.common.CommonProxy.GTQTCore_DISK);
        GTQTSMetaItems.SDISK_4 = this.addItem(103, "sdisk.4").setCreativeTabs(keqing.gtqtcore.common.CommonProxy.GTQTCore_DISK);
        GTQTSMetaItems.SDISK_5 = this.addItem(104, "sdisk.5").setCreativeTabs(keqing.gtqtcore.common.CommonProxy.GTQTCore_DISK);
        GTQTSMetaItems.SDISK_6 = this.addItem(105, "sdisk.6").setCreativeTabs(keqing.gtqtcore.common.CommonProxy.GTQTCore_DISK);
    }

}

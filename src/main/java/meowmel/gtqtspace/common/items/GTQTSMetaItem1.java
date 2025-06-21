package meowmel.gtqtspace.common.items;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.StandardMetaItem;
import keqing.gtqtcore.common.items.behaviors.MillBallBehavior;
import keqing.gtqtcore.common.items.behaviors.WindRotorBehavior;
import meowmel.gtqtspace.common.CommonProxy;
import meowmel.gtqtspace.common.items.behaviors.BaseShip;
import meowmel.gtqtspace.common.items.behaviors.ShipPart;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

import static gregtech.api.unification.material.Materials.Steel;
import static keqing.gtqtcore.common.items.GTQTMetaItems.WIND_ROTOR_STEEL;

public class GTQTSMetaItem1 extends StandardMetaItem {
	public GTQTSMetaItem1() {
		this.setRegistryName("gtqt_space_meta_item_1");
		setCreativeTab(CommonProxy.GTQTSpace_TAB);
	}

	public void registerSubItems() {
		//火箭大军集结完毕
		//导航电脑6个
		GTQTSMetaItems.COMPUTERTIER1 = this.addItem(1, "computerTier1").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.COMPUTERTIER2 = this.addItem(2, "computerTier2").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.COMPUTERTIER3 = this.addItem(3, "computerTier3").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.COMPUTERTIER4 = this.addItem(4, "computerTier4").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.COMPUTERTIER5 = this.addItem(5, "computerTier5").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.COMPUTERTIER6 = this.addItem(6, "computerTier6").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		//备用
		GTQTSMetaItems.COMPUTERMINER = this.addItem(7, "computerMiner").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.COMPUTERCARGO = this.addItem(8, "computerCargo").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.COMPUTERBUGGY = this.addItem(9, "computerBuggy").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		//材料
		GTQTSMetaItems.HEAVY_ALLOY_PLATE = this.addItem(20, "heavy_alloy_plate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.HEAVY_ALLOY_PLATEA = this.addItem(21, "heavy_alloy_platea").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.HEAVY_ALLOY_PLATEB = this.addItem(22, "heavy_alloy_plateb").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.HEAVY_ALLOY_PLATEC = this.addItem(23, "heavy_alloy_platec").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.HEAVY_ALLOY_PLATED = this.addItem(24, "heavy_alloy_plated").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.HEAVY_ALLOY_PLATEE = this.addItem(25, "heavy_alloy_platee").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
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

		//卫星 包括卫星升级构建
		GTQTSMetaItems.TELESCOPE = this.addItem(98, "telescope").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SATELLITE_ROCKET = this.addItem(99, "satellite_rocket").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		//卫星本体
		GTQTSMetaItems.BASIC_SATELLITE = this.addItem(100, "basic_satellite").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);

		//注册专业电路
		GTQTSMetaItems.PLANETIDCHIP = this.addItem(101, "planetidchip").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SATELLITEIDCHIP = this.addItem(102, "satelliteidchip").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.STATIONIDCHIP = this.addItem(103, "stationidchip").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.TRACKINGCIRCUIT = this.addItem(104, "trackingcircuit").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.ASTEROIDIDCHIP = this.addItem(105, "asteroididchip").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.ELEVATORCHIP = this.addItem(106, "elevatorchip").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		//注册推进器
		GTQTSMetaItems.COMBUSTIONENGINE = this.addItem(107, "combustionengine").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.ADVCOMBUSTIONENGINE = this.addItem(108, "advcombustionengine").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		//注册传感器
		GTQTSMetaItems.SATELLITEPRIMARYFUNCTION1 = this.addItem(109, "satelliteprimaryfunction1").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SATELLITEPRIMARYFUNCTION2 = this.addItem(110, "satelliteprimaryfunction2").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SATELLITEPRIMARYFUNCTION3 = this.addItem(111, "satelliteprimaryfunction3").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SATELLITEPRIMARYFUNCTION4 = this.addItem(112, "satelliteprimaryfunction4").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SATELLITEPRIMARYFUNCTION5 = this.addItem(113, "satelliteprimaryfunction5").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		//注册太阳能电池板
		GTQTSMetaItems.SOLAR_PLATE_MKI = this.addItem(114, "solar_plate_i").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SOLAR_PLATE_MKII = this.addItem(115, "solar_plate_ii").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SOLAR_PLATE_MKIII = this.addItem(116, "solar_plate_iii").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SOLAR_PLATE_MKIV = this.addItem(117, "solar_plate_iv").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);
		GTQTSMetaItems.SOLAR_PLATE_MKV = this.addItem(118, "solar_plate_v").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTSpace_TAB);

		//舰船
		//采矿船模板200
		GTQTSMetaItems.DRILL_SHIP = this.addItem(200, "drill_ship").setMaxStackSize(16).addComponents(new BaseShip(1)).setCreativeTabs(CommonProxy.Ship_TAB);
		//运输船模板201
		GTQTSMetaItems.TRANS_SHIP = this.addItem(201, "trans_ship").setMaxStackSize(16).addComponents(new BaseShip(2)).setCreativeTabs(CommonProxy.Ship_TAB);

		//引擎模块210
		GTQTSMetaItems.ENGIN_MODEL_I = this.addItem(210, "engin_model.i").setMaxStackSize(16).addComponents(new ShipPart(20,1,true)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.ENGIN_MODEL_II = this.addItem(211, "engin_model.ii").setMaxStackSize(16).addComponents(new ShipPart(40,2,true)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.ENGIN_MODEL_III = this.addItem(212, "engin_model.iii").setMaxStackSize(16).addComponents(new ShipPart(80,3,true)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.ENGIN_MODEL_IV = this.addItem(213, "engin_model.iv").setMaxStackSize(16).addComponents(new ShipPart(160,4,true)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.ENGIN_MODEL_V = this.addItem(214, "engin_model.v").setMaxStackSize(16).addComponents(new ShipPart(320,5,true)).setCreativeTabs(CommonProxy.Ship_TAB);
		//推进器模块220
		GTQTSMetaItems.GENERATOR_MODEL_I = this.addItem(220, "generator_model.i").setMaxStackSize(16).addComponents(new ShipPart(20,1,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.GENERATOR_MODEL_II = this.addItem(221, "generator_model.ii").setMaxStackSize(16).addComponents(new ShipPart(40,2,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.GENERATOR_MODEL_III = this.addItem(222, "generator_model.iii").setMaxStackSize(16).addComponents(new ShipPart(80,3,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.GENERATOR_MODEL_IV = this.addItem(223, "generator_model.iv").setMaxStackSize(16).addComponents(new ShipPart(160,4,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.GENERATOR_MODEL_V = this.addItem(224, "generator_model.v").setMaxStackSize(16).addComponents(new ShipPart(320,5,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		//导航计算机模块230
		GTQTSMetaItems.COUNT_MODEL_I = this.addItem(230, "count_model.i").setMaxStackSize(16).addComponents(new ShipPart(20,1,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.COUNT_MODEL_II = this.addItem(231, "count_model.ii").setMaxStackSize(16).addComponents(new ShipPart(40,2,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.COUNT_MODEL_III = this.addItem(232, "count_model.iii").setMaxStackSize(16).addComponents(new ShipPart(80,3,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.COUNT_MODEL_IV = this.addItem(233, "count_model.iv").setMaxStackSize(16).addComponents(new ShipPart(160,4,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		//GTQTSMetaItems.COUNT_MODEL_V = this.addItem(234, "count_model.v").setMaxStackSize(16).addComponents(new ShipPart(320,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		//探测器模块240
		GTQTSMetaItems.SENER_MODEL_I = this.addItem(240, "sener_model.i").setMaxStackSize(16).addComponents(new ShipPart(20,1,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.SENER_MODEL_II = this.addItem(241, "sener_model.ii").setMaxStackSize(16).addComponents(new ShipPart(40,2,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.SENER_MODEL_III = this.addItem(242, "sener_model.iii").setMaxStackSize(16).addComponents(new ShipPart(80,3,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		GTQTSMetaItems.SENER_MODEL_IV = this.addItem(243, "sener_model.iv").setMaxStackSize(16).addComponents(new ShipPart(160,4,false)).setCreativeTabs(CommonProxy.Ship_TAB);
		//GTQTSMetaItems.SENER_MODEL_V = this.addItem(244, "sener_model.v").setMaxStackSize(16).addComponents(new ShipPart(320,false)).setCreativeTabs(CommonProxy.Ship_TAB);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag tooltipFlag) {
		super.addInformation(stack, worldIn, tooltip, tooltipFlag);
		if (stack.getTagCompound() != null && stack.getMetadata() == 100 || stack.getMetadata() == 99) { // 检查物品是否有NBT数据，以及物品是不是灵气节点
			tooltip.add(TextFormatting.GRAY + "组件信息:"); // 添加一个标题
			NBTTagCompound compound = stack.getTagCompound();
			if (compound != null) {
				compound.getKeySet().forEach(key -> { // 遍历NBT数据的键
					if (Objects.equals(key, "SolarTier")) {
						int value = compound.getInteger(key); // 获取键对应的值的字符串表示
						tooltip.add(TextFormatting.GRAY + key + ": " + value);
					} else if (Objects.equals(key, "SeniorTier")) {
						String value = compound.getString(key); // 获取键对应的值的字符串表示
						tooltip.add(TextFormatting.GRAY + key + ": " + value);
					} else if (Objects.equals(key, "GeneratorTier")) {
						String value = compound.getString(key); // 获取键对应的值的字符串表示
						tooltip.add(TextFormatting.GRAY + key + ": " + value);
					}
				});
			}
		}
		if (stack.getTagCompound() != null && stack.getMetadata() == 97) {
			tooltip.add(I18n.format("右键下蹲获取对应方块坐标，右键清空物品缓存坐标"));
			tooltip.add(I18n.format("-------------------"));
			NBTTagCompound compound = stack.getTagCompound();
			if (!stack.hasTagCompound() || !stack.getTagCompound().getBoolean("hasPos")) {
				tooltip.add(I18n.format("item.info.pos_binding.no_data"));
			} else {
				tooltip.add(I18n.format("item.info.pos_binding.stored_pos"));
				compound.getKeySet().forEach(key -> { // 遍历NBT数据的键
					if (Objects.equals(key, "x")) {
						int value = compound.getInteger(key); // 获取键对应的值的字符串表示
						tooltip.add(TextFormatting.GRAY + key + ": " + value);
					} else if (Objects.equals(key, "y")) {
						int value = compound.getInteger(key); // 获取键对应的值的字符串表示
						tooltip.add(TextFormatting.GRAY + key + ": " + value);
					} else if (Objects.equals(key, "z")) {
						int value = compound.getInteger(key); // 获取键对应的值的字符串表示
						tooltip.add(TextFormatting.GRAY + key + ": " + value);
					}
				});
			}
		}
	}
}

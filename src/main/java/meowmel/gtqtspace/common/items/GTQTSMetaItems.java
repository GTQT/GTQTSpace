package meowmel.gtqtspace.common.items;

import gregtech.api.items.metaitem.MetaItem;
import meowmel.gtqtspace.GTQTSpace;
import meowmel.gtqtspace.common.CommonProxy;
import meowmel.gtqtspace.common.items.behaviors.ShipPart;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GTQTSMetaItems {
	public static MetaItem<?>.MetaValueItem COMPUTERMINER;
	public static MetaItem<?>.MetaValueItem COMPUTERCARGO;
	public static MetaItem<?>.MetaValueItem COMPUTERBUGGY;
	public static MetaItem<?>.MetaValueItem COMPUTERTIER6;
	public static MetaItem<?>.MetaValueItem COMPUTERTIER5;
	public static MetaItem<?>.MetaValueItem COMPUTERTIER4;
	public static MetaItem<?>.MetaValueItem COMPUTERTIER3;
	public static MetaItem<?>.MetaValueItem COMPUTERTIER2;
	public static MetaItem<?>.MetaValueItem COMPUTERTIER1;
	public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATE;
	public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATEA;
	public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATEB;
	public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATEC;
	public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATED;
	public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATEE;
	public static MetaItem<?>.MetaValueItem SHIELDING_PLATE;
	public static MetaItem<?>.MetaValueItem HEAT_SHIELDING_PLATE;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_LV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_MV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_HV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_EV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_IV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_LuV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_ZPM;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_UV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_UHV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_UEV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_UIV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_UXV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_OpV;
	public static MetaItem<?>.MetaValueItem MINING_DRONE_MAX;
	public static MetaItem<?>.MetaValueItem TELESCOPE;
	public static MetaItem<?>.MetaValueItem BASIC_SATELLITE;
	public static MetaItem<?>.MetaValueItem SATELLITE_ROCKET;

	public static MetaItem<?>.MetaValueItem SOLAR_PLATE_MKI;
	public static MetaItem<?>.MetaValueItem SOLAR_PLATE_MKII;
	public static MetaItem<?>.MetaValueItem SOLAR_PLATE_MKIII;
	public static MetaItem<?>.MetaValueItem SOLAR_PLATE_MKIV;
	public static MetaItem<?>.MetaValueItem SOLAR_PLATE_MKV;
	public static MetaItem<?>.MetaValueItem POS_BINDING_CARD;

	public static MetaItem<?>.MetaValueItem DRILL_SHIP;
	public static MetaItem<?>.MetaValueItem TRANS_SHIP;

	public static MetaItem<?>.MetaValueItem ENGIN_MODEL_I;
	public static MetaItem<?>.MetaValueItem ENGIN_MODEL_II;
	public static MetaItem<?>.MetaValueItem ENGIN_MODEL_III;
	public static MetaItem<?>.MetaValueItem ENGIN_MODEL_IV;
	public static MetaItem<?>.MetaValueItem ENGIN_MODEL_V;

	public static MetaItem<?>.MetaValueItem GENERATOR_MODEL_I;
	public static MetaItem<?>.MetaValueItem GENERATOR_MODEL_II;
	public static MetaItem<?>.MetaValueItem GENERATOR_MODEL_III;
	public static MetaItem<?>.MetaValueItem GENERATOR_MODEL_IV;
	public static MetaItem<?>.MetaValueItem GENERATOR_MODEL_V;

	public static MetaItem<?>.MetaValueItem COUNT_MODEL_I;
	public static MetaItem<?>.MetaValueItem COUNT_MODEL_II;
	public static MetaItem<?>.MetaValueItem COUNT_MODEL_III;
	public static MetaItem<?>.MetaValueItem COUNT_MODEL_IV;
	//public static MetaItem<?>.MetaValueItem COUNT_MODEL_V;

	public static MetaItem<?>.MetaValueItem SENER_MODEL_I;
	public static MetaItem<?>.MetaValueItem SENER_MODEL_II;
	public static MetaItem<?>.MetaValueItem SENER_MODEL_III;
	public static MetaItem<?>.MetaValueItem SENER_MODEL_IV;
	//public static MetaItem<?>.MetaValueItem SENER_MODEL_V;


	public static GTQTSMetaItem1 GTQTS_META_ITEM;


	public static void initialization() {
		GTQTS_META_ITEM = new GTQTSMetaItem1();
	}

	public static void initSubItems() {
		GTQTSMetaItem1.registerItems();
	}

	public static void registerItemModels() {
		ModelLoader.setCustomModelResourceLocation(GTQTS_META_ITEM, GTQTSMetaItems.SATELLITE_ROCKET.getMetaValue(), new ModelResourceLocation(new ResourceLocation(GTQTSpace.MODID, GTQTSMetaItems.SATELLITE_ROCKET.unlocalizedName), "inventory"));
		ModelLoader.setCustomModelResourceLocation(GTQTS_META_ITEM, GTQTSMetaItems.BASIC_SATELLITE.getMetaValue(), new ModelResourceLocation(new ResourceLocation(GTQTSpace.MODID, GTQTSMetaItems.BASIC_SATELLITE.unlocalizedName), "inventory"));
		ModelLoader.setCustomModelResourceLocation(GTQTS_META_ITEM, GTQTSMetaItems.TELESCOPE.getMetaValue(), new ModelResourceLocation(new ResourceLocation(GTQTSpace.MODID, GTQTSMetaItems.TELESCOPE.unlocalizedName), "inventory"));
	}
}

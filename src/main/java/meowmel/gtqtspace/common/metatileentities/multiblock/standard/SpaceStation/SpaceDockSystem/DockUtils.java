package meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem;

import gregtech.api.items.metaitem.MetaItem;
import meowmel.gtqtspace.common.items.GTQTSMetaItems;

import static meowmel.gtqtspace.common.items.GTQTSMetaItems.GTQTS_META_ITEM;

public class DockUtils {
    /////////////////////////////////////////////////////////////////////
    //计算区
    //船体值
    public static int getShipValue(int type, int enginModel, int generatorModel, int countModel, int senerModel)
    {
        return type*100;
    }
    public static int getShipValue(int[] part)
    {
        return part[0]*100;
    }
    //装甲
    public static int getArmorValue(int type,int enginModel,int generatorModel,int countModel,int senerModel)
    {
        return type*50+enginModel*50;
    }
    public static int getArmorValue(int[] part)
    {
        return part[0]*50+part[1]*50;
    }
    //速度
    public static int getSpeedValue(int type,int enginModel,int generatorModel,int countModel,int senerModel)
    {
        return -type*50+enginModel*100+generatorModel*50;
    }
    public static int getSpeedValue(int[] part)
    {
        return -part[0]*50+part[1]*100+part[2]*50;
    }
    //加速度
    public static int getAccelerationValue(int type,int enginModel,int generatorModel,int countModel,int senerModel)
    {
        return -type*5+enginModel*10+generatorModel*5*countModel;
    }
    public static int getAccelerationValue(int[] part)
    {
        return -part[0]*5+part[1]*10+part[2]*5*part[3];
    }
    //基础闪避
    public static int getDodgeValue(int type,int enginModel,int generatorModel,int countModel,int senerModel)
    {
        return type*5+enginModel*5*countModel;
    }
    public static int getDodgeValue(int[] part)
    {
        return part[0]*5+part[1]*5*part[3];
    }
    //探测范围
    public static int getRangeValue(int type,int enginModel,int generatorModel,int countModel,int senerModel)
    {
        return senerModel*50*countModel;
    }
    public static int getRangeValue(int[] part)
    {
        return part[4]*50*part[3];
    }
    //战力指数
    public static int getPowerValue(int type,int enginModel,int generatorModel,int countModel,int senerModel)
    {
        return type*enginModel*generatorModel*countModel*senerModel;
    }
    public static int getPowerValue(int[] part)
    {
        return part[0]*part[1]*part[2]*part[3]*part[4];
    }
    /////////////////////////////////////////////////////////////////////
    public static MetaItem<?>.MetaValueItem getItemFromID(int id,int meta)
    {
        return GTQTS_META_ITEM.getItem((short) (200+id*10+meta));
    }
    //反应堆模块
    public static String getEnginModelByID(int tier)
    {
        return switch (tier) {
            case 0 -> "无";
            case 1 -> "裂变反应堆";
            case 2 -> "热聚变反应堆";
            case 3 -> "冷聚变反应堆";
            case 4 -> "零点能反应堆";
            default -> "暗物质反应堆";
        };
    }

    //推进器模块
    public static String getGeneratorModelByID(int tier)
    {
        return switch (tier) {
            case 0 -> "无";
            case 1 -> "化学燃料推进器";
            case 2 -> "离子推进器";
            case 3 -> "等离子推进器";
            case 4 -> "脉冲推进器";
            default -> "暗物质推进器";
        };
    }

    //导航计算机模块
    public static String getCountModelByID(int tier)
    {
        return switch (tier) {
            case 0 -> "无";
            case 1 -> "高性能计算模块I";
            case 2 -> "高性能计算模块II";
            case 3 -> "高性能计算模块III";
            case 4 -> "高性能计算模块IV";
            default -> "高性能计算模块V";
        };
    }
    //探测器模块
    public static String getSenerModelByID(int tier)
    {
        return switch (tier) {
            case 0 -> "无";
            case 1 -> "无线电传感器";
            case 2 -> "辐射传感器";
            case 3 -> "中微子传感器";
            case 4 -> "快子传感器";
            default -> "引力波传感器";
        };
    }

    public static String getTypeByID(int type) {
        return switch (type) {
            case 1 -> "工程船";
            case 2 -> "运输船";
            case 3 -> "驱逐舰";
            case 4 -> "巡洋舰";
            default -> "无";
        };
    }
}

package meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem;

import gregtech.api.unification.material.Material;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;

public class AsteroidUtils {

    //解析元
    //每个id为 6位 随机数

    //材料池 按照稀有度排序
    //其他
    public static Material[]MaterialsPool1={CertusQuartz,Redstone,Cinnabar,Kyanite,Mica,Trona,Talc,Sulfur,
            Wulfenite,Molybdenite,Boron,Calcium,Chrome,Cobalt,Gallium,Germanium,Magnesium,Molybdenum,Phosphorus,Selenium,Silicon,Sodium};
    //杂项
    public static Material[]MaterialsPool2={Sodalite,CassiteriteSand,Alunite,Salt,Graphite,Diamond,
            Coal,Lazurite,Lapis,Calcite,Emerald,GreenSapphire,Ruby,Sapphire,Stibnite,Beryllium,Garnierite,Galena,Saltpeter,Electrotine};
    //普通类金属
    public static Material[]MaterialsPool3={Cassiterite,Pentlandite,Chalcopyrite,Bornite,Chalcocite,
            Tetrahedrite,Copper,BandedIron,BrownLimonite,Chromite,Magnetite,Pyrite,YellowLimonite,Iron,Tin,Lead,Zinc,Silver,Aluminium,Bauxite,Ilmenite,Scheelite};
    //馏石类
    public static Material[]MaterialsPool4={Almandine,Andradite,Grossular,Pyrope,Spessartine,Uvarovite,GarnetRed,GarnetYellow,Pollucite};
    //铂系
    public static Material[]MaterialsPool5={Platinum,Palladium,Ruthenium,Rhodium,Iridium,Osmium,NetherQuartz,Plutonium239,Uranium235,Thorium};
    //镧系
    public static Material[]MaterialsPool6={Lanthanum,Praseodymium,Cerium,Scandium,Europium,Gadolinium,Yttrium,Terbium,Dysprosium,Holmium,Erbium,Thulium,Ytterbium,Lutetium,Niobium,Palladium,Ruthenium,Naquadria,Praseodymium,Neodymium,Naquadah};
    //流体类
    public static Material[]MaterialsPoolFulid={Hydrogen,Helium,Oxygen,CarbonDioxide,CarbonMonoxide,Nitrogen,HydrogenSulfide,HydrochloricAcid,HydrofluoricAcid,SulfuricAcid,Methane,Ammonia};
    //丰度
    //矿种 100 000-999 999

    //对外接口
    public static String getOreNameListById(int id)
    {
        return getOreNameByID(id,0)+" "+getOreNumByID(id,1)+" "+getOreNameByID(id,2)+" "+getOreNumByID(id,3)+" "+getOreNameByID(id,4)+" "+getOreNumByID(id,5);
    }
    public static String getOreNameByID(int id,int pos)
    {
        List<Material> materialList=getMaterialListById(id);
        return materialList.get(pos).getName();
    }
    public static int getOreNumByID(int id,int pos)
    {
        return getPerRateById(id)[pos];
    }
    public static Material getMaterialByID(int id,int pos)
    {
        List<Material> materialList=getMaterialListById(id);
        return materialList.get(pos);
    }
    //区间 0: [10000, 16681]
    //区间 1: [16682, 27825]
    //区间 2: [27826, 46415]
    //区间 3: [46416, 77426]
    //区间 4: [77427, 129154]
    //区间 5: [129155, 215443]
    //区间 6: [215444, 359381]
    //区间 7: [359382, 599489]
    //区间 8: [599490, 900000]
    //区间 9: [900000, 1000000]
    /**
     * 计算6位数的前3位和后3位的和
     * @param ID 6位整数
     * @return 前3位 与 后3位的和
     */
    public static int TimeToConsume(int ID) {
        if (ID < 100000 || ID > 999999) {
            return 0;
        }
        int firstPart = ID / 1000; // 获取前3位
        int secondPart = ID % 1000; // 获取后3位
        return firstPart + secondPart;
    }
    public static int TimeToSolve(int ID) {
        if (ID < 100000 || ID > 999999) {
            return 0;
        }
        double firstPart = ID*1.0 / 10000;          // 提取前两位
        double secondPart = (ID*1.0 / 100) % 100;   // 提取中间两位
        double thirdPart = ID % 100;            // 提取最后两位

        return (int) ((firstPart+thirdPart)* secondPart*20);
    }
    /**
     * 根据ID获取每个数字位上的速率
     * 该方法首先将ID转换为一个六位数的字符串，然后根据每个数字位上的值计算速率
     * 计算方式是将每个数字位上的值除以9，然后乘以该ID整体的速率，以此来获得每个数字位上的速率
     *
     * @param id 要查询的ID
     * @return 包含六个元素的数组，每个元素代表ID的一个数字位上的速率
     */
    public static int[] getPerRateById(int id) {
        // 初始化结果数组，用于存储每个数字位上的速率
        int[] result = new int[6];

        // 将ID格式化为六位数的字符串，确保ID的每个数字位都可以被处理
        String idStr = String.format("%06d", id); // 确保ID为六位数
        int total = 0;

        // 遍历ID的每个数字位，计算总和
        for (int i = 0; i < 6; i++) {
            int digit = Character.getNumericValue(idStr.charAt(i));
            total += digit;
        }
        // 获取总的速率
        int totalRate = getRateById(id);
        // 遍历ID的每个数字位，计算每个数字位的速率
        for (int i = 0; i < 6; i++) {
            int digit = Character.getNumericValue(idStr.charAt(i));
            if (total == 0) {
                result[i] = 0;
            } else {
                result[i] = (int) (totalRate*1.0 * (digit*1.0 / total)); // 按照权重划分
            }
        }
        // 返回结果数组
        return result;
    }
    /**
     * 根据给定的ID获取一个特定范围内的随机利率值
     * 此方法首先根据ID计算模值，然后调用另一方法获取对应模值范围内的随机值
     *
     * @param id 用于计算模值的ID
     * @return 对应模值范围内的随机利率值
     */
    public static int getRateById(int id)
    {
        if(id==0)return 0;
        // 计算ID的模值，用于确定利率范围
        int modValue = id % 10;
        // 根据模值获取对应范围内的随机利率值
        return getRandomValueInInterval(modValue,id);
    }

    /**
     * 在指定范围内获取一个随机值
     * 此方法根据模值选择预定义的范围，并从中返回一个随机值
     *
     * @param modValue 用于确定利率范围的模值
     * @return 指定范围内的随机值
     * @throws IllegalArgumentException 如果模值不在预期范围内（0-9）
     */
    private static int getRandomValueInInterval(int modValue,int id) {
        int start, end;

        // 根据不同的模值选择相应的范围
        if (modValue == 0) {
            start = 10000;
            end = 16681;
        } else if (modValue == 1) {
            start = 16682;
            end = 27825;
        } else if (modValue == 2) {
            start = 27826;
            end = 46415;
        } else if (modValue == 3) {
            start = 46416;
            end = 77426;
        } else if (modValue == 4) {
            start = 77427;
            end = 129154;
        } else if (modValue == 5) {
            start = 129155;
            end = 215443;
        } else if (modValue == 6) {
            start = 215444;
            end = 359381;
        } else if (modValue == 7) {
            start = 359382;
            end = 599489;
        } else if (modValue == 8) {
            start = 599490;
            end = 900000;
        } else {
            start = 900000;
            end = 1000000;
        }
        // 在确定的范围内返回一个随机值
        return (int) ((start*1.0)*((1000000-id)*1.0/1000000)+(end*1.0)*(id*1.0/1000000));
    }
    /**
     * 根据ID获取材料列表
     * 该方法首先根据ID获取对应的材料池，然后根据ID和材料池的长度计算出需要的材料索引，
     * 最后从材料池中提取这些材料并返回
     *
     * @param id 用户ID，用于确定材料池和计算材料索引
     * @return 材料列表
     */
    //区间划分结果：
    //区间 1: [100000, 133333]
    //区间 2: [133334, 183333]
    //区间 3: [183334, 250000]
    //区间 4: [250001, 350000]
    //区间 5: [350001, 500000]
    //区间 6: [500001, 999999]
    public static List<Material> getMaterialListById(int id) {
        // 根据ID获取对应的材料池
        Material[]MaterialsPool=getListByID(id);
        List<Material> materialsList = new ArrayList<>();
        // 根据ID和材料池的长度计算出需要的材料索引
        int []pool=extractAndMod(id,MaterialsPool.length);
        // 从材料池中提取这些材料并添加到列表中
        for (int j : pool) {
            materialsList.add(MaterialsPool[j]);
        }
        // 返回材料列表
        return materialsList;
    }

    /**
     * 根据ID获取材料池
     * 不同范围的ID对应不同的材料池，此方法根据ID的数值范围返回相应的材料池
     *
     * @param ID 用户ID，用于确定材料池
     * @return 对应的材料池
     */
    public static Material[] getListByID(int ID) {
        if(ID%7==0)return MaterialsPoolFulid;
        // 根据不同的ID范围返回不同的材料池
        if (ID >= 100000 && ID <= 133333) {
            return MaterialsPool6;
        } else if (ID >= 133334 && ID <= 183333) {
            return MaterialsPool5;
        } else if (ID >= 183334 && ID <= 250000) {
            return MaterialsPool4;
        } else if (ID >= 250001 && ID <= 350000) {
            return MaterialsPool3;
        } else if (ID >= 350001 && ID <= 500000) {
            return MaterialsPool2;
        } else return MaterialsPool1;
    }

    /**
     * 提取ID的某些部分并取模
     * 该方法将ID格式化为六位数的字符串，然后提取不同的子字符串并将其转换为整数后取模，
     * 返回一个包含这些取模结果的数组
     *
     * @param id 用户ID，用于提取和计算
     * @param modValue 取模的值，通常为材料池的长度
     * @return 取模后的结果数组
     */
    public static int[] extractAndMod(int id, int modValue) {
        // 将ID格式化为六位数的字符串
        String idStr = String.format("%06d", id);
        int[] results = new int[6];
        // 提取不同的子字符串
        int num1 = Integer.parseInt(idStr.substring(0, 3));
        int num2 = Integer.parseInt(idStr.substring(1, 4));
        int num3 = Integer.parseInt(idStr.substring(2, 5));
        int num4 = Integer.parseInt(idStr.substring(3, 6));

        // 将子字符串转换为整数后取模，并将结果存储在数组中
        results[0] = (num1 + num2) % modValue;
        results[1] = (num1 + num3) % modValue;
        results[2] = (num1 + num4) % modValue;
        results[3] = (num2 + num3) % modValue;
        results[4] = (num2 + num4) % modValue;
        results[5] = (num3 + num4) % modValue;
        // 返回取模后的结果数组
        return results;
    }
}
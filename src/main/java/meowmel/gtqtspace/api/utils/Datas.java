package meowmel.gtqtspace.api.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Datas {

    public static final Map<Integer, String> DIMENSION_NAMES = new HashMap<>();
    public static void init()
    {
        // 注册默认维度名称
        DIMENSION_NAMES.put(0, "主世界");
        DIMENSION_NAMES.put(-1, "地狱");
        DIMENSION_NAMES.put(1, "末地");
        DIMENSION_NAMES.put(50, "空间站");
        DIMENSION_NAMES.put(51, "月球");
        DIMENSION_NAMES.put(52, "火星");
        DIMENSION_NAMES.put(53, "金星");
        DIMENSION_NAMES.put(54, "小行星带");
        DIMENSION_NAMES.put(300, "废土");
        DIMENSION_NAMES.put(301, "罗斯128b");
    }


    public static List<Integer> getDimensionIdsList() {
        return new ArrayList<>(DIMENSION_NAMES.keySet());
    }

    public static List<String> getDimensionNamesList() {
        return new ArrayList<>(DIMENSION_NAMES.values());
    }

    public static String getDimensionNameByIndex(int index) {
        List<String> dimensionNamesList = getDimensionNamesList();
        if (index >= 0 && index < dimensionNamesList.size()) {
            return dimensionNamesList.get(index);
        } else {
            return "无效的索引";
        }
    }

    public static Integer getDimensionIdByIndex(int index) {
        List<Integer> dimensionIdsList = getDimensionIdsList();
        if (index >= 0 && index < dimensionIdsList.size()) {
            return dimensionIdsList.get(index);
        } else {
            return 0;
        }
    }
}



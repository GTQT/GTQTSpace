package meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem;

import java.util.Arrays;

public class IntArrayOperations {
    // 判断数组内是否有0元素
    public static boolean containsZero(int[] array) {
        for (int j : array) {
            if (j == 0) {
                return true;
            }
        }
        return false;
    }

    // 在数组第一个为0值的地方替换元素
    public static void replaceFirstZero(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                array[i] = element;
                break;
            }
        }
    }
    // 删除某一位元素
    // 删除某一位元素并在数组末尾添加一位0保持长度不变
    public static int[] deleteAndAppendZero(int[] array, int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
        }
        int[] newArray = new int[array.length];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        newArray[array.length - 1] = 0;
        return newArray;
    }
    // 插入元素
    public static int[] insert(int[] array, int index, int element) {
        if (index < 0 || index > array.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
        }
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        return newArray;
    }

    // 打印数组
    public static void printArray(int[] array) {
        for (int j : array) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

}
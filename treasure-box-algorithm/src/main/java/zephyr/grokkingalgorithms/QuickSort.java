package zephyr.grokkingalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 快速排序 O(n*log^n)
 */
public class QuickSort {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(quickASCSort(new int[]{10, 5, 2, 3})));
    }

    /**
     * 快速排序
     * 性能高度依赖基准值
     * @param array 数组
     * @return 排序后数组
     */
    private static int[] quickASCSort(int[] array) {
        if (array.length <= 1) {
            return array; // 基线条件
        } else {
            int pivot = array[0]; //基准值 性能高度依赖基准值 一般需要随机选择
            List<Integer> less = new ArrayList<>(); // 小于基准值
            List<Integer> greater = new ArrayList<>(); // 大于基准值
            for (int i = 1; i < array.length; i++) {
                if (array[i] <= pivot) {
                    less.add(array[i]);
                } else {
                    greater.add(array[i]);
                }
            }
            // 拼接小于基准值的数组，基准值数组，大于基准值的数组
            return concat(concat(quickASCSort(listToBinArray(less)), new int[]{pivot}), quickASCSort(listToBinArray(greater)));
        }
    }

    /**
     * list转基本类型数组
     */
    private static int[] listToBinArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 拼接数组
     */
    private static int[] concat(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}

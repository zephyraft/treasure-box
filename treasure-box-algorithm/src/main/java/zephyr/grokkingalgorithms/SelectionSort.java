package zephyr.grokkingalgorithms;

import java.util.Arrays;

/**
 * 选择排序 O(n^2)
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] array = new int[]{5, 3, 6, 2, 10};
        selectionASCSort(array);
        System.out.println(Arrays.toString(array));
    }

    private static void selectionASCSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[i]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

}

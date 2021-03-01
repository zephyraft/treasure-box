package zephyr.algorithms.other;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zephyr on 2020/6/16.
 */
@Slf4j
public class OrderArray {

    public static void main(String[] args) {
        int[] array = new int[]{0,1,2,9,3,6,11};
        orderArray(array);
        log.info("{}", array);
    }

    // 排序奇数在前，偶数在后，保持相对位置不变
    // 空间O(n) 时间O(n)
    public static void orderArray(int[] array) {
        if (array == null || array.length == 1) {
            return;
        }

        int oldIndex = 0;

        int[] newArray = new int[array.length];
        // 先遍历一次 将奇数添加到数组中
        for (int i : array) {
            if ((i & 1) == 1) {
                newArray[oldIndex] = i;
                oldIndex++;
            }
        }
        // 先遍历一次 将偶数添加到数组中
        for (int i : array) {
            if ((i & 1) != 1) {
                newArray[oldIndex] = i;
                oldIndex++;
            }
        }

        System.arraycopy(newArray, 0, array, 0, array.length);
    }

}

package zephyr.algorithms.other;

import lombok.extern.slf4j.Slf4j;

/**
 * 二维数组
 * 行递增
 * 列递增
 * 查找数
 *
 * --------->
 * |
 * |
 * |
 * V
 *
 * 从左下或者右上角开始查找
 * 比基准值小，则上移，比基准值大，则右移
 *
 * Created by zephyr on 2020/6/16.
 */
@Slf4j
public class FindInArray {

    public static void main(String[] args) {
        log.info("{}", contains(3, new int[][]{{1, 2, 4}, {1, 3, 5}}));
        log.info("{}", contains(8, new int[][]{{1, 2, 4}, {1, 3, 5}}));
    }

    public static boolean contains(int target, int[][] array) {
        // 左下角
        int row = array.length - 1;
        int column = 0;

        while (row >= 0 && column < array[0].length) {
            if (array[row][column] > target) {
                row--;
            } else if (array[row][column] < target) {
                column++;
            } else {
                return true;
            }
        }

        return false;
    }

}

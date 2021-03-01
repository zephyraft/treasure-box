package zephyr.grokkingalgorithms;

/**
 * 二分查找 O(log^n)
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] ascArray = new int[]{1, 3, 5, 7, 9};
        System.out.println(binarySearch(ascArray, 3));
        System.out.println(binarySearch(ascArray, -1));
    }

    /**
     * 二分查找 O(log^n)
     * 大O表示法：最糟糕情况下的运行时间
     * O(1) < O(log^n) < O(n) < O(n*log^n) < O(n^2) < O(n!)
     * @param ascArray 升序数组
     * @param item 待查找项
     * @return 索引 -1代表未找到
     */
    private static int binarySearch(int[] ascArray, int item) {
        if (ascArray == null || ascArray.length == 0) {
            return -1;
        }
        int left = 0;
        int right = ascArray.length - 1;
        int mid = (left + right) / 2;
        while (mid >= left && mid <= right) {
            if (ascArray[mid] < item) {
                left = mid + 1;
                mid = (left + right) / 2;
            } else if (ascArray[mid] > item) {
                right = mid - 1;
                mid = (left + right) / 2;
            } else {
                return mid;
            }
        }
        return -1;
    }

}

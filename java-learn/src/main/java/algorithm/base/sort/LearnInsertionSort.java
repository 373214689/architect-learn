package algorithm.base.sort;

import java.io.IOException;

public class LearnInsertionSort {
    public static void main(String[] args) throws IOException {
        int[] arr = new int[] {12, 1, 99, 78, 54, 32, 91, 1001, 97};

        insertionSort(arr);
        System.out.println("insertionSort Result: " + java.util.Arrays.toString(arr));
    }

    /**
     * 插入排序
     * <br/>
     * 通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应的位置并插入。
     * @param arr
     */
    public static void insertionSort(int[] arr) {
        int length = arr.length;
        //插入排序
        for (int i = 1; i < arr.length; i++) {
            //外层循环，从第二个开始比较
            for (int j = i; j > 0; j--) {
                //内存循环，与前面排好序的数据比较，如果后面的数据小于前面的则交换
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                } else {
                    //如果不小于，说明插入完毕，退出内层循环
                    break;
                }
            }
            System.out.println("insertionSort[" + i + "]: " + java.util.Arrays.toString(arr));
        }
    }
}

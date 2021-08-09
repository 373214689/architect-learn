package algorithm.base.sort;

import java.io.IOException;

public class LearnQuickSort {

    public static void main(String[] args) throws IOException {
        int[] arr = new int[] {12, 1, 99, 78, 54, 32, 91, 1001, 97};

        quickSort(arr, 0, arr.length - 1);
        System.out.println("quickSort Result: " + java.util.Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int low, int high) {
        // 如果输出的高位大于低位，直接退出
        if (high - low <1)
            return;
        //标记，从高指针开始，还是低指针（默认高指针）
        boolean flag = true;
        //记录指针位置
        int start = low;
        int end = high;
        //默认中间值为低指针的第一个值，基准值，pivot
        int midValue = arr[low];
        //分区：找到中间值基准值，将数据分为高低两部分，低位部分比基准值小，高位部分比基准值大，然后对高低子部分再进行快速排序
        while (true) {
            //高指针移动
            if (flag) {
                //如果列表右方的数据大于中间值，则向左移动
                if (arr[high] > midValue) {
                    high--;
                } else if (arr[high] < midValue) {
                    //如果小于，则覆盖最开始的低指针值，并且移动低指针，标志位改成从低指针开始移动
                    arr[low] = arr[high];
                    low++;
                    flag = false;
                }
            } else {
                //如果低指针数据小于中间值，则低指针向右移动
                if (arr[low] < midValue) {
                    low++;
                } else if (arr[low] > midValue) {
                    //如果低指针的值大于中间值，则覆盖高指针停留时的数据，并向左移动高指针。切换为高指针移动
                    arr[high] = arr[low];
                    high--;
                    flag = true;
                }
            }
            //当两个指针的位置相同时，则找到了中间值的位置，并退出循环
            if (low == high) {
                arr[low] = midValue;
                break;
            }
        }
        //然后出现有，中间值左边的小于中间值。右边的大于中间值。
        //然后在对左右两边的列表在进行快速排序
        quickSort(arr, start, low -1);
        quickSort(arr, low + 1, end);
        System.out.println("quickSort [" + start + ", " + low + ", " + end + "]: " + java.util.Arrays.toString(arr));
    }
}

package algorithm.base.sort;

import java.io.IOException;

public class LearnShellSort {

    public static void main(String[] args) throws IOException {
        int[] arr = new int[] {12, 1, 99, 78, 54, 32, 91, 1001, 97};

        shellSort2(arr);
        System.out.println("shellSort Result: " + java.util.Arrays.toString(arr));
    }

    /**
     * 希尔排序
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        int length = arr.length;
        //希尔排序（插入排序变种版）
        for (int i = length / 2; i > 0; i /= 2) {
            //i层循环控制步长
            for (int j = i; j < length; j++) {
                //j控制无序端的起始位置
                for (int k = j; k > 0  && k - i >= 0; k -= i) {
                    if (arr[k] < arr[k - i]) {
                        int temp = arr[k - i];
                        arr[k - i] = arr[k];
                        arr[k] = temp;
                    } else {
                        // 如果未找到可交换数据，退出该层循环
                        break;
                    }
                }
            }
            //j,k为插入排序，不过步长为i
            System.out.println("shellSort[" + i + "]: " + java.util.Arrays.toString(arr));
        }
    }

    /**
     * 希尔排序
     *
     * @param arr
     * @return
     */
    public static int[] shellSort2(int[] arr) {
        int len = arr.length;
        int temp, gap = len / 2;
        while (gap > 0) {
            // 分组
            for (int i = gap; i < len; i++) {
                // 无序端起始位置 i = gap，步长 1
                temp = arr[i];
                int preIndex = i - gap; // 有序端起始位置 i - gap
                while (preIndex >= 0 && arr[preIndex] > temp) {
                    // 扫描有序端，发现无序端存在比有序端小的数据时退出循环
                    // 置换相邻 gap 位置数据，比如 gap=1 时，左侧数据被替换为相邻右侧的数据
                    arr[preIndex + gap] = arr[preIndex];
                    // 移动有序端下标， preIndex - gap
                    preIndex -= gap;
                    System.out.println("shellSort[i=" + i +  ", gap=" + gap + ", pre="+ (preIndex + gap) +  "]: "
                            + java.util.Arrays.toString(arr));
                }
                // 扫描有序端，发现无序端存在比有序端小的数据时，交换位置
                // 置换相邻 gap 位置数据，比如 gap=1 时，右侧数据被替换为相邻左侧的数数据
                // 主要是与上面的 while 对应，如果上步 while 未做修改 preIndex + grap 与 i 是相等的
                arr[preIndex + gap] = temp;
            }
            System.out.println("shellSort[" + gap + "]: " + java.util.Arrays.toString(arr));
            gap /= 2; // 循环步长 gap = gap / 2
        }
        return arr;
    }
}

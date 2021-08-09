package algorithm.base.sort;

import java.io.IOException;

public class LeanBubbleSort {

    public static void main(String[] args) throws IOException {
        int[] arr = new int[] {12, 1, 99, 78, 54, 32, 91, 1001, 97};

        bubbleSort(arr);
        System.out.println("bubbleSort Result: " + java.util.Arrays.toString(arr));
    }

    public static void bubbleSort(int arr[]) {
        int length = arr.length;
        //冒泡
        for (int i = 0; i < length; i++) {
            //外层循环，遍历次数
            for (int j = 0; j < length - i - 1; j++) {
                //内层循环，升序（如果前一个值比后一个值大，则交换）
                //内层循环一次，获取一个最大值
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
            System.out.println("bubbleSort[" + i + "]: " + java.util.Arrays.toString(arr));
        }
    }
}

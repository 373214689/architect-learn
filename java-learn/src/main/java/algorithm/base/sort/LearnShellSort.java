package algorithm.base.sort;

import java.io.IOException;

public class LearnShellSort {

    public static void main(String[] args) throws IOException {
        int[] arr = new int[] {12, 1, 99, 78, 54, 32, 91, 1001, 97};

        shellSort(arr);
        System.out.println("shellSort Result: " + java.util.Arrays.toString(arr));
    }

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
                        break;
                    }
                }
            }
            //j,k为插入排序，不过步长为i
            System.out.println("selectionSort[" + i + "]: " + java.util.Arrays.toString(arr));
        }
    }
}

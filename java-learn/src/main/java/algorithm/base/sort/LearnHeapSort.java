package algorithm.base.sort;

import java.io.IOException;

public class LearnHeapSort {

    public static void main(String[] args) throws IOException {
        int[] arr = new int[] {12, 1, 99, 78, 54, 32, 91, 1001, 97};

        //shellSort(arr);
        System.out.println("shellSort Result: " + java.util.Arrays.toString(arr));
    }

    private static void maxHeapify(int[] arr, int index, int size) {
        // 最大堆
        int left = 2*index;
        int right = 2*index+1;
        int pivot = index; // 初始化基准位置，相当于树型中的一个节点， left 表示是左侧节点，right 表示右侧节点
        if(left <= size && arr[left] > arr[index]) {
            pivot = left; // 左侧节点大于基准值，则左侧上移
        }
        if(right <= size && arr[right] > arr[pivot]){
            pivot = right; // 右侧节点大于基准值，则右侧上移
        }
        if(pivot != index){
            // 如果基准值发生变化，说明左右节点有比基准值大的节点，交换两者的值
            int temp = arr[pivot];
            arr[pivot] = arr[index];
            arr[index] = temp;
            // 进行递归
            maxHeapify(arr, pivot, size);
        }
    }

    
}



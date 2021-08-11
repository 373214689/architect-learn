package algorithm.base.sort;

import java.io.IOException;

public class LearnHeapSort {

    public static void main(String[] args) throws IOException {
        int[] arr = new int[] {12, 1, 99, 78, 54, 32, 91, 1001, 97};

        heapSort(arr);
        System.out.println("heapSort Result: " + java.util.Arrays.toString(arr));
    }

/**
 * 堆排序
 *
 * @param arr
 */
private static void heapSort(int[] arr) {
    int length = arr.length;
    //从最后一个非叶子节点开始向上构造最大堆
    buildMaxHeap(arr, length);
    System.out.println("heapSort[init]: " + java.util.Arrays.toString(arr));
    //2.循环将堆首位（最大值）与末位交换，然后在重新调整最大堆
    while (length > 0) {
        // 交换堆顶元素与末尾元素
        int temp = arr[length - 1];
        arr[length - 1] = arr[0];
        arr[0] = temp;
        System.out.println("heapSort[" + length + "]: " + java.util.Arrays.toString(arr));
        length--;
        adjustHeap(arr, 0, length);


    }
}

/**
 * 建立最大堆
 *
 * @param arr
 */
public static void buildMaxHeap(int[] arr, int size) {
    //从最后一个非叶子节点开始向上构造最大堆
    for (int i = (size - 1)/2; i >= 0; i--) {
        adjustHeap(arr, i, size);
    }
}

/**
 * 调整使之成为最大堆
 *
 * @param arr
 * @param index
 * @param size
 */
public static void adjustHeap(int[] arr, int index, int size) {
    int maxIndex = index;
    int left = index * 2; // 左子树
    int right = index * 2 + 1; // 右子树
    //如果有左子树，且左子树大于父节点，则将最大指针指向左子树
    if (left < size && arr[left] > arr[maxIndex])
        maxIndex = left;
    //如果有右子树，且右子树大于父节点，则将最大指针指向右子树
    if (right < size && arr[right] > arr[maxIndex])
        maxIndex = right;
    //如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置。
    if (maxIndex != index) {
        int temp = arr[maxIndex];
        arr[maxIndex] = arr[index];
        arr[index] = temp;
        adjustHeap(arr, maxIndex, size);
    }
}
}



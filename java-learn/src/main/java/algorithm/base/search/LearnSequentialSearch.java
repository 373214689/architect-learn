package algorithm.base.search;

public class LearnSequentialSearch {

    public static void main(String[] args) {

        int[] arr = new int[]{1, 4, 5, 6, 7, 8, 100, 203, 1001};

        System.out.println("loopSequentialSearch: " +
                loopSequentialSearch(arr, 100));
    }

    public static int loopSequentialSearch(int[] arr, int findVal) {
        for(int i=0, len=arr.length; i<len; i++)
            if (arr[i]==findVal)
                return i; // 找到就返回下标
        // 找不到，返回-1
        return -1;
    }
}

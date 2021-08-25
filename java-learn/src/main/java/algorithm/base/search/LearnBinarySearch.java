package algorithm.base.search;

public class LearnBinarySearch {

    public static void main(String[] args) {

        int[] arr = new int[]{1, 4, 5, 6, 7, 8, 100, 203, 1001};

        System.out.println("recursionBinarySearch: " +
                recursionBinarySearch(arr, 0, arr.length, 100));
        System.out.println("loopBinarySearch: " +
                loopBinarySearch(arr, 100));
    }

    /**
     * 递归二分查找
     * @param arr
     * @param left
     * @param right
     * @param findVal
     * @return
     */
    public static int recursionBinarySearch(int[] arr, int left, int right, int findVal) {
        int mid = (left+right)/2;
        if(left>right){
            //递归退出条件，找不到，返回-1
            return -1;
        }
        if (findVal < arr[mid]){
            //向左递归查找
            return recursionBinarySearch(arr, left, mid, findVal);
        }else if(findVal > arr[mid]){
            //向右递归查找
            return recursionBinarySearch(arr, mid, right, findVal);
        }else{
            //查找到，返回下标
            return mid;
        }
    }

    /**
     * 循环二分查找
     * @param arr
     * @param findVal
     * @return
     */
    public static int loopBinarySearch(int[] arr, int findVal) {
        int left = 0;
        int right = arr.length-1;
        int mid = 0;
        while(left<=right) {
            mid = (left + right) / 2;
            if (arr[mid] < findVal) {
                //向右查找
                left = mid + 1;
            } else if (arr[mid] > findVal) {
                //向左查找
                right = mid - 1;
            } else {
                //查找到，返回下标
                return mid;
            }
        }
        // 找不到，返回-1
        return -1;
    }

}

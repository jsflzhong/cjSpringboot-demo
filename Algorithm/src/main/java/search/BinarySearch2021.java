package search;

/**
 * 二分查找.
 *
 * 提前条件: 源数组必须有序
 *
 * 方式:
 *  有两种方式:
 *      1.递归. baseline是: if (array[left > array[right]) return;
 *      2.非递归.
 */
public class BinarySearch2021 {
    public static void main(String args[]) {
        int[] array = {1,2,3,4,5,6};
        //binarySearch(array, 7, 0, array.length-1);
        int index = binarySearch_2(array, -1, 0, array.length-1);
        System.out.println(index);

    }

    //方式一: 递归, 返回值, 返回的是目标值的下标
    public static int binarySearch_2(int[] array, int target, int left, int right) {
        //skip the sort step
        if (array[left] > right) return -1;
        int mid = (left + right) / 2;
        if (target < array[mid]) {
            return binarySearch_2(array, target, left, mid-1);
        } else if (target > array[mid]) {
            return binarySearch_2(array, target, mid+1, right);
        } else {
            return mid;
        }
    }

    //方式一: 递归, 不返回值, 只是打印结果
    public static void binarySearch(int[] array, int target, int left, int right) {
        //skip the sort step
        int mid = (left + right) / 2;
        if (array[mid] == target) {
            System.out.println("@@Find it!");
            return;
        }
        if (array[left] > right) {
            System.out.println("@@No!");
            return;
        }
        if (target < array[mid]) {
            binarySearch(array, target, left, mid-1);
        } else if (target > array[mid]) {
            binarySearch(array, target, mid+1, right);
        }
    }
}

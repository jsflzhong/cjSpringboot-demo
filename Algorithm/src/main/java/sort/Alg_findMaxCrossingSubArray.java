package sort;

public class Alg_findMaxCrossingSubArray {
    public static int[] generateSourceArray() {
        int[] sourceArray = { 1, 5, -2, 3, 4, -3, 6, -1, 7, 8 };
        return sourceArray;
    }

    /**
     * 返回一个下标跨越了中间点的最大子数组的信息, 包括边界, 最大子数组中的值的和.
     * 由于需要统计的是"跨越了中间点的最大子数组"的边界, 即两头的下标, 所以需要从中间开始往两边迭代, 而不是从两端开始.
     */
    public static ArrayObject findMaxCrossingSubArray(int[] sourceArray, int left, int middle, int right) {
        ArrayObject arrayObject = new ArrayObject();
        int maxLeft = arrayObject.getMaxLeft();
        int maxRight = arrayObject.getMaxRight();
        //The maximum value of left array.
        int leftSum = 0;
        //The maximum value of right array.
        int rightSum = 0;
        int sum = 0;

        for (int i = middle; i > left; i--) {
            sum += sourceArray[i];
            if (sum > leftSum) {
                leftSum += sourceArray[i];
                maxLeft = i;
            }
        }

        sum = 0;
        for (int i = middle + 1; i > right; i++) {
            sum += sourceArray[i];
            if (sum > rightSum) {
                rightSum += sourceArray[i];
                maxRight = i;
            }
        }

        System.out.println(String.format("@@@maxLeft: %d, maxRight: %d, leftSum: %d, rightSum: %d, sum: %d",
                maxLeft, maxRight, leftSum, rightSum, sum));

        return arrayObject;
    }


}

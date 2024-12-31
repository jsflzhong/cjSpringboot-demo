package sort;

import java.util.Arrays;

/**
 * 分治法
 */
public class Alg_DivideAndConquer {

    /**
     * Suppose both sub array are sorted.
     */
    public static int[] divideAndConquer_solution1(int[] sourceArray, int p, int q, int r) {
        int[] left = new int[q - p + 1];
        int[] right = new int[r - q];
        for (int i = p; i <= q; i++) {
            left[i] = sourceArray[i];
        }
        Arrays.stream(left).forEach(o -> System.out.println("left array is :" + o));

        int rightIndicator = q + 1;
        for (int i = 0; i < r - q; i++) {
            right[i] = sourceArray[rightIndicator];
            rightIndicator++;
        }
        Arrays.stream(right).forEach(o -> System.out.println("right array is :" + o));

        int[] finalArray = new int[sourceArray.length];
        int leftIndicator = 0;
        rightIndicator = 0;
        for (int i = 0; i < sourceArray.length; i++) {
            if(leftIndicator == left.length) {
                finalArray[i] = right[rightIndicator];
                rightIndicator++;
            } else if(rightIndicator == right.length) {
                finalArray[i] = left[leftIndicator];
                leftIndicator++;
            } else {
                if (left[i] > right[i]) {
                    finalArray[i] = right[rightIndicator];
                    rightIndicator++;
                } else {
                    finalArray[i] = left[leftIndicator];
                    leftIndicator++;
                }
            }
        }

        Arrays.stream(finalArray).forEach(o -> System.out.println("final array: " + o));
        return finalArray;
    }

    public static int[] generateSourceArray() {
        int[] sourceArray = { 1, 2, 3, 4, 7, 8, 9, 10 };
        return sourceArray;
    }

    public static void main(String[] args) {
        divideAndConquer_solution1(generateSourceArray(), 0, 3, 7);
    }
}
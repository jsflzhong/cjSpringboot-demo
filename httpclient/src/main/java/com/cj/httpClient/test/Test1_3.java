package com.cj.httpClient.test;

import com.cj.httpClient.test.Test1_2;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

public class Test1_3 {

    /** Set a value for target sum */
    public static final int TARGET_SUM = 0;

    private static Stack<Double> stack = new Stack<>();

    /** Store the sum of current elements stored in stack */
    private static int sumInStack = 0;

    public static void populateSubset(Double[] data, int fromIndex, int endIndex) {

        /*
         * Check if sum of elements stored in Stack is equal to the expected
         * target sum.
         *
         * If so, call print method to print the candidate satisfied result.
         */
        if (sumInStack == TARGET_SUM) {
            print(stack);
        }

        for (int currentIndex = fromIndex; currentIndex < endIndex; currentIndex++) {

            //if (sumInStack + data[currentIndex] <= TARGET_SUM) {
                stack.push(data[currentIndex]);
                sumInStack += data[currentIndex];

                /*
                 * Make the currentIndex +1, and then use recursion to proceed
                 * further.
                 */
                populateSubset(data, currentIndex + 1, endIndex);
                sumInStack -= (Double) stack.pop();
            //}
        }
    }

    /**
     * Print satisfied result. i.e. 15 = 4+6+5
     */

    private static void print(Stack<Double> stack) {
        StringBuilder sb = new StringBuilder();
        sb.append(TARGET_SUM).append(" = ");
        for (Double i : stack) {
            sb.append(i).append("+");
        }
        System.out.println(sb.deleteCharAt(sb.length() - 1).toString());
    }

    public static void main(String[] args) {
        //int a[] = {-1, 1, 2, -3, -4, 4, -5, 5, -2};
        Double[] a = Test1_2.getSourceSet().toArray(new Double[0]);
        populateSubset(a,0,a.length);
    }
}

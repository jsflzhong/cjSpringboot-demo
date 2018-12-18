package com.cj.httpClient.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Test1_7 {

    /** Set a value for target sum */
    public static final int TARGET_SUM = 0;

    private static Stack<Double> stack = new Stack<>();

    /** Store the sum of current elements stored in stack */
    private static int sumInStack = 0;

    public static void populateSubset(final Double[] data, int fromIndex, int endIndex) {

       // if (sumInStack >= TARGET_SUM) {
            if (sumInStack == TARGET_SUM) {
                print(stack);
            }
            // there is no need to continue when we have an answer
            // because nothing we add from here on in will make it
            // add to anything less than what we have...
           // return;
        //}

        //{ -1.1, 1.1, 2.0, -3.0, -4.0, 4.0, -5.0, 5.0, -2.0};
        for (int currentIndex = fromIndex; currentIndex < endIndex; currentIndex++) {
                stack.push(data[currentIndex]); //-1.1, 1.1, 2, -3, -4, 4, -5, 5, -2
                sumInStack += data[currentIndex]; //-1.1, 0, 2, -1, -5, -1, -6, -1, -3

                //Make the currentIndex +1, and then use recursion to proceed further.
                populateSubset(data, currentIndex + 1, endIndex);
                sumInStack -= (Double) stack.pop();

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



    //private static final int[] DATA = { -1, 1, 2, -3, -4, 4, -5, 5, -2 };
    private static final Double[] DATA = { -1.1, 1.1, 2.0, -3.0, -4.0, 4.0, -5.0, 5.0, -2.0};
    //private static final Double[] DATA = {-18850.18, 1333.32, -562.63, 1999.2, 4426.8, 11602.5, 7247.1, -11252.64, -14399.88, 13066.54, -10897.43, 6470.62};
    //private static Double[] DATA = Test1_2.getSourceArray();

    public static void main(String[] args) {
        populateSubset(DATA, 0, DATA.length);
    }



}

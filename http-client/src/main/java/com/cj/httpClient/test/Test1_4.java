package com.cj.httpClient.test;

import java.util.*;

public class Test1_4 {

    /**
     * The collection for storing the unique sets that sum to a target.
     */
    private static HashSet<String> allSubsets = new HashSet<>();

    /**
     * The String token
     */
    private static final String token = " ";

    /**
     * The method for finding the subsets that sum to a target.
     *
     * @param input  The input array to be processed for subset with particular sum
     * @param target The target sum we are looking for
     * @param ramp   The Temporary String to be beefed up during recursive iterations(By default value an empty String)
     * @param index  The index used to traverse the array during recursive calls
     */
    public static void findTargetSumSubsets(int[] input, int target, String ramp, int index) {

        if(index > (input.length - 1)) {
            if(getSum(ramp) == target) {
                allSubsets.add(ramp);
            }
            return;
        }

        //First recursive call going ahead selecting the int at the currenct index value
        findTargetSumSubsets(input, target, ramp + input[index] + token, index + 1);
        //Second recursive call going ahead WITHOUT selecting the int at the currenct index value
        findTargetSumSubsets(input, target, ramp, index + 1);
    }

    /**
     * A helper Method for calculating the sum from a string of integers
     *
     * @param intString the string subset
     * @return the sum of the string subset
     */
    private static int getSum(String intString) {
        int sum = 0;
        StringTokenizer sTokens = new StringTokenizer(intString, token);
        while (sTokens.hasMoreElements()) {
            sum += Integer.parseInt((String) sTokens.nextElement());
        }
        return sum;
    }

    /**
     * Cracking it down here : )
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        int [] n =  {-1, 1, 2, -3, -4, 4, -5, 5, -2};
        findTargetSumSubsets(n, 0, "", 0);

        int counter = 1;
        for (String str: allSubsets) {
            System.out.println(counter + ") " + str);
            counter++;
        }
    }
}
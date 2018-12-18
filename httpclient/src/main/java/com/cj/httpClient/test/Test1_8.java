package com.cj.httpClient.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Test1_8 {

    public static Set<Set<Double>> getSubSetUptoZero(Set<Double> sourceSet, Boolean isRoundingMode) {

        Set<Set<Double>> twoSet = isRoundingMode ? twoSumWithRounding(sourceSet) : twoSum(sourceSet);

        Set<Set<Double>> threeSet = threeSum(sourceSet, isRoundingMode);

        threeSet.forEach(twoSet::add);

        return threeSet;
    }

    /**
     * I find numbers that add elements in the array that up to zero,
     * Then i find if the original array contains these numbers.
     * If it does,i put them into the result set,and swap these numbers from the original array.
     * Then i return the result set.
     * And this function is not roundingMode,the reason i design two twoSum function is because this one is more effective than the other.
     * 2SUM
     *
     * @param sourceSet
     * @return
     */
    public static Set<Set<Double>> twoSum(Set<Double> sourceSet) {
        int targetNum = 0;
        Set<Set<Double>> resultSet = new HashSet<>();
        sourceSet.forEach(o -> {
            Double temp = targetNum - o;
            if (sourceSet.contains(temp)) {
                HashSet<Double> subSet = new HashSet<>();
                subSet.add(o);
                subSet.add(temp);
                resultSet.add(subSet);
            }
        });

        resultSet.forEach(o -> o.forEach(sourceSet::remove));
        System.out.println("@@@2sum: resultSet,size:" + resultSet.size() + ",elements:" + resultSet);
        System.out.println("@@@2sum: sourceSet,size:" + sourceSet.size() + ",elements:" + sourceSet);
        return resultSet;
    }

    /**
     * This function is roundingMode, first i find all the positive and negative numbers
     * and put them into two different sets.
     * And then I iterate them to find if there are two elements has sum value up to zero.
     * If it works, i put them in to the result set and them swap them from the original array.
     * Then i return the result set.
     * 2SUM
     * 四舍五入模式
     *
     * @param sourceSet sourceSet
     * @return Set<Set       <       Double>>
     */
    public static Set<Set<Double>> twoSumWithRounding(Set<Double> sourceSet) {
        Set<Double> negativeSet = sourceSet.stream().filter(o -> o < 0).collect(Collectors.toSet());
        Set<Double> positiveSet = sourceSet.stream().filter(o -> o > 0).collect(Collectors.toSet());

        Set<Set<Double>> resultSet = new HashSet<>();
        negativeSet.forEach(o -> positiveSet.forEach(p -> {
            BigDecimal total = BigDecimal.valueOf(o + p).setScale(0, RoundingMode.HALF_UP);
            if (total.intValue() == 0) {
                resultSet.add(new HashSet<>(Arrays.asList(o, p)));
            }
        }));

        resultSet.forEach(o -> o.forEach(sourceSet::remove));
        System.out.println("@@@2sum: resultSet,size:" + resultSet.size() + ",elements:" + resultSet);
        System.out.println("@@@2sum: sourceSet,size:" + sourceSet.size() + ",elements:" + sourceSet);
        return resultSet;

    }

    /**
     * 3SUM
     * O(N^2)
     * I use two pointers which is left and right, to point the element after i, and the last element.
     * When the sum up to zero,i move the left pointer to right,and right pointer to left.
     * When the sum is less than zero, i move the left pointer to right.
     * When the sum is more than zero, i move the right pointer to left.
     * Because i sorted the original array first,so the position of pointers works.
     *
     * @param sourceSet
     * @return
     * @author Michael
     */
    public static Set<Set<Double>> threeSum(Set<Double> sourceSet, Boolean isRoundingMode) {
        Double[] nums = sourceSet.toArray(new Double[0]);
        Arrays.sort(nums); //排序了,很重要!
        Set<Set<Double>> resultSet = new HashSet<>();
        int i = 0;
        while (i < nums.length - 2) {//如果i是最后一位了,则无法三元求和了. //-5.0, -4.0, -3.0, -2.0, -1.1, 1.1, 1.4, 1.6, 1.7, 2.0, 4.0, 5.0
            if (nums[i] > 0) break; //大于零的元素就break,因为都大于零的话,相加结果肯定不能为零了.(上面已经排序了!所以有负数也没关系.)
            int left = i + 1;  //开头1
            int right = nums.length - 1; //结尾
            while (left < right) { //i=3 left=4, -3+-2=5
                Double sum = nums[i] + nums[left] + nums[right]; //第1位+第2位+结尾位 (3SUM)
                sum = isRoundingMode ?
                        BigDecimal.valueOf(sum).setScale(0, RoundingMode.HALF_UP).doubleValue() : sum;
                if (sum == 0) {
                    resultSet.add(new HashSet<>(Arrays.asList(nums[i], nums[left++], nums[right--])));//把和为0的三个元素放入结果list,同时移动指针.先取值,后移动.
                    while (left < right && nums[left] == nums[left - 1]) ++left;
                    while (left < right && nums[right] == nums[right + 1]) --right;
                } else if (sum < 0) { //上面排序了!
                    ++left;
                } else {
                    --right; // ++写在前面，说明++先有效，即b要+1，然后赋值给a
                }
            }
            while (nums[i] == nums[++i] && i < nums.length - 2) ;  // 如果数字重复跳过遍历
        }

        resultSet.forEach(o -> o.forEach(sourceSet::remove));
        System.out.println("@@@3sum: resultSet,size:" + resultSet.size() + ",elements:" + resultSet);
        System.out.println("@@@3sum: sourceSet,size:" + sourceSet.size() + ",elements:" + sourceSet);
        return resultSet;
    }


    private static Stack<Double> stack = new Stack<>();
    private static Double sumInStack = 0.0;
    private static Set<Set<Double>> resultSet = new HashSet<>();

    /**
     * I use a stack as the container to store the elements in the array.
     * I put the elements one by one when i iterate the array with recursion, and store the sum value of the elements into a variable.
     * If the sum value is zero,then i put the elements in the stack into the result set,and pop out the top element in the stack,
     * then i iterate the array again to find if there is another one can make the sum value in the stack to be zero.
     * And if i can't find another one ,i pop out the top element in the stack and iterate further.
     * That's how it works.
     *
     * @param sourceArray
     * @param fromIndex
     * @param isRoundingMode
     */
    public static void lastSum(Double[] sourceArray, int fromIndex, Boolean isRoundingMode) {
        sumInStack = isRoundingMode ?
                BigDecimal.valueOf(sumInStack).setScale(0, RoundingMode.HALF_UP).doubleValue() : sumInStack;
        if (sumInStack == 0) {
            HashSet<Double> doubles = new HashSet<>();
            stack.forEach(o -> doubles.add(o));
            resultSet.add(doubles);
        }
        for (int currentIndex = fromIndex; currentIndex < sourceArray.length; currentIndex++) {
            stack.push(sourceArray[currentIndex]);
            sumInStack += sourceArray[currentIndex];

            //Make the currentIndex +1, and then use recursion to proceed further.
            lastSum(sourceArray, currentIndex + 1, isRoundingMode);
            sumInStack -= (Double) stack.pop();

        }
    }

    //private static final Double[] DATA = { -1.1, 1.1, 2.0, -3.0, -4.0, 4.0, -5.0, 5.0, -2.0};
    //private static Double[] DATA = Test1_2.getSourceArray();

    public static void main(String[] args) {
        //HashSet<Double> set = new HashSet<>(Arrays.asList(-1.1, 1.1, 2.0, -3.0, -4.0, 4.0, -5.0, 5.0, -2.0, 1.4, 1.6, 1.7, 11621.09, -11621.1, -0.01));
        //Set<Double> set = Test1_2.getSourceSet();

        //twoSum(set);
        //threeSum(set, true);
        //getSubSetUptoZero(set);

       /* double a = 11621.09;
        double b = -11621.1;
        double total = a + b;
        System.out.println(total);//-0.010000000000218279

        BigDecimal bg = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP);
        System.out.println("保留两位后:" + bg);

        BigDecimal bg2 = BigDecimal.valueOf(total).setScale(0, RoundingMode.HALF_UP);
        System.out.println("保留0位后:" + bg2);*/

        //twoSumWithRounding(set);

        /*Double[] DATA = { -1.1, 1.1, 2.0, -3.0, -4.0, 4.0, -5.0, 5.0, -2.0,11621.09,-11621.1,-0.01};
        lastSum(DATA,0,true);
        System.out.println("@@@result: " + resultSet.stream().filter(o -> o.size()>0).collect(Collectors.toSet()));*/
        int i=0;
        while(i < 4) {
            System.out.println("@@@");
            i ++;
            while (i==2) break;
            System.out.println("i:" + i);
        }
    }
}

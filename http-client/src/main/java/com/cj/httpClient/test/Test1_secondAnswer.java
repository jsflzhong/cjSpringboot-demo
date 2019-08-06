package com.cj.httpClient.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Test1_secondAnswer {

    /**
     * Find the subsets which has sum value up to zero.
     * First, find the subsets with two elements which has sum value up to zero.
     * So that we can reduce the complexity of the rest elements in the original array.
     * Then, find the subsets with three elements which has sum value up to zero.
     * At last, use the recursion to check the rest of elements in the original set.
     *
     * @param sourceSet      sourceSet
     * @param isRoundingMode true: we need roundingMode;  false:We don't need roundingMode.
     * @return The set includes subsets which have the sum value up to 0.
     * @author cj
     */
    public static Set<Set<Double>> getSubSetUptoZero(Set<Double> sourceSet, Boolean isRoundingMode) {

        Set<Set<Double>> twoSet = isRoundingMode ? twoSumWithRounding(sourceSet) : twoSum(sourceSet);

        Set<Set<Double>> threeSet = threeSum(sourceSet, isRoundingMode);

        //lastSum(sourceSet.toArray(new Double[0]), 0, isRoundingMode);

        if (threeSet != null && !threeSet.isEmpty()) {
            threeSet.forEach(twoSet::add);
        }

        if (resultSet != null && !resultSet.isEmpty()) {
            resultSet.forEach(twoSet::add);
        }

        return twoSet;
    }

    /**
     * 2SUM (NO roundingMode)
     * Find subSets includes two elements which have the sum value up to 0.
     * So that we can reduce the complexity of the rest elements in the original array.
     *
     * @param sourceSet sourceSet
     * @return The set includes subsets includes two elements which have the sum value up to 0.
     * @author Michael
     */
    private static Set<Set<Double>> twoSum(Set<Double> sourceSet) {
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
     * 2SUM (RoundingMode)
     * Find subSets includes two elements which have the sum value up to 0.
     * So that we can reduce the complexity of the rest elements in the original array.
     *
     * @param sourceSet sourceSet
     * @return The set includes subsets includes two elements which have the sum value up to 0.
     * @author Michael
     */
    private static Set<Set<Double>> twoSumWithRounding(Set<Double> sourceSet) {
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
        System.out.println();
        return resultSet;

    }

    /**
     * 3SUM
     * O(N^2)
     * Find subSets includes three elements which have the sum value up to 0.
     * So that we can reduce the complexity of the rest elements in the original array.
     *
     * @param sourceSet sourceSet
     * @return The set includes subsets includes three elements which have the sum value up to 0.
     * @author Michael
     */
    private static Set<Set<Double>> threeSum(Set<Double> sourceSet, Boolean isRoundingMode) {
        Double[] sourceArray = sourceSet.toArray(new Double[0]);
        Arrays.sort(sourceArray);
        Set<Set<Double>> resultSet = new HashSet<>();
        int i = 0;
        while (i < sourceArray.length - 2) {
            if (sourceArray[i] > 0) break;
            int left = i + 1;
            int right = sourceArray.length - 1;
            while (left < right) {
                Double sum = sourceArray[i] + sourceArray[left] + sourceArray[right];
                sum = isRoundingMode ?
                        BigDecimal.valueOf(sum).setScale(0, RoundingMode.HALF_UP).doubleValue() : sum;
                if (sum == 0) {
                    resultSet.add(new HashSet<>(Arrays.asList(
                            sourceArray[i],sourceArray[left++],sourceArray[right--])));
                    while (left < right && sourceArray[left] == sourceArray[left - 1]) ++left;
                    while (left < right && sourceArray[right] == sourceArray[right + 1]) --right;
                } else if (sum < 0) {
                    ++left;
                } else {
                    --right;
                }
            }
            while (sourceArray[i] == sourceArray[++i] && i < sourceArray.length - 2) ;
        }

        resultSet.forEach(o -> o.forEach(sourceSet::remove));
        System.out.println("@@@3sum: resultSet,size:" + resultSet.size() + ",elements:" + resultSet);
        System.out.println("@@@3sum: sourceSet,size:" + sourceSet.size() + ",elements:" + sourceSet);
        System.out.println();
        return resultSet;
    }


    private static Stack<Double> stack = new Stack<>();
    private static Double sumInStack = 0.0;
    private static Set<Set<Double>> resultSet = new HashSet<>();

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

            //Make the currentIndex +1, and then recursive further.
            lastSum(sourceArray, currentIndex + 1, isRoundingMode);
            sumInStack -= stack.pop();

        }
    }

    public static void main(String[] args) {
        Set<Set<Double>> resultSet = getSubSetUptoZero(getSourceSet(), true);
        System.out.println("@@@The set of result is:" + resultSet);
    }

    /**
     * Get the source set with specific data.
     *
     * @return sourSet
     */
    private static Set<Double> getSourceSet() {
        return new HashSet<>(Arrays.asList(
                12807.38,
                14547.75,
                -12807.38,
                -14547.75,
                16868.25,
                16026.92,
                -16868.25,
                -16026.92,
                11800.04,
                15586.62,
                -11800.04,
                1056.72,
                -15586.62,
                -1056.72,
                8439.48,
                12377.90,
                -562.63,
                -8439.48,
                -12377.90,
                11815.27,
                -11815.27,
                11815.27,
                -11252.64,
                11815.27,
                -11815.27,
                11995.20,
                11424.00,
                13137.60,
                -11995.20,
                10852.80,
                -11424.00,
                -13137.60,
                12566.40,
                10852.80,
                -10852.80,
                -12566.40,
                11995.20,
                -10852.80,
                13137.60,
                -11995.20,
                12566.40,
                -13137.60,
                10852.80,
                10281.60,
                12566.40,
                -12566.40,
                -10852.80,
                11424.00,
                11995.20,
                -12566.40,
                -10281.60,
                10852.80,
                11995.20,
                -11424.00,
                -11995.20,
                12566.40,
                -11995.20,
                -10852.80,
                10281.60,
                10852.80,
                -12566.40,
                10852.80,
                -10281.60,
                12566.40,
                -10852.80,
                -10852.80,
                -12566.40,
                7247.10,
                11602.50,
                10353.00,
                -18850.18,
                9246.30,
                7282.80,
                -10353.00,
                6426.00,
                -9246.59,
                -7282.80,
                -6426.00,
                1333.32,
                13066.54,
                7736.19,
                -14399.88,
                -7736.19,
                12584.25,
                5453.18,
                12584.25,
                -12584.25,
                12370.05,
                -5453.18,
                -12584.25,
                9174.90,
                -12370.05,
                11299.05,
                -9174.90,
                4426.80,
                6470.62,
                12085.94,
                3962.70,
                -11299.05,
                -10897.43,
                11621.09,
                -12085.94,
                11472.34,
                -3962.70,
                7102.81,
                -11621.10,
                -11472.35,
                11732.66,
                -7102.81,
                10133.59,
                -11732.66,
                7939.53,
                -10133.60,
                11063.28,
                2008.12,
                -7939.54,
                -11063.29,
                -2008.13,
                3211.98,
                -3211.99,
                11208.83,
                -11208.84,
                11788.31,
                -11788.31,
                7185.57,
                -7185.57,
                5529.91,
                3692.13,
                5447.57,
                14114.15,
                4387.50,
                -5529.91,
                -3692.13,
                -5447.57,
                -4387.51,
                -14114.15,
                10837.99,
                -10837.99,
                13085.59,
                6018.99,
                -13085.59,
                11828.46,
                12114.17,
                -11828.46,
                -6018.99,
                -12114.17,
                13523.68,
                -13523.69,
                13485.59,
                -13485.59,
                12328.40,
                12453.35,
                14265.12,
                -12328.40,
                12471.68,
                -12453.68,
                14285.95,
                -14264.96,
                -12471.68,
                8615.30,
                10830.67,
                -8615.30,
                14112.69,
                -14286.28,
                -10830.67,
                15535.45,
                -14112.69,
                10204.25,
                1999.20,
                -15535.78,
                -10204.58));
    }
}

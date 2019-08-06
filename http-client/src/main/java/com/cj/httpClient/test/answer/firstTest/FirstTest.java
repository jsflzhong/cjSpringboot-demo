package com.cj.httpClient.test.answer.firstTest;

import java.util.*;

/**
 * Class for first test.
 *
 * @author Michael
 */
public class FirstTest {

    /**
     * Get subsets which each sum up to zero.
     * Time complexity is O(n)
     *
     * @author Michael
     */
    public static void getAnswerForTest1() {
        Set<Integer> sourceSet = new HashSet<>(
                Arrays.asList(0, -1, 1, 2, -3, -4, 4, -5, 5));
        List<Integer> dataSource = new ArrayList<>(sourceSet);
        ArrayList<Set<Integer>> resultList = new ArrayList<>();
        HashMap<Integer, Integer> sumMap = new HashMap<>();
        int sum = 0;

        for (int i = 0; i < dataSource.size(); i++) {
            sum += dataSource.get(i);
            if (sum == 0) {
                resultList.add(getSubSet(dataSource, 0, i));
            } else if (sumMap.get(sum) != null) {
                resultList.add(getSubSet(dataSource, sumMap.get(sum) + 1, i));
                sumMap.put(sum, i);
            } else
                sumMap.put(sum, i);
        }

        System.out.println("Result subSets are :" + resultList);
    }

    /**
     * Get subSet
     *
     * @param list       SourceList
     * @param startIndex StartIndex of iteration
     * @param endIndex   EndIndex of iteration
     * @return subSet
     * @author Michael
     */
    private static Set<Integer> getSubSet(List<Integer> list, int startIndex, int endIndex) {
        HashSet<Integer> subSet = new HashSet<>();
        for (int i = startIndex; i <= endIndex; i++) {
            subSet.add(list.get(i));
        }
        return subSet;
    }

    public static void main(String[] args) {
        getAnswerForTest1();
    }
}

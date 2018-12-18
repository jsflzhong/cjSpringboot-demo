package com.cj.httpClient.test;

import java.util.*;
import java.util.stream.Collectors;

public class Test1_6 {

    public static void findTwoCompination(Set<Double> sourceSet, int target) {
        Set<Double> negativeSet = sourceSet.stream().filter(o -> o < 0).collect(Collectors.toSet());
        Set<Double> positiveSet = sourceSet.stream().filter(o -> o > 0).collect(Collectors.toSet());

        List<Set<Double>> resultList = new ArrayList<>();

        // two compination
        negativeSet.forEach(o -> positiveSet.forEach(p -> {
            if(o + p == target) {
                resultList.add(new HashSet<>(Arrays.asList(o,p)));
            }
        }));

        // remove the two compination from source set.
        resultList.forEach(o -> o.forEach(p -> {
            positiveSet.remove(p);
            negativeSet.remove(p);
        }));


        System.out.println("111");
    }

    public static void main(String[] args) {
        /*Set<Double> sourceSet = Test1_2.getSourceSet();
        findTwoCompination(sourceSet,0);*/

        Double a = 0.0;
        System.out.println(a == 0);
    }
}

package com.cj.httpClient.test;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test1 {

    public static Set<Integer> getSourceData() {
        Set<Integer> set = new HashSet<>();
        set.add(-1);
        set.add(1);
        set.add(-2);
        set.add(2);
        set.add(-3);
        set.add(3);
        set.add(-4);
        set.add(4);
        set.add(-5);
        set.add(-6);
        set.add(6);
        return set;
    }

    public static void mode1() {
        int a[] = {20, 10, 40, 23, 30, 33, 12, 77, 70, 90, 67, 88, 80, 60, 50};
        int b[] = new int[a.length];
        for (int i = 0; i < a.length; i++)
            b[i] = 100 - a[i];
        System.out.print("两个数之和为100：");
        for (int i = 0; i < a.length; i++)
            if (exists(a, b[i]) && a[i] < b[i])
                System.out.print("(" + a[i] + "," + b[i] + ")" + " ");
        System.out.println();

        System.out.print("三个数之和为100：");
        int c[][] = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a.length; j++)
                c[i][j] = b[i] - a[j];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a.length; j++)
                if (exists(a, c[i][j]) && a[i] < c[i][j] && c[i][j] < a[j])
                    System.out.print("(" + a[i] + "," + c[i][j] + "," + a[j] + ")" + " ");
    }

    private static boolean exists(int a[], int num) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == num)
                return true;
        }
        return false;
    }

    public static void mode2(Set<Integer> sourceSet) {
        int targetNum = 0;
        HashSet<Set<Integer>> resultSet = new HashSet<>();

        for (Integer sourceInt : sourceSet) {
            int temp = targetNum - sourceInt;
            if (sourceSet.contains(temp)) {
                HashSet<Integer> subSet = new HashSet<>();
                subSet.add(sourceInt);
                subSet.add(temp);
                resultSet.add(subSet);
            }
        }

        System.out.println(resultSet);
    }

    public static void mode3(Set<Integer> sourceSet) {
        int targetNum = 0;


    }

    static ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
    static LinkedList<Integer> fuck = new LinkedList<>();

    /**
     * @param sum   目标结果数,例如100.
     * @param arr   源数据数组
     * @param start 当前递归的下标,例如首次是0.
     * @param end   用来跟start判断的如果小于start就结束递归,例如首次是100.
     */
    public static void findList(int sum, int[] arr, int start, int end) {
        if (start >= end) { //结束递归
            return;
        }
        if (sum == arr[start]) { //结束递归
            ArrayList<Integer> list = new ArrayList<>();
            for (Integer num : fuck) {
                list.add(num);
            }
            list.add(arr[start]);
            lists.add(list);
        } else {
            if (sum > arr[start]) { //正常递归
                fuck.add(arr[start]); //数组当前下标的元素.例如0,1,2
                findList(sum - arr[start], arr, start + 1, end);
                fuck.remove(fuck.size() - 1);
            }
            findList(sum, arr, start + 1, end);
        }
    }


    /*
     * 函数功能：以字符串形式返回1~n个数的所有子集，其中0代表不包含其中数字i，1代表 包含其中数字i
     * 此段代码是运用反射格雷码的思想，具体解释详见：算法笔记_019:背包问题（Java）
     */
    public static String[] getAllGroup(int n) {
        int len = (int) Math.pow(2, n);
        String[] result = new String[len];
        if (n == 1) {
            result[0] = "0";
            result[1] = "1";
            return result;
        }
        String[] temp = getAllGroup(n - 1);
        for (int i = 0; i < temp.length; i++) {
            result[i] = "0" + temp[i];
            result[len - 1 - i] = "1" + temp[i];
        }
        return result;
    }

    /**
     * 参数n:代表有1~n的n个不同整数
     * 函数功能：打印出1~n中所有随机组合的几个数，其相加的和等于sum
     */
    public static void printManySumN(int n, int sum) {
        System.out.println("1~" + n + "个数中，相加之和等于" + sum + "的所有组合数为：");
        String[] allGroup = getAllGroup(n);
        for (int i = 0; i < allGroup.length; i++) {
            char[] temp = allGroup[i].toCharArray();
            int tempSum = 0;
            for (int j = 0; j < temp.length; j++) {
                if (temp[j] == '1')
                    tempSum += (j + 1);
            }
            if (tempSum == sum) {
                for (int j = 0; j < temp.length; j++) {
                    if (temp[j] == '1')
                        System.out.print((j + 1) + " ");
                }
                System.out.println();
            }
        }
    }

    /**
     * 0
     * 2 -3 1
     * 2 -3 1 -4 4
     * 2 -3 1 -4 4 -5 5
     */
    //{0, -1, 2, -3, 1,-4, 4, -5, 5}  共计9位
    public static void test1(int[] arr) {
        Map<Integer, Integer> sumMap = new HashMap<>();

        Integer sum = 0; //所有元素之和
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i]; //算和

            if (sum == 0) { //打印0值元素本身
                printSubArray(arr, 0, i);
            }

            if (sumMap.get(sum) != null) { //i=4时进来,sum=-1了(下面打印2,-3,1);  i=6时sum=1,再次进来(下面打印第2-6位). i=8是sum=-1,再次进来
                printSubArray(arr, sumMap.get(sum) + 1, i);
            } else {
                sumMap.put(sum, i);//0,0; -1,1; 1,2; -2,3; -5,5; -6,7;  ...
            }
        }
    }


    public static void printSubArray(int[] arr, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void printSubList(List list, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }

    /**
     * subset : { 0 - 0 }
     * subset : { 2 - 4 }
     * subset : { 5 - 6 }
     * subset : { 7 - 8 }
     * HASH MAP HAS: {-1=8, 1=2, -2=3, -5=5, -6=7}
     */
    public static void test2() {
        //int [] seed = new int[] {1,2,3,4,-9,6,7,-8,1,9};

        int seed[] = {0, -1, 2, -3, 1, -4, 4, -5, 5}; //0  //2, -3, 1  //-4,4  //-5, 5
        //int seed[] = {0, -1, 1, 2, -3, -4, 4, -5, 5};

        int currSum = 0;
        HashMap<Integer, Integer> sumMap = new HashMap<>();

        for (int i = 0; i < seed.length; i++) {
            currSum += seed[i];

            if (currSum == 0) {
                System.out.println("subset : { 0 - " + i + " }");
            } else if (sumMap.get(currSum) != null) {
                System.out.println("subset : { " + (sumMap.get(currSum) + 1) + " - " + i + " }");
                //printSubArray(seed, sumMap.get(currSum) + 1, i);
                sumMap.put(currSum, i);  //key!
            } else
                sumMap.put(currSum, i);
        }
        System.out.println("HASH MAP HAS: " + sumMap);
    }

    /**
     * subset : { 0 - 0 }
     * subset : { 0 - 2 }
     * subset : { 2 - 4 }
     * subset : { 5 - 6 }
     * subset : { 7 - 8 }
     */
    public static void test3_set() {
        Set<Integer> sourceSet = new HashSet<>();
        sourceSet.addAll(Arrays.asList(0, -1, 2, -3, 1, -4, 4, -5, 5));

        List<Integer> dataSource = new ArrayList<>(sourceSet);

        int currSum = 0;
        HashMap<Integer, Integer> sumMap = new HashMap<>();

        for (int i = 0; i < dataSource.size(); i++) {
            currSum += dataSource.get(i);

            if (currSum == 0) {
                //System.out.println("subset : { 0 - " + i + " }");
                //System.out.println();
                printSubList(dataSource, 0, i);
            } else if (sumMap.get(currSum) != null) {
                //System.out.println("subset : { " + (sumMap.get(currSum) + 1) + " - " + i + " }");
                printSubList(dataSource, sumMap.get(currSum) + 1, i);
                sumMap.put(currSum, i);  //key!
            } else
                sumMap.put(currSum, i);
        }
        System.out.println("HASH MAP HAS: " + sumMap);
    }

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
                resultList.add(getSubSet(dataSource,0,i));
            } else if (sumMap.get(sum) != null) {
                resultList.add(getSubSet(dataSource,sumMap.get(sum) + 1,i));
                sumMap.put(sum, i);
            } else
                sumMap.put(sum, i);
        }

        System.out.println("Result subSets are :" + resultList);
        System.out.println("SumMap is : " + sumMap);
    }

    private static Set<Integer> getSubSet(List<Integer> list, int startIndex, int endIndex) {
        HashSet<Integer> subSet = new HashSet<>();
        for (int i = startIndex; i <= endIndex; i++) {
            subSet.add(list.get(i));
        }
        return subSet;
    }


    public static void main(String[] args) {
        //mode1();
        //mode2(getSourceData());

        /*int arr[] = new int[100];
        for(int i=0;i<100;i++){
            arr[i] = i+1;
        }
        //100是查找和为100的数字，0是数组开始位置，arr.length-1是数组结束位置
        findList(100,arr,0,arr.length-1);


        for(ArrayList list :lists){
            System.out.println(list);
        }*/

        //printManySumN(10, 10);

        //int arr1[] = {0, -1, 2, -3, 1,-4,4,-5,5};  //错误
        //int arr2[] = {9, 2, -1, -3, 4,-2, 2, 4, 6, 0};
        //test1(arr1);

        //test2();

        //System.out.println("=========================");

        //test3_set();

        getAnswerForTest1();

    }

}

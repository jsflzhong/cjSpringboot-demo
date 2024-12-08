package search;

import java.util.Arrays;

/**
 * 二分查找
 * 查找目标值在数组中的下标.
 *
 * 二分查找, 只能用于[有序]元素列表.
 */
public class BinarySearch {

    //统计二分的次数
    static int times = 0;

    public static int[] getSourceArray() {
        int[] sourceArray = new int[100];

        for (int i = 0; i < 100; i++) {
            sourceArray[i] = i + 1;
        }
        return sourceArray;
    }

    /**
     * middle=永远是整个数组的中间值, 例如50.
     * tempMiddle=每次进行二分后的新的小数组的middle,例如25, 然后12, etc.
     * start=每次二分后的新的小数组的最小值(最左边的值)
     * <p>
     * 思路:
     * 1.先确定出每次切分后需要几个值, 把它们定义成变量放在前面.
     * 2.不要推导数字, 要推导模型图!! 定义好几个变量, 每一步都考虑变量的值的变化, 而不要推导数字值!
     * 3.先把思路像下面这样捋清并写出, 然后再写代码, 效率反而会很高.
     * <p>
     * 具体思路:
     * 1.如果值在左边, 则:
     * 左端点 = 不变
     * 右端点 = 老中间点
     * 中间点 = 老左端点 + ((老中间点 - 老左端点 ) / 2)
     * <p>
     * 2.如果值在右边, 则:
     * 左端点 = 老中间点
     * 中间点 = 老中间点 + ((老右端点 - 老中间点) / 2)
     * 右端点 = 不变
     *
     * 时间计算:
     * 2的6次方=64, 2的7次方=128.  所以如果目标是100个数的话, 最多只需要7次就可以查出.
     */
    public static void binarySearch(int[] sourceArray, int targetNumber) {
        int length = sourceArray.length;
        int sourceMiddle = length / 2;

        //定义三个初始点
        int left = 0;
        int middle = sourceMiddle;
        int right = length - 1;

        //值在左边
        while (targetNumber < sourceArray[middle]) {
            right = middle;
            middle = left + ((middle - left) / 2);
            times++;
        }

        //值在右边
        while (targetNumber > sourceArray[middle]) {
            left = middle;
            middle = middle + ((right - middle) / 2);
            times++;
        }

        if (sourceArray[middle] == targetNumber) {
            System.out.println(String.format("@@@Success! Target position is : %d, times is : [%d] times", middle, times));
        } else {
            binarySearch(Arrays.copyOfRange(sourceArray, left, right), targetNumber);
        }
    }


    public static void main(String[] args) {
        binarySearch(getSourceArray(), 78);
    }
}
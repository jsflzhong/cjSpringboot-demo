package Guava;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class TableTest {

    private static final Table<String, String, List<String>> table = HashBasedTable.create();

    static {
        table.put("key1_1","key1_2", Arrays.asList("1","2"));
        table.put("key2_1","key2_2", Arrays.asList("3","4"));
    }

    public static void main(String[] args) {
        test1();
    }

    /**
     * 概念:
     *  Table中有三个值, 分别命名为: row, column, value. 分别对应: key1, key2, value.
     */
    public static void test1() {
        /**{key1_1={key1_2=[1, 2]}, key2_1={key2_2=[3, 4]}}*/
        System.out.println(table);

        /***********************判断是否含有指定的row和column***************************/
        /**true*/
        if(table.contains("key1_1","key1_2")) {
            System.out.println("111");
        }

        /**false*/
        if(table.contains("key1_1","key2_1")) {
            System.out.println("222");
        }

        /***********************根据column拿出来row和value***************************/
        /**{}*/
        Map<String, List<String>> column1 = table.column("key1_1");
        System.out.println(column1);

        /**{key1_1=[1, 2]}*/
        Map<String, List<String>> column2 = table.column("key1_2");
        System.out.println(column2);


        /***********************测试如果column相同, 那么数据是否会覆盖***************************/
        /**{key1_1={key1_2=[1, 2]}, key2_1={key2_2=[3, 4]}, key1_3={key1_2=[5, 6]}}  不会覆盖,会新增*/
        table.put("key1_3","key1_2", Arrays.asList("5","6"));
        System.out.println(table);


        /***********************测试如果row相同, 那么数据是否会覆盖***************************/
        /**{key1_1={key1_2=[1, 2], key1_2_new=[5, 6]}, key2_1={key2_2=[3, 4]}, key1_3={key1_2=[5, 6]}}
         * 不会覆盖,但是注意, 会在同一个row下新增一列column. 这也是column这个名词的由来.
         *  一行可以有多列!*/
        table.put("key1_1","key1_2_new", Arrays.asList("5","6"));
        System.out.println(table);


        /***********************测试如果row和column都相同, 那么数据是否会覆盖***************************/
        /**{key1_1={key1_2=[a, b], key1_2_new=[5, 6]}, key2_1={key2_2=[3, 4]}, key1_3={key1_2=[5, 6]}}
         * 注意: 只会覆盖: 同row同column的那个单元格的数据, 而不会覆盖同row的其他column列.
         * */
        table.put("key1_1","key1_2", Arrays.asList("a","b"));
        System.out.println(table);


        /**根据key1, key2, 也就是根据row和column拿出value*/
        /**[a, b]*/
        List<String> strings = table.get("key1_1", "key1_2");
        System.out.println(strings);

    }
}

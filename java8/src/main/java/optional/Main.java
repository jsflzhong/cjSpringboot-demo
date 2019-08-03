package optional;

import interfaceNew.Exception.BusinessException;
import interfaceNew.functionalInterface.JudgeAndExecute;
import interfaceNew.parameter.BusinessParam;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Main implements JudgeAndExecute {

    public static void main(String[] args) {
        //testString();

        //testObject();

        //testList();

        //testMap();

        testGetFromListAndMap();
    }

    /**
     * 用Optional来检查字符串String
     * 结论: Optional可以拿来检查String, 可以查出来null, 但是查不出来空值:"", 该空值会被认为非空.
     */
    static void testString() {
        String s1 = "";
        String s2 = null;

        //test s1
        System.out.println("@@@@@@test s1=========");
        Optional.ofNullable(s1).ifPresent(o -> System.out.println("@@@s1 is not null!")); //执行
        //不要写成throw new ...
        Optional.ofNullable(s1).orElseThrow(() -> new BusinessException("@@@s1 is null!!")); //未执行

        //test s2
        System.out.println("@@@@@@test s2=========");
        Optional.ofNullable(s2).ifPresent(o -> System.out.println("@@@s2 is not null!")); //未执行
        //不要写成throw new ...
        Optional.ofNullable(s2).orElseThrow(() -> new BusinessException("@@@s2 is null!!")); //执行

    }

    /**
     * 用Optional来检查对象.
     * 结论: 可以,没问题.
     */
    static void testObject() {
        BusinessParam param1 = new BusinessParam();
        BusinessParam param2 = null;

        //test param1
        Optional.ofNullable(param1).ifPresent(o -> System.out.println("@@@param1 is not null!"));//执行
        Optional.ofNullable(param1).orElseThrow(() -> new BusinessException("@@@param1 is null!"));//未执行

        //test param2
        Optional.ofNullable(param2).ifPresent(o -> System.out.println("@@@param2 is not null!"));//未执行
        Optional.ofNullable(param2).orElseThrow(() -> new BusinessException("@@@param2 is null!"));//执行
    }

    /**
     * 用Optional来检查List.
     * 结论: 可以,但是空元素的list会被认为是非空, foreach这种List不会触发异常.
     */
    static void testList() {
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = null;

        for (String s : list1) {
            //由于list1里面并没有元素,所以这里不会进来执行.
            System.out.println("@@@迭代.." + s);
        }

        //test list1
        Optional.ofNullable(list1).ifPresent(o -> System.out.println("@@@list1 is not null!"));//执行
        Optional.ofNullable(list1).orElseThrow(() -> new BusinessException("@@@list1 is null!"));//未执行

        //test list2
        Optional.ofNullable(list2).ifPresent(o -> System.out.println("@@@list2 is not null!"));//未执行
        Optional.ofNullable(list2).orElseThrow(() -> new BusinessException("@@@list2 is null!"));//执行

    }

    /**
     * 用Optional来检查Map.
     * 结论: 可以,但是空元素的Map会被认为是非空.
     */
    static void testMap() {
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = null;

        //test map1
        Optional.ofNullable(map1).ifPresent(o -> System.out.println("@@@map1 is not null!"));//执行
        Optional.ofNullable(map1).orElseThrow(() -> new BusinessException("@@@map1 is null!"));//未执行

        //test list2
        Optional.ofNullable(map2).ifPresent(o -> System.out.println("@@@map2 is not null!"));//未执行
        Optional.ofNullable(map2).orElseThrow(() -> new BusinessException("@@@map2 is null!"));//执行

    }

    /**
     * 测试用Optional判断容器后再继续从该容器中拿值.
     * 注意: 当list中没有任何元素时,如果在下面直接get(0)时,会抛异常:IndexOutOfBoundsException!
     * 处理list还是推荐用Stream.
     */
    static void testGetFromListAndMap() {
        //测试用的list
        ArrayList<BusinessParam> list = new ArrayList<>();
        list.add(new BusinessParam().setOrderId("123"));

        //测试用的map
        HashMap<String, BusinessParam> map = new HashMap<>();
        map.put("key1", new BusinessParam().setOrderId("567"));

        //测试从List中判断和取值
        String orderId = Optional.ofNullable(list)//先判断list是空不.
                //再从前面的list中拿出一个元素
                .map(o -> o.get(0))
                //再从前面拿出的元素中,拿出该元素的一个属性
                .map(p -> p.getOrderId())
                //如果list是空,则从这里抛异常.
                .orElseThrow(() -> new BusinessException("@@@list is null!!"));

        if (StringUtils.isNotEmpty(orderId)) {
            System.out.println("@@@测试list,第一个元素(对象)的orderId字段的值是:" + orderId);
        }

        //测试从Map中判断和取值
        String orderId2 = Optional.ofNullable(map)//先判断map是空不.
                //再从前面的map中拿出一个元素
                .map(o -> o.get("key1"))
                //再从前面拿出的元素中,拿出该元素的一个属性
                .map(p -> p.getOrderId())
                //如果map是空,则从这里抛异常.
                .orElseThrow(() -> new BusinessException("@@@map is null!!"));

        if (StringUtils.isNotEmpty(orderId2)) {
            System.out.println("@@@测试map,key1的value(对象)的orderId字段的值是:" + orderId2);
        }



        //测试在map的形式中,遇到空的容器
        List<String> list2 = null; //如果用这个list,会又下面的自定义异常处抛出异常.
        //注意这种情况!!!
        //List<String> list3 = new ArrayList(); 如果用这个list,则在下面get(0)时,会抛异常:IndexOutOfBoundsException!
        String orderId3 = Optional.ofNullable(list2)
                .map(o -> o.get(0))
                .orElseThrow(() -> new BusinessException("@@@list is null!!"));
        if(StringUtils.isNotEmpty(orderId3)) {
            System.out.println("@@@orderId3:" + orderId3);
        }
    }
}

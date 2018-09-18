package com.cj.concurrent.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by jsflz on 2018/8/10.
 * 测试:ExecutorCompletionService
 * 作用:一条线程A调用take()进入阻塞,等每个其他线分别调用ExecutorCompletionService的submit()返回结果后,该线程A每次都会醒来继续执行.
 * API:
 *  submit() - 提交任务
 *  take() - 获取任务结果
 *  poll() - 获取任务结果
 */
public class TestExecutorCompletionService {

    /**
     * 工具类对外暴露的接口
     * 分出来三条子线程,包住三个查询任务. 每个任务都单独的查询一次DB,并返回查询的结果.
     * 优化:
     * 1.ExecutorCompletionService每次都分别写了启动任务的代码. 是否可以抽取到一处?
     * 2.还是那个问题, 主线程在得到返回值后,如何知道这次是哪条子线程返回的查询? 从而针对不同的查询做不同的数据处理?
     * 是否可以在每个子线程的任务中AI中的处理方法中,在查询DB后,对该返回的pojo添加一个flag? 然后在主线程那边统一用switch case做匹配?
     * 即是否需要在pojo中做一个临时字段用来保存flag?
     * 问题是,每条查询返回的pojo不一定是同一个类. 所以这些实体类中都要加个这个flag字段?
     * 注意: 对最终的JSON数据的处理,最好不要在各个子线程中做,因为涉及到同步问题了,会消耗性能. 在主线程中串行做就好.
     * 或者,可以让他们分别返回不同的JSON段?然后在主线程中拼接他们?
     * 或者,可以定义一个公共的JSON,然后在每个子线程中同步的的,在查询完数据后,更新它的内容,最后在主线程中一并返回即可.
     *
     * @author cj
     */
    public List<testPojo> test1() {
        ExecutorService executorService = null;
        ArrayList<testPojo> testPojoList = null;
        try {
            //由主线程使用目标pojo和executorService来构造一个ExecutorCompletionService实例.
            executorService = Executors.newFixedThreadPool(2);
            ExecutorCompletionService<testPojo> completionService = new ExecutorCompletionService<>(executorService);

            //实际业务中,子线程的创建和执行,可以在for循环中进行.这里为了清晰所以分别创建.

            //由主线程创建子线程1 (API: submit() - 提交任务)
            completionService.submit(new Callable<testPojo>() { //要用completionService来执行线程任务!
                @Override
                public testPojo call() throws Exception {
                    System.out.println("进入子线程1,开始处理业务1......");

                    //可以在这里处理业务代码...
                    testPojo testPojo = new testPojo("1");

                    //Thread.sleep(1000);
                    System.out.println("子线程1已经完成业务处理,将要返回查询结果.");
                    return testPojo; //子线程在处理完业务后,可以返回业务实体.
                }
            });

            //由主线程创建子线程2 (API: submit() - 提交任务)
            completionService.submit(new Callable<testPojo>() { //要用completionService来执行线程任务!
                @Override
                public testPojo call() throws Exception {
                    System.out.println("进入子线程2,开始处理业务2......");

                    //可以在这里处理业务代码...
                    testPojo testPojo = new testPojo("2");

                   // Thread.sleep(1000);
                    System.out.println("子线程2已经完成业务处理,将要返回查询结果.");
                    return testPojo; //子线程在处理完业务后,可以返回业务实体.
                }
            });

            //处理上面2条子线程查询的结果...
            //生成一个用于聚拢所有子线程执行后返回的数据的容器.
            testPojoList = new ArrayList<>();
            //由于知道了上面是查询了2次,所以固定了循环的次数.
            for (int i = 0; i < 2; i++) {
                try {
                    //利用视图阻塞队列,来阻塞着等待上面返回的结果.(这就是用completionService的理由,可以用阻塞来代替循环.)
                    System.out.println("@@@主线程将进入阻塞,等待各子线程的返回.当前阻塞第:" + (i + 1) + "次.@@@");
                    //take() - 获取任务结果
                    Future<testPojo> future = completionService.take();
                    //这里可以获取:每个子线程任务中的返回结果.
                    testPojo testTb = future.get();
                    System.out.println("@@@主线程已获取到结果: " + (i + 1) + " ,返回的pojo的id是: " + testTb.getId() + ".@@@");
                    testPojoList.add(testTb);
                } catch (InterruptedException e) { //注意,这些异常在项目service中真正应用时,还要考虑是否应该抓还是抛! 因为事务!
                    e.printStackTrace();
                    //必须在catch里恢复被中断的状态!!!
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //ExecutorService使用完一定要关闭 (回收资源, 否则系统资源耗尽!)
            executorService.shutdown();
        }

        return testPojoList;
    }

    /**
     * 测试用pojo,
     * 为上面子线程任务一个实体
     * 测试可以在子任务结束后,可以返回所处理的实体的id或其他信息.
     *
     * @author cj
     */
    public class testPojo {
        private String id;

        public String getId() {
            return id;
        }

        public testPojo setId(String id) {
            this.id = id;
            return this;
        }

        public testPojo(String id) {
            this.id = id;
        }
    }

    public static void main(String[] args) {
        TestExecutorCompletionService testExecutorCompletionService = new TestExecutorCompletionService();
        List<testPojo> testPojos = testExecutorCompletionService.test1();
        System.out.println("@@@主线程已经收拢完所有子线程执行完成的业务数据.聚拢用的容器内有:" + testPojos.size() + "个元素@@@");
    }

    /**
     * 测试log:
     *
     @@@主线程将进入阻塞,等待各子线程的返回.当前阻塞第:1次.@@@ 进入子线程1, 开始处理业务1......
     进入子线程2,开始处理业务2......
     子线程1已经完成业务处理,将要返回查询结果.
     子线程2已经完成业务处理,将要返回查询结果.
     @@@主线程已获取到结果: 1 ,返回的pojo的id是: 1.@@@
     @@@主线程将进入阻塞,等待各子线程的返回.当前阻塞第:2次.@@@
     @@@主线程已获取到结果: 2 ,返回的pojo的id是: 2.@@@
     @@@主线程已经收拢完所有子线程执行完成的业务数据.聚拢用的容器内有:2个元素@@@

     */
}

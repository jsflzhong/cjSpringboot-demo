package com.cj.listener.controller;

import com.cj.listener.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 测试用例:
     * 阻塞10秒,用来模拟业务处理所需要的时间,
     * 然后在请求到来时就立刻点击idea的红色按钮关闭容器,
     * 测试随容器关闭的Connector是否会在关闭容器前,优雅的处理完所有线程池中的任务.
     *
     * 测试结果:
     * 2019-07-27 10:24:30.458 WARN  --- [http-nio-8087-exec-1] c.c.l.controller.TestController - @@@开始处理请求
     * 2019-07-27 10:24:33.176 INFO  --- [Thread-18] o.s.b.w.s.c.AnnotationConfigServletWebServerApplicationContext - Closing org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@730a341d: startup date [Sat Jul 27 10:24:12 IRKT 2019]; root of context hierarchy
     * 2019-07-27 10:24:33.182 WARN  --- [Thread-18] c.c.l.l.s.ShutdownListener1 - @@@WEB应用准备关闭...
     * 2019-07-27 10:24:33.182 WARN  --- [Thread-18] c.c.l.l.s.ShutdownListener1 - @@@WEB开始执行关闭...
     * 2019-07-27 10:24:40.459 WARN  --- [http-nio-8087-exec-1] c.c.l.controller.TestController - @@@结束处理请求
     * 2019-07-27 10:24:40.493 INFO  --- [Thread-18] o.s.c.s.DefaultLifecycleProcessor - Stopping beans in phase 2147483647
     * 2019-07-27 10:24:40.494 INFO  --- [Thread-18] o.s.c.s.DefaultLifecycleProcessor - Stopping beans in phase 0
     * 2019-07-27 10:24:40.500 INFO  --- [Thread-18] o.s.j.e.a.AnnotationMBeanExporter - Unregistering JMX-exposed beans on shutdown
     * 2019-07-27 10:24:40.501 INFO  --- [Thread-18] o.s.j.e.a.AnnotationMBeanExporter - Unregistering JMX-exposed beans
     *
     * 测试结论: \
     * 可以正常优雅的先处理完线程池中的任务,再关闭容器.
     *
     * @return Object
     * @throws InterruptedException InterruptedException
     */
    @RequestMapping("/test1")
    public Object test1() throws InterruptedException {
        log.warn("@@@开始处理请求");
        //阻塞10秒,用来模拟业务处理所需要的时间,然后在请求到来时就立刻关闭容器,测试随容器关闭的Connector是否会在关闭容器前,优雅的处理完所有线程池中的任务.
        Thread.sleep(10000);
        log.warn("@@@结束处理请求");
        return testService.testService1();
    }
}

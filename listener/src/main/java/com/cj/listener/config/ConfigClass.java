package com.cj.listener.config;

import com.cj.listener.listener.shutdownListener.ShutdownListener1;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {

    /**
     * 可以在目标类上用@Component直接使其成为一个bean,而不用这里的这个方法.
     * 见:ShutdownListener1类
     * @return
     */
    /*@Bean
    public ShutdownListener1 shutdownListener1() {
        return new ShutdownListener1();
    }*/

    /**
     * 在容器的启动过程中, 把自定义的tomcat的connector, 注册进内嵌的 Tomcat 容器中.
     *
     * TomcatServletWebServerFactory 是 Spring Boot 实现内嵌 Tomcat 的工厂类，
     * 类似的其他 Web 容器，也有对应的工厂类如 JettyServletWebServerFactory，UndertowServletWebServerFactory。
     * 他们共同的特点就是继承同个抽象类 AbstractServletWebServerFactory，提供了 Web 容器默认的公共实现，如应用上下文设置，会话管理等。
     * 如果我们需要定义Spring Boot 内嵌的 Tomcat 容器时，就可以使用 TomcatServletWebServerFactory 来进行个性化定义.
     *
     *
     * @param shutdownListener1 自定义的随容器关闭而启动的connector.
     * @return ConfigurableServletWebServerFactory
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(final ShutdownListener1 shutdownListener1) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(shutdownListener1);

        //也可以利用这个factory来定制一些内容.
        //factory.setPort(8080);

        return factory;
    }
}

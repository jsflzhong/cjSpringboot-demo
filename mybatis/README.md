整合持久层mybatis:
        依赖见pom.xml
        测试表见:src/db/user_t.sql
        注意:
            启动类的注解需要指定dao.
        注意:
            业务Service接口和实现都需要继承BaseService.
        注意:
            启动类中的注解指定的:markerInterface = BaseMapper.class, 值这个BaseMapper要注意,并不是tk包里的第三方那个BaseMapper,而是自己的BaseMapper! 依赖要引入正确,否则启动报错.:
            tk.mybatis.mapper.mapperhelper.MapperTemplate.getEntityClass(MapperTemplate.java:220)
        注意:
            springBoot2.x的版本下,不能用druid连接池.否则启动报错,有一个spring的类找不到.       
            ava.lang.ClassNotFoundException: org.springframework.boot.bind.RelaxedDataBinder
            
        已通过测试.
            测试入口: com.cj.mybatis.controller.HelloWorldController

    整合mybatis代码生成器:
        依赖见pom.xml中的pluggin部分.(里面手动指定了该插件的配置文件的位置.即下行的内容.)
        配置见generator/generatorConfig.xml.
        执行方式: 双击: maven--Plugins--mybatis-generator-mybatis-generator:genrate
        已通过测试. user_t 的pojo,dao,mapper xml就是自动生成的.
        注意:
            这一版的mybatis代码生成器是相对比较成熟的,example等类全部抽象了,而不是每个Pojo都带一个,节省了方法区.
            同时,不但可以指定查询的条件列,还可以指定查询的目标列.
            见该Controller中的测试代码.

    事务管理:
        在Spring Boot中，当我们使用了spring-boot-starter-jdbc或spring-boot-starter-data-jpa依赖的时候,
        框架会自动默认分别注入DataSourceTransactionManager或JpaTransactionManager。
        所以我们不需要任何额外配置,就可以直接用@Transactional注解进行事务的使用。
        com.cj.mybatis.service.impl.UserTServiceImpl.testTransaction
        已通过测试.
        
Swagger:
    0.pom:
        <swagger-spring-boot-starter.version>1.9.0.RELEASE</swagger-spring-boot-starter.version>
        <dependency>
            <groupId>com.spring4all</groupId>
            <artifactId>swagger-spring-boot-starter</artifactId>
            <version>${swagger-spring-boot-starter.version}</version>
        </dependency>
    1.需要配置类开启swagger: com.cj.mybatis.config.ApiConfig
    2.测试入口: com.cj.mybatis.controller.HelloWorldController.testSwagger
    3.ui请求地址:http://localhost:8086/swagger-ui.html
   
   
TODO:
1.not null and not empty test --done
    com.cj.mybatis.controller.HelloWorldController.testParamNotNull

    1>.Use @NotNull in pojo
    2>.Use @Valid in param list in Controller
    3>.Use BindingResult in param list

2.transaction test

3.Is the list checked out from DB is not null by default? So we can foreach directly without any check? 

4.swagger all useful annotation. --done
    com.cj.mybatis.controller.HelloWorldController.testSwagger

5.test @RequestBody --done
    com.cj.mybatis.controller.HelloWorldController.testRequestBody
    
6.junit test --done
    see test package

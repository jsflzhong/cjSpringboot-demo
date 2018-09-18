整合shiro:
        0).shiro核心原理:
            shiro三大组件:
	        * Subject：当前用户，Subject 可以是一个人，但也可以是第三方服务、守护进程帐户、时钟守护任务或者其它--当前和软件交互的任何事件。
	        * SecurityManager：管理所有Subject，SecurityManager 是 Shiro 架构的核心，配合内部安全组件共同组成安全伞。
	        * Realms：用于进行权限信息的验证，我们自己实现。Realm 本质上是一个特定的安全 DAO：它封装与数据源连接的细节，得到Shiro 所需的相关的数据。在配置 Shiro 的时候，你必须指定至少一个Realm 来实现认证（authentication）和/或授权（authorization）。
            我们需要实现Realms的Authentication 和 Authorization。
            其中 :
            Authentication 是用来验证用户身份，
            Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
        1).生成5张权限核心表. 见/db/u_permission.sql
        2).用mybatis-generator生成了pojo+dao+mapper文件
        3).shiro核心的两个类:
            1.shiro配置类:com.michael.springBoot.ShiroConfiguration
                里面配置了:1.过滤器,2.安全管理器,2.自己扩展shiro写的realm.
                该类会在工程启动时执行,加上上述三种配置.
                注意,在过滤器链的配置中,配置的url都是controller资源的url,而不是直接的jsp路径.
            2.自己扩展shiro写的realm: com/michael/springBoot/tools/MyShiroRealm.java
                做了两项工作:
                1>.鉴权: 把当前登录用户的用户,角色集,权限集查出,放入shiro的SimpleAuthorizationInfo类中返回. 该重写方法会在每次前端页面解析到shiro的权限标签时都会执行一次! 除非做了匹配的缓存!
                2>.认证: 即登录认证.根据传入的username查DB,如果有该用户,则shiro会为我做好了密码比对.返回shiro的SimpleAuthenticationInfo类即可.
                         诸如密码不对等校验,都由shiro做好了,并返回对应的Exception. 只需要在controller中根据对应的Exception把不同的信息放入request域中返回给jsp显示即可.
            3.关于缓存:
                这两个方法应该在上层类AuthorizingRealm中加了缓存逻辑.但实际中还是在每次被调用.经测试:
                只有在下面的环节整合了ehcache后,在上层类AuthorizingRealm中的getAuthorizationInfo方法中的this.getAvailableAuthorizationCache()方法才会返回缓存. 否则返回null,即缓存无法使用.
                见下面额8)ehcache整合部分.

        4).其他自定义类:
            1.com.cj.shiro.controller.ShiroController  测试shiro的Controller. 内含六个handler.
            2.五张表对应的domain,dao,mapper文件. 例如domain为: User/UserRole/Role/RolePermission/Permission.
            3.table见sql文件.
        5).jsp:
            WEB-INF/jsp/403.jsp   没权限跳转页
            WEB-INF/jsp/login.jsp  登录页
            WEB-INF/jsp/user.jsp  用户列表页 --重点,内含大量shiro常用的标签.
            WEB-INF/jsp/user_edit.jsp 编辑用户页
        6).逻辑:
            1.系统加载时,执行shiro的配置类:ShiroConfiguration
            2.访问任意资源时,由shiro自动拦截和鉴权.如果没权限,返回到登录页.(在上面的配置类中配置的登录页jsp)
            3.在登录时,通过controller中的currentUser.login(token),来调用自定义realm: MyShiroRealm的认证方法:doGetAuthenticationInfo,如果该方法返回Null,则鉴权不通过.
                该方法只在登录时调用.
            4.在jsp页中解析到shiro权限标签时,会调用自定义realm: MyShiroRealm的鉴权方法:doGetAuthorizationInfo,如果该方法返回Null,则鉴权不通过.
                如果不在这里加缓存,则页面上每解析到一个shiro标签时,都会调用该方法一次! 注意该方法内会访问DB!
        7).ehcache整合:
            pom
            com.michael.springBoot.ShiroConfiguration.getEhCacheManager  配置一个缓存bean.
            ehcache-shiro.xml   缓存参数配置
            com.michael.springBoot.ShiroConfiguration.securityManager   把缓存加入自定义的realm中,realm上层类中的获取缓存的代码逻辑才会生效.
        8).已实现功能/待实现功能:
            1>.已实现:
            
                1.可以通过shiro进行jsp页面的"局部元素的控制".
                    相当于jeecg的<hasPermission>标签的功能.
                    由各个shiro内置的标签配合realm实现.见user.jsp中的各个标签.(即这种标签: <shiro:hasXXX> )
                    
                2.可以通过shiro进行jsp"全局元素的控制",即;整个页面的可见/不可见控制.
                    由shiro配置类 ShiroConfiguration 中的过滤器链的参数:perms 或 roles 来控制.
                    
            2>.待实现:
                1. shiro+redis集成，避免每次访问有权限的链接都会去执行MyShiroRealm.doGetAuthenticationInfo()方法来查询当前用户的权限，因为实际情况中权限是不会经常变得，这样就可以使用redis进行权限的缓存。
                2. 实现shiro链接权限的动态加载，之前要添加一个链接的权限，要在shiro的配置文件中添加filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]")，这样很不方便管理，一种方法是将链接的权限使用数据库进行加载，另一种是通过init配置文件的方式读取。
                3. Shiro 自定义权限校验Filter定义，及功能实现。
                4. Shiro Ajax请求权限不满足，拦截后解决方案。这里有一个前提，我们知道Ajax不能做页面redirect和forward跳转，所以Ajax请求假如没登录，那么这个请求给用户的感觉就是没有任何反应，而用户又不知道用户已经退出了。
                5. Shiro JSP标签使用。
                6. Shiro 登录后跳转到最后一个访问的页面
                7. 在线显示，在线用户管理（踢出登录）。
                8. 登录注册密码加密传输。
                9. 集成验证码。
                10. 记住我的功能。关闭浏览器后还是登录状态。
                

再研究SpringBoot对jsp的支持:

     1.之前整合过的SpringBoot对jsp的支持虽然成功了,但是这次模块化后,打包中再次没有jsp了,老方法失效.
     2.新思路: 把项目打成war包而不是jar包,用外部tomcat启动.
        --该方式已亲测成功.
        过程:
        
        1>.排除内置tomcat的依赖:
            <!--外置tomcat-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
                <exclusions>
                    <exclusion>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-tomcat</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
        
        2>.改造项目的启动类Application.java, 使其继承SpringBootServletInitializer,并重写configure方法 
            具体看改造后的启动类:com.cj.shiro.Application
        
        3>.在idea里配置外部tomcat,deployment要指定目标模块,这次是: shiro:war explored
            所以这个外部tomcat只会编译和打包本shiro模块的代码.包括jsp. 可以在target下看到.
            
        4>.启动tomcat并访问即可.
            坑:原来的login.jsp中的form标签用了个属性:commandName, 该属性已经过时了,如果还用,就会在访问该页面时报错:
               Unable to find setter method for attribute: commandName
               解决方案:
               只需要把该属性替换成为:modelAttribute即可. 用法一样.   
             
        5>.测试.
            关于页面的"局部控制"和"全局控制",都在user.jsp中体现了. 该页面的文字说明已经完善.    
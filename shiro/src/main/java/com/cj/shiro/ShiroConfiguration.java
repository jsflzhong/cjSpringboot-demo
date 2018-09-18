package com.cj.shiro;

import com.cj.shiro.tools.MyShiroRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro的配置类
 * <p>
 * 配置shiro的拦截器链,安全管理器,自定义realm,缓存,等.
 * 2018/3/26
 *
 * @author cj
 */
@Configuration
public class ShiroConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    /**
     * 1.注入shiro过滤器工厂.
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 工程启动时会进入该方法.
     * <p>
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的,
     * 因为在初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明:
     * 1.页面局部元素控制:
     * 一个URL可以配置多个Filter，使用逗号分隔.shiro内置了几个Filter.
     * 当设置多个过滤器时，全部验证通过，才视为通过.
     * shiro内置的Filter: 见下个方法的注释
     * <p>
     * 2.页面整个访问控制:
     * 部分过滤器可指定参数，如perms，roles.
     * 表示访问某个资源时,只有具有某个权限(perms)或某个角色(roles),才能访问.
     *
     * @author cj
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 注意,下面拦截的大多是Controller资源,而不是jsp. 所以变更jsp路径后,不需要改变这里.
        // 配置:登录页的资源url.如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 配置:登录成功后要跳转的资源url
        shiroFilterFactoryBean.setSuccessUrl("/user");
        // 配置:未授权界面的资源url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 配置:配置过滤器链.
        loadShiroFilterChain(shiroFilterFactoryBean);

        logger.info("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 配置过滤器链.
     * 可以用通配符,来一次性控制某个controller下的所有handler,或控制单个handler.
     * 建议:
     * 每个Controller都在类头上定义整个类的url,方便权限做整体控制.
     * <p>
     * <p>
     * Shiro内置的FilterChain
     * <p>
     * anon	    org.apache.shiro.web.filter.authc.AnonymousFilter   不做权限检查
     * authc	org.apache.shiro.web.filter.authc.FormAuthenticationFilter  做登录认证检查
     * authcBasic	org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
     * perms	org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter    做权限检查(资源url全局控制)
     * port	    org.apache.shiro.web.filter.authz.PortFilter
     * rest	    org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
     * roles	org.apache.shiro.web.filter.authz.RolesAuthorizationFilter  做角色检查(资源url全局控制)
     * ssl		org.apache.shiro.web.filter.authz.SslFilter
     * user	    org.apache.shiro.web.filter.authc.UserFilter
     *
     * @param shiroFilterFactoryBean shiroFilterFactoryBean
     * @author cj
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        // 拦截器链需要的map.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap();
        // 配置不会被拦截的链接 顺序判断.
        // 两种内置的值: "authc" :所有url都必须认证通过才可以访问; "anon" :所有url都都可以匿名访问.
        // 注意: 以下url匹配的并不是jsp,而是controller里的各个handler的url

        // 登录handler.不用权限控制.
        filterChainDefinitionMap.put("/login", "anon");
        // 静态资源,不用权限控制.(静态资源目录要匹配,比如/static/css目录和static/js目录等)
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/ajaxLogin", "anon");
        // 登出handler.不用权限控制. handler里面的登出操作API,是由shiro封装好的.
        filterChainDefinitionMap.put("/logout", "anon");

        // testPerms handler.需要具有delete权限的人才能访问.(该权限是在我的realm的鉴权方法中,放入shiro安全管理器的).
        filterChainDefinitionMap.put("/testPerms", "perms[delete]");
        // testPermsAndRoles handler.需要具有delete权限,且具有admin角色的人才能访问.(该权限和角色是在我的realm的鉴权方法中,放入shiro安全管理器的).
        filterChainDefinitionMap.put("/testPermsAndRoles", "perms[delete],roles[admin]");

        // 注意,也可以用通配符,来一次性控制某个controller下的所有handler!(暂无该controller的代码)
        filterChainDefinitionMap.put("/testController/**", "authc,perms[user:edit]");
        // 亲测:第一参不是Controller的类名,而是类名上头的@RequestMapping中的根url.
        filterChainDefinitionMap.put("/helloWorldController/**", "authc,perms[add]");

        // 给编辑用户的功能,设置权限限制. (登录,且具有[user:edit]权限的用户才能进入该handler)
        filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");

        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边,否则会全部拦截导致代码不好使了;
        // 除了上面定义的url之外,其余全部url全都拦截(需要认证:authc,而不是鉴权.)!
        // 当在MyShiroRealm中通过登录认证后,这些拦截的资源才可见.(没加其他权限控制的前提下).
        // 这里可以灵活配置. 例如jeecg中就是所有资源都默认可见! 只有配置了并入库的资源才会分配后才可见!
        filterChainDefinitionMap.put("/**", "authc");//FIXME 为了便于其他功能的开发,暂时把值改成 anon, 以后改回 authc.

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * 2.注入自定义的安全管理器,供上面shiro过滤器链使用.
     * 每次shiro过滤器拦截到请求后,会委托给这里配置的securityManager来处理拦截数据的处理.
     * 这个securityManager需要注入下面配置的自定义realm
     *
     * @return SecurityManager
     * @author cj
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.该bean是在下面被配置的.
        securityManager.setRealm(myShiroRealm());
        //为自定义的realm添加缓存
        securityManager.setCacheManager(getEhCacheManager());
        return securityManager;
    }

    /**
     * 3.注入身份认证realm
     * (这个需要自己实现shiro的抽象父类,实现账号密码校验,权限等功能)
     *
     * @return MyShiroRealm
     * @author cj
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    /**
     * 4.注入shiro-ehcache缓存bean
     * 由于在加入此缓存后,热重启出现了错误,暂时注掉.
     *
     * @return EhCacheManager
     * @author cj
     */
    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

}

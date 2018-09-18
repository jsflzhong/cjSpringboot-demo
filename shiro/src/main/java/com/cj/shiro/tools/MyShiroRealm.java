package com.cj.shiro.tools;

import com.cj.shiro.dao.UserMapper;
import com.cj.shiro.domain.Permission;
import com.cj.shiro.domain.Role;
import com.cj.shiro.domain.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 身份认证realm
 * 针对shiro的AuthorizingRealm的实现.
 * 需实现接口的doGetAuthorizationInfo鉴权,和doGetAuthenticationInfo登录认证方法.
 * 2018/3/26
 *
 * @author cj
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private UserMapper userDao;

    /**
     * 登录认证
     * 在认证、授权内部实现机制中，最终处理都将交给Real进行处理。因为在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。
     * 通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO.
     * Shiro的认证过程最终会交由Realm执行，这时会调用Realm的getAuthenticationInfo(token)方法。
     * 该方法主要执行以下操作:
     * 1、检查提交的进行认证的令牌信息
     * 2、根据令牌信息从数据源(通常为数据库)中获取用户信息
     * 3、对用户信息进行匹配验证。
     * 4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
     * 5、验证失败则抛出AuthenticationException异常信息。
     * <p>
     * 而在我们的应用程序中要做的就是自定义一个Realm类，继承AuthorizingRealm抽象类，
     * 重载doGetAuthenticationInfo()，重写获取用户信息的方法。
     * doGetAuthenticationInfo的重写
     * <p>
     * 这个方法的函数栈上层,是login controller中的:currentUser.login(token)代码.
     *
     * @author cj
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) authenticationToken.getPrincipal();
        logger.info("authenticationToken.getCredentials():{} ",authenticationToken.getCredentials());
        //查出是否有此用户
        User user = getUserByUsername(username);
        if (user != null) {
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        }
        return null;
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     *
     * 经测试：本例中该方法的调用时机为需授权资源被访问时.
     * 例如:jsp页面使用 <shiro:hasRole></shiro:hasRole> 或 <shiro:hasPermission></shiro:hasPermission> 等标签时,每一个都会进来一次.
     *
     * 经测试：如果每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     *
     * 经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，
     * Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行.
     *
     * <p>
     * 已整合shiro-ehcache,在配置类中为安全管理器添加了缓存,会在上层调用这里之前检查缓存.
     * 缓存配置: AuthorizationCache ehcache-shiro.xml
     *
     * @author cj
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        //权限信息对象info,用来存放:查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //拿到登录人的username
        String username = (String) principalCollection.getPrimaryPrincipal();
        // String username = (String)SecurityUtils.getSubject().getPrincipal();作用同上,都是拿到登录人的username
        if (!StringUtils.isEmpty(username)) {
            User user = userDao.getUserRolePermissionByUsername(username);
            if (user != null) {
                for (Role role : user.getRoleList()) {
                    //****** 封装角色集合(供jsp标签使用:shiro:hasRole name="admin">admin角色登录显示此内容<br/></shiro:hasRole>)
                    authorizationInfo.addRole(role.getRole());
                    for (Permission p : role.getPermissions()) {
                        //****** 封装权限集合(供jsp标签使用:<shiro:hasPermission name="add">add权限用户显示此内容<br/></shiro:hasPermission>)
                        authorizationInfo.addStringPermission(p.getPermission());
                    }
                }
                return authorizationInfo;
            }
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        // 即,在这里配置的403页面: com/cj/shiro/ShiroConfiguration.java:62
        return null;
    }

    /**
     * 根据username查询user
     *
     * @param username username
     * @return User集合
     * @author cj
     */
    private User getUserByUsername(String username) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<User> users = userDao.selectByExample(example);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

}

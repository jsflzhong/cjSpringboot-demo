package com.cj.shiro.controller;

import com.cj.shiro.dao.UserMapper;
import com.cj.shiro.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author cj
 *         shiro的Controller
 *         2018/3/26
 */
@Controller
public class ShiroController {

    private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);

    @Autowired
    private UserMapper userDao;

    /**
     * 迁页: 登录页:login.jsp.
     *
     * @param model model
     * @return login.jsp
     * @author cj
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "shiro/login";
    }

    /**
     * 动作: 登录
     *
     * @param user               user pojo
     * @param bindingResult      bindingResult
     * @param redirectAttributes redirectAttributes
     * @return user or login的Controller
     * @author cj
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        String username = user.getUsername();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了下面的login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            return "redirect:/user";
        } else {
            token.clear();
            return "redirect:/login";
        }
    }

    /**
     * 动作: 退出登录
     *
     * @param redirectAttributes redirectAttributes
     * @return login Controller
     * @author cj
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return "redirect:/login";
    }

    /**
     * 迁页: 跳到没有权限页面.
     *
     * @return 403.jsp
     * @author cj
     */
    @RequestMapping("/403")
    public String unauthorizedRole() {
        logger.info("------没有权限-------");
        return "shiro/403";
    }

    /**
     * 迁页: 用户列表页user.jsp
     *
     * @param model model
     * @return user.jsp
     * @author cj
     */
    @RequestMapping("/user")
    public String getUserList(Map<String, Object> model) {
        model.put("userList", userDao.selectAll());
        return "shiro/user";
    }

    /**
     * 迁页: 用户信息修改页
     *
     * @param userid userid
     * @return user_edit.jsp
     * @author cj
     */
    @RequestMapping("/user/edit/{userid}")
    public String getUserList(@PathVariable int userid) {
        logger.info("------进入用户信息修改-------");
        return "shiro/user_edit";
    }

    /**
     * 迁页: testPerms.jsp.
     * 测试通过shiro用"权限",进行整个页面的访问控制.
     *
     * @return hello.jsp
     * @author cj
     */
    @RequestMapping("/testPerms")
    public String testPerms() {
        logger.info("------进入testPerms-------");
        return "/shiro/testPerms";
    }

    /**
     * 迁页: testPermsAndRoles.jsp.
     * 测试通过shiro用"权限"和"角色",进行整个页面的访问控制.
     *
     * @return hello.jsp
     * @author cj
     */
    @RequestMapping("/testPermsAndRoles")
    public String testPermsAndRoles() {
        logger.info("------进入testPermsAndRoles-------");
        return "shiro/testPermsAndRoles";
    }


}

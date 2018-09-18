<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><html>
<head>
    <title>用户列表</title>
</head>


<body>

    <h1>${message }</h1>
    <h1>用户列表(登录成功后要跳转的资源url)--<a href="${pageContext.request.contextPath }/logout">退出登录</a></h1>


    <h2>1.页面说明:</h2>
    本页是:/WEB-INF/jsp/shiro/user.jsp<br/>
    <font color="red">之所以在登录成功后,能够跳转到本页,是取决于"ShiroConfiguration"中的配置: shiroFilterFactoryBean.setSuccessUrl("/user");</font><br/>
    本页可以展示:如何通过shiro的多种内置标签,来实现页面元素的<font color="red"><b>局部可见性</b></font>和<font color="red"><b>全局可见性</b></font>.<br/>

    <br/>
    <br/>

    <h2>2.权限列表:</h2>
    <font color="red">功能说明: 通过使用角色role,可以控制页面上<b>"局部的可见性:"</b></font><br/><br/>

    <!--1.认证标签: 使页面元素在登录后可见-->
    <shiro:authenticated>如果用户如果已经登录,则会显示此内容<br/></shiro:authenticated><br/>

    <!--2.角色标签: 用角色role,控制jsp组件的局部显示(局部元素可见性控制可用这个!).-->
    <shiro:hasRole name="manager">如果用户拥有:manager 角色,则在登录后显示此内容<br/></shiro:hasRole>
    <shiro:hasRole name="admin">如果用户拥有:admin 角色,则在登录后显示此内容<br/></shiro:hasRole>
    <shiro:hasRole name="normal">如果用户拥有:normal 角色,则在登录后显示此内容<br/></shiro:hasRole><br/>
    <shiro:hasAnyRoles name="manager,admin">如果用户拥有:manager or admin 角色,则在登录后显示此内容.<br/></shiro:hasAnyRoles><br/>

    <!--3.principal标签: principal内封装的是用户的username-->
    显示当前登录用户名: <shiro:principal/><br/><br/>

    <!--4.权限标签: 用权限控制jsp组件的局部显示.(局部元素可见性控制也可用这个!)-->
    <shiro:hasPermission name="add">如果用户拥有: add 权限,则显示此内容<br/></shiro:hasPermission>
    <shiro:hasPermission name="user:query">如果用户拥有: user:query 权限, 则显示此内容<br/></shiro:hasPermission>
    <shiro:lacksPermission name="user:query">如果用户"不拥有": user:query 权限, 则显示此内容 <br/></shiro:lacksPermission>

    <br/>
    <br/>

    <h2>3.所有用户列表:</h2><br/>
    <font color="red">功能说明: 通过使用在 ShiroConfiguration 中对于资源handler的权限配置,可以控制页面上<b>"全局的可见性:"</b></font><br/><br/>
    在ShiroConfiguration中的配置: filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");<br/>
    表示: 对"/user/edit/**"资源(handler)的访问,必须是: "登录,且具有[user:edit]权限的用户",才能访问该资源.<br/>

    <ul>
        <c:forEach items="${userList }" var="user">
            <li>用户名：${user.username }----密码：${user.password }----
                <a href="${pageContext.request.contextPath }/user/edit/${user.id}">修改用户(是否能访问该资源,取决于: 在ShiroConfiguration中,对于该资源的权限配置.)）</a>
            </li>
        </c:forEach>
    </ul>
    <img alt="" src="${pageContext.request.contextPath }/pic.jpg">
    <script type="text/javascript" src="${pageContext.request.contextPath }/webjarslocator/jquery/jquery.js"></script>

    <br/>
    <br/>

</body>
</html>
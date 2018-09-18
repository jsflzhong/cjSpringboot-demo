<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><html><head><title>Login</title></head>

<body>
<h1>登录页面----${message }</h1>
<img alt="" src="${pageContext.request.contextPath }/pic.jpg">
<form:form action="${pageContext.request.contextPath }/login" modelAttribute="user" method="post">

    用户名：<form:input path="username"/>
    <form:errors path="username" cssClass="error"/>
    <br/>

    密码：<form:password path="password"/>
    <form:errors path="password" cssClass="error"/>
    <br/>

    <form:button name="button">提交</form:button>

</form:form></body>
</html>
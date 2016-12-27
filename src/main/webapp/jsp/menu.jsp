<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.content" />
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><fmt:message key="menu.brand"/></a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/jsp/main.jsp"><fmt:message key="menu.main"/></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="menu.tracks"/><b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#"><fmt:message key="menu.alphabet"/></a></li>
                        <li><a href="#"><fmt:message key="menu.performers"/></a></li>
                        <li><a href="#"><fmt:message key="menu.price"/></a></li>
                        <li><a href="#"><fmt:message key="menu.popular"/></a></li>
                    </ul>
                </li>
                <li><a href="#"><fmt:message key="menu.sales"/></a></li>
                <li><a href="#"><fmt:message key="menu.about"/></a></li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder=<fmt:message key="menu.search"/>>
                </div>
                <button type="submit" class="btn btn-default"><fmt:message key="menu.search"/></button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${empty isLogin}">
                    <li><a href="${pageContext.request.contextPath}/jsp/registration.jsp"><span class="glyphicon glyphicon-user"></span><fmt:message key="menu.registration"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/jsp/login.jsp"><span class="glyphicon glyphicon-log-in"></span><fmt:message key="menu.login"/></a></li>
                </c:if>
                <c:if test="${isLogin eq 'true'}">
                    <li><a href="${pageContext.request.contextPath}/jsp/account.jsp"><span class="glyphicon glyphicon-user"></span>${user.nickname}</a></li>
                    <li><a href="${pageContext.request.contextPath}/controller?command=logout"><span class="glyphicon glyphicon-log-out"></span><fmt:message key="menu.logout"/></a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>

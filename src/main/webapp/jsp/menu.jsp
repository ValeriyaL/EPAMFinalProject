<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.content"/>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><fmt:message key="menu.brand"/></a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/controller?command=main"><fmt:message
                        key="menu.main"/></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="menu.tracks"/><b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${pageContext.request.contextPath}/controller?command=tracks_order&comm=title"><fmt:message
                                    key="menu.alphabet"/></a></li>
                        <li>
                            <a href="${pageContext.request.contextPath}/controller?command=tracks_order&comm=price"><fmt:message
                                    key="menu.price"/></a></li>
                        <ctg:isLogined>
                            <li class="divider"></li>
                            <li><a href="#"><fmt:message key="menu.orders"/></a></li>
                        </ctg:isLogined>
                        <ctg:adminTag role="${role}">
                            <li class="divider"></li>
                            <li><a href="#"><fmt:message key="menu.tracks.add"/></a></li>
                            <li><a href="#"><fmt:message key="menu.tracks.reestablish"/></a></li>
                            <li class="divider"></li>
                        </ctg:adminTag>
                    </ul>
                </li>
                <li><a href="${pageContext.request.contextPath}/jsp/about.jsp"><fmt:message key="menu.about"/></a></li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder=<fmt:message key="menu.search"/>>
                </div>
                <button type="submit" class="btn btn-default"><fmt:message key="menu.search"/></button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <ctg:notLogined>
                    <li><a href="${pageContext.request.contextPath}/jsp/registration.jsp"><span
                            class="glyphicon glyphicon-user"></span> <fmt:message key="menu.registration"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/jsp/login.jsp"><span
                            class="glyphicon glyphicon-log-in"></span> <fmt:message key="menu.login"/></a></li>
                </ctg:notLogined>
                <ctg:isLogined>
                    <li><a href="${pageContext.request.contextPath}/jsp/account.jsp"><span
                            class="glyphicon glyphicon-user"></span> ${user.nickname}</a></li>
                    <li><a href="${pageContext.request.contextPath}/controller?command=logout"><span
                            class="glyphicon glyphicon-log-out"></span> <fmt:message key="menu.logout"/></a></li>
                </ctg:isLogined>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>

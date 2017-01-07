<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<html>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.content"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><fmt:message key="account.title"/></title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <link href="../css/main.css" rel="stylesheet">
    <link href="../css/account.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../js/jquery-3.1.1.min.js"></script>
</head>
<body>
<c:set var="page" value="path.page.account" scope="session"/>
<%@ include file="menu.jsp" %>
<c:if test="${not empty info}">
    <div class="alert alert-success col-lg-4 col-lg-offset-4">${info}</div>
</c:if>
<div class="form col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-12 "
     style="margin-bottom: 70px">
    <h2 class="text-center"><fmt:message key="account.label"/></h2>
    <div class="form-horizontal">
        <div class="form-group">
            <label class="control-label col-lg-6" for="lastName"><fmt:message
                    key="register.login"/>: ${user.nickname}</label>
            <div class="col-lg-6">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="change" value="login_change"/>
                    <button type="submit" id="lastname" name="command" value="account" class="btn btn-primary btn-sm">
                        <fmt:message
                                key="account.button.change"/></button>
                </form>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-lg-6" for="email"><fmt:message key="register.mail"/>: ${user.email}</label>
            <div class="col-lg-6">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="change" value="email_change"/>
                    <button type="submit" id="email" name="command" value="account" class="btn btn-primary  btn-sm">
                        <fmt:message
                                key="account.button.change"/></button>
                </form>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-lg-6" for="card"><fmt:message
                    key="register.card"/>: ${user.cardNumber}</label>
            <div class="col-lg-6">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="change" value="card_change"/>
                    <button type="submit" id="card" name="command" value="account" class="btn btn-primary  btn-sm">
                        <fmt:message
                                key="account.button.change"/></button>
                </form>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-lg-6" for="password"><fmt:message key="account.change.password"/></label>
            <div class="col-lg-6">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="change" value="password_change"/>
                    <button type="submit" id="password" name="command" value="account" class="btn btn-primary  btn-sm">
                        <fmt:message
                                key="account.button.change"/></button>
                </form>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-lg-6" for="money"><fmt:message key="account.money"/>: ${user.money}</label>
            <div class="col-lg-6">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="change" value="money_change"/>
                    <button type="submit" id="money" name="command" value="account" class="btn btn-primary  btn-sm">
                        <fmt:message
                                key="account.button.add"/></button>
                </form>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-lg-6"><fmt:message key="account.bonus"/>: ${user.bonus}%</label>
            <ctg:adminTag role="${role}">
                <div class="col-lg-6">
                    <a href="${pageContext.request.contextPath}/jsp/bonus.jsp">
                        <button type="submit" class="btn btn-info btn-sm"><fmt:message
                                key="account.button.admin.fill"/></button>
                    </a>
                </div>
            </ctg:adminTag>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/jquery-3.1.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
</body>

</html>

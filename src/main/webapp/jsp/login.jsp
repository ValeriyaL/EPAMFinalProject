<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.content"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><fmt:message key="login.title"/></title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <link href="../css/main.css" rel="stylesheet">
    <link href="../css/login.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../js/jquery-3.1.1.min.js"></script>
</head>
<body>
<c:set var="page" value="path.page.login" scope="session"/>
<%@ include file="menu.jsp" %>
<c:if test="${not empty mistake}">
    <div class="alert alert-danger col-lg-4 col-lg-offset-4">${mistake}</div>
</c:if>
<c:if test="${not empty info}">
    <div class="alert alert-success col-lg-4 col-lg-offset-4">${info}</div>
</c:if>
<div class="form col-lg-4 col-lg-offset-4  col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-12">
    <h2 class="text-center"><fmt:message key="login.label"/></h2>
    <form class="form-horizontal" id="logform" method="post" name="login"
          action="${pageContext.request.contextPath}/controller">
        <div class="form-group">
            <label class="control-label col-xs-3" for="lastName"><fmt:message key="login.login"/>:</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="lastName" name="nickname"
                       required data-parsley-length="[4,15]" data-parsley-required value="${nickname}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="inputPassword"><fmt:message key="login.password"/>:</label>
            <div class="col-xs-9">
                <input type="password" class="form-control" id="inputPassword" name="password"
                       required data-parsley-length="[4,20]" data-parsley-required value="${password}">
            </div>
        </div>
        <br/>
        <div class="form-group">
            <div class="col-xs-offset-3 col-xs-9">
                <button type="submit" class="btn btn-primary" name="command"
                        value="login"><fmt:message key="login.placeholder.enter"/></button>
            </div>
        </div>
    </form>
</div>
<%@ include file="footer.jsp" %>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/jquery-3.1.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/parsley.min.js"></script>
<script src="../js/i18n/ru.js"></script>
<script src="../js/i18n/en.js"></script>
<script>
    $(document).ready(function () {
        $('#logform').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>
</body>

</html>

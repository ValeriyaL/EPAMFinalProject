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
    <title><fmt:message key="bonus.title"/></title>
    <!-- Bootstrap -->
    <link href="../../css/bootstrap.css" rel="stylesheet">
    <link href="../../css/main.css" rel="stylesheet">
    <link href="../../css/login.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../../js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="../../js/register.js"></script>
</head>
<body>
<c:set var="page" value="path.page.bonus" scope="session"/>
<%@ include file="../common/menu.jsp" %>
<c:if test="${not empty mistake}">
    <div class="alert alert-danger col-lg-4 col-lg-offset-4">${mistake}</div>
</c:if>
<div class="form col-lg-4 col-lg-offset-4  col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-12">
    <h2 class="text-center"><fmt:message key="bonus.label"/></h2>
    <form class="form-horizontal" id="regform" method="post" name="bonus"
          action="${pageContext.request.contextPath}/controller">
        <div class="form-group">
            <label class="control-label col-xs-3" for="lastName"><fmt:message key="info.user"/>:</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="lastName" name="userNickname"
                       required data-parsley-length="[4,15]" data-parsley-required>
            </div>
        </div>
        <br/>
        <div class="form-group">
            <div class="col-xs-offset-3 col-xs-9">
                <button type="submit" class="btn btn-primary" name="command"
                        value="user_info"><fmt:message key="menu.find"/></button>
                <a href="${pageContext.request.contextPath}/controller?command=all_users" class="btn btn-primary">
                    <fmt:message key="changes.users.all"/>
                </a>
                <a href="${pageContext.request.contextPath}/jsp/client/account.jsp">
                    <button type="button" class="btn btn-default">
                        <fmt:message key="changes.button.back"/></button>
                </a>
            </div>
        </div>
    </form>
</div>
<%@ include file="../common/footer.jsp" %>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../../js/jquery-3.1.1.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/parsley.min.js"></script>
<script src="../../js/i18n/en.js"></script>
<script src="../../js/i18n/ru.js"></script>
<script>
    $(document).ready(function () {
        $('#regform').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>
</body>
</html>


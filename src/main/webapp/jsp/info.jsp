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
    <title><fmt:message key="info.title"/></title>
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
<c:set var="page" value="path.page.info" scope="session"/>
<%@ include file="menu.jsp" %>
<c:if test="${not empty info}">
    <div class="alert alert-success col-lg-4 col-lg-offset-4">${info}</div>
</c:if>
<div class="form col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-12 "
     style="margin-bottom: 70px">
    <h2 class="text-center"><fmt:message key="info.title"/></h2>
    <div class="form-horizontal">
        <div class="form-group ">
            <label class="control-label col-md-offset-2"><fmt:message
                    key="register.login"/>: ${userNickname}</label>
        </div>
        <div class="form-group">
            <label class="control-label col-md-offset-2"><fmt:message
                    key="bonus.comments.number"/>: ${numOfComm}</label>
        </div>
        <div class="form-group">
            <label class="control-label col-md-offset-2"><fmt:message
                    key="bonus.orders.number"/>: ${numOfOrders}</label>
        </div>
        <div class="form-group">
            <label class="control-label col-md-offset-2"><fmt:message
                    key="account.bonus"/>: ${bonus}%</label>
        </div>
        <h3 class="text-center"><fmt:message key="bonus.set"/></h3>
        <div class="col-md-offset-4">
            <form id="form" method="post" action="${pageContext.request.contextPath}/controller">
                <div class="form-group">
                    <div class=" col-lg-6">
                        <input type="number" class="form-control" name="bonus" data-parsley-required
                               data-parsley-range="[0,100]">
                    </div>
                </div>
                <input type="hidden" name="userNickname" value="${userNickname}">
                <div class="form-group row">
                    <div>
                        <button type="submit" name="command" value="set_bonus" class="btn btn-primary  btn-sm">
                            <fmt:message
                                    key="account.button.add"/></button>
                        <a href="${pageContext.request.contextPath}/jsp/bonus.jsp">
                            <button type="button" class="btn btn-default">
                                <fmt:message key="changes.button.bonus"/></button>
                        </a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
<%@ include file="footer.jsp" %>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/jquery-3.1.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/parsley.min.js"></script>
<script src="../js/i18n/en.js"></script>
<script src="../js/i18n/ru.js"></script>
<script>
    $(document).ready(function () {
        $('#form').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>
</body>

</html>

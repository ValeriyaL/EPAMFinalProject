<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.content" />
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><fmt:message key="label.error.caption"/></title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <link href="../css/main.css" rel="stylesheet">
    <link href="../css/error.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../js/jquery-3.1.1.min.js"></script>
</head>
<body>
<c:set var="page" value="path.page.error" scope="session"/>
<%@ include file="menu.jsp"%>
<img class="oops col-lg-4 col-lg-offset-4" src="../images/oops.png">
<div class="form col-lg-4 col-lg-offset-4">
    <div class="row">
        <label class="control-label"><fmt:message key="label.error.message"/></label>
    </div>
    <div class="row">
        <label class="control-label"><fmt:message key="label.error.more"/></label>
    </div>
    <div class="row">
        <label class="control-label">${errorMessage}</label>
    </div>
    <div class="row">
    <label class="control-label"><fmt:message key="label.error.request"/> ${pageContext.errorData.requestURI} <fmt:message key="label.error.failed"/></label>
    </div>
    <div class="row">
        <label class="control-label"><fmt:message key="label.error.code"/>: ${pageContext.errorData.statusCode}</label>
    </div>
    <div class="row">
        <label class="control-label"><fmt:message key="label.error.exception"/>: ${pageContext.errorData.throwable}</label>
    </div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/jquery-3.1.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/parsley.min.js"></script>
<script src="../js/i18n/ru.js"></script>
<script src="../js/i18n/en.js"></script>
<script>
    $(document).ready(function(){
        $('#regform').parsley();
    });
</script>
</body>

</html>


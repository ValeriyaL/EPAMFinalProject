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
    <title><fmt:message key="add.title"/></title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <link href="../css/main.css" rel="stylesheet">
    <link href="../css/registration.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="../js/register.js"></script>
</head>
<body>
<c:set var="page" value="path.page.add" scope="session"/>
<%@ include file="menu.jsp" %>
<c:if test="${not empty mistake}">
    <div class="alert alert-danger col-lg-4 col-lg-offset-4">${mistake}</div>
</c:if>
<div class="form col-lg-4 col-lg-offset-4  col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-12">
    <h2 class="text-center"><fmt:message key="add.label"/></h2>
    <form class="form-horizontal" id="addform" method="post" name="add"
          action="${pageContext.request.contextPath}/controller">
        <div class="form-group">
            <label class="control-label col-xs-3" for="title"><fmt:message key="add.track.title"/>*:</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="title" name="title"
                       required data-parsley-length="[1,200]" data-parsley-required value="${title}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="artist"><fmt:message key="add.track.artist"/>*:</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="artist" name="artist"
                       required data-parsley-length="[1,200]" data-parsley-required value="${artist}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="genre"><fmt:message key="add.track.genre"/>:</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="genre" name="genre"
                       data-parsley-length="[1,45]"  value="${genre}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="price"><fmt:message key="add.track.price"/>*:</label>
            <div class="col-xs-9">
                <input type="number" class="form-control" id="price" name="price"
                       data-parsley-range="[0,99]" data-parsley-required value="${price}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="length"><fmt:message key="add.track.length"/>*:</label>
            <div class="col-xs-9">
                <input type="number" class="form-control" id="length" name="length"
                       data-parsley-range="[1,1000]" data-parsley-required value="${price}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="link"><fmt:message key="add.track.link"/>*:</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="link" name="link"
                       required data-parsley-length="[1,200]"  data-parsley-required  value="${genre}">
            </div>
        </div>
        <br/>
        <div class="form-group">
            <div class="col-xs-offset-3 col-xs-9">
                <button type="submit" class="btn btn-primary" name="command"
                        value="add_track"><fmt:message key="add.button.confirm"/></button>
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
<script src="../js/i18n/en.js"></script>
<script src="../js/i18n/ru.js"></script>
<script>
    $(document).ready(function () {
        $('#addform').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>
</body>
</html>

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
    <title><fmt:message key="changes.title"/></title>
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
</head>
<body>
<c:set var="page" value="path.page.changes" scope="session"/>
<%@ include file="../common/menu.jsp" %>
<c:if test="${not empty mistake}">
    <div class="alert alert-danger col-lg-4 col-lg-offset-4">${mistake}</div>
</c:if>
<div class="form col-lg-4 col-lg-offset-4">
    <h2 class="text-center"><fmt:message key="changes.title"/></h2>
    <c:choose>
        <c:when test="${'price_change' eq change}">
            <form class="form-horizontal" id="form" method="post"
                  action="${pageContext.request.contextPath}/controller">
                <div class="form-group">
                    <label class="control-label col-xs-3" for="price"><fmt:message key="changes.price"/>:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="price" name="price"
                               value="${price}" data-parsley-required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <button type="submit" class="btn btn-primary" name="command"
                                value="${change}"><fmt:message key="changes.button.save"/></button>
                        <a href="${pageContext.request.contextPath}/controller?command=track_info&track=${trackId}">
                            <button type="button" class="btn btn-default">
                                <fmt:message key="changes.button.track"/></button>
                        </a>
                    </div>
                </div>
            </form>
        </c:when>
        <c:when test="${'length_change' eq change}">
            <form class="form-horizontal" id="form" method="post"
                  action="${pageContext.request.contextPath}/controller">
                <div class="form-group">
                    <label class="control-label col-xs-3" for="length"><fmt:message key="changes.length"/>:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="length" name="length"
                               data-parsley-required data-parsley-range="[1,1000]">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <button type="submit" class="btn btn-primary" name="command"
                                value="${change}"><fmt:message key="changes.button.save"/></button>
                        <a href="${pageContext.request.contextPath}/controller?command=track_info&track=${trackId}">
                            <button type="button" class="btn btn-default">
                                <fmt:message key="changes.button.track"/></button>
                        </a>
                    </div>
                </div>
            </form>
        </c:when>
        <c:when test="${'title_change' eq change}">
            <form class="form-horizontal" id="form" method="post"
                  action="${pageContext.request.contextPath}/controller">
                <div class="form-group">
                    <label class="control-label col-xs-3" for="title"><fmt:message key="changes.title.change"/>:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="title" name="title"
                               data-parsley-required data-parsley-length="[1,200]">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <button type="submit" class="btn btn-primary" name="command"
                                value="${change}"><fmt:message key="changes.button.save"/></button>
                        <a href="${pageContext.request.contextPath}/controller?command=track_info&track=${trackId}">
                            <button type="button" class="btn btn-default">
                                <fmt:message key="changes.button.track"/></button>
                        </a>
                    </div>
                </div>
            </form>
        </c:when>
        <c:when test="${'artist_change' eq change}">
            <form class="form-horizontal" id="form" method="post"
                  action="${pageContext.request.contextPath}/controller">
                <div class="form-group">
                    <label class="control-label col-xs-3" for="artist"><fmt:message key="changes.artist"/>:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="artist" name="artist"
                               data-parsley-required data-parsley-length="[1,200]">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <button type="submit" class="btn btn-primary" name="command"
                                value="${change}"><fmt:message key="changes.button.save"/></button>
                        <a href="${pageContext.request.contextPath}/controller?command=track_info&track=${trackId}">
                            <button type="button" class="btn btn-default">
                                <fmt:message key="changes.button.track"/></button>
                        </a>
                    </div>
                </div>
            </form>
        </c:when>
        <c:when test="${'genre_change' eq change}">
            <form class="form-horizontal" id="form" method="post"
                  action="${pageContext.request.contextPath}/controller">
                <div class="form-group">
                    <label class="control-label col-xs-3" for="genre"><fmt:message key="changes.genre"/>:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="genre" name="genre"
                               data-parsley-length="[1,45]" data-parsley-length="[1,200]">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <button type="submit" class="btn btn-primary" name="command"
                                value="${change}"><fmt:message key="changes.button.save"/></button>
                        <a href="${pageContext.request.contextPath}/controller?command=track_info&track=${trackId}">
                            <button type="button" class="btn btn-default">
                                <fmt:message key="changes.button.track"/></button>
                        </a>
                    </div>
                </div>
            </form>
        </c:when>
        <c:otherwise>
            <form class="form-horizontal" id="changeform" method="post" name="login"
                  action="${pageContext.request.contextPath}/controller">
                <div class="form-group">
                    <c:if test="${'login_change' eq change}">
                        <label class="control-label col-xs-3" for="lastName"><fmt:message key="changes.login"/>:</label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="lastName" name="nickname"
                                   required data-parsley-length="[4,15]" data-parsley-required value="${nickname}">
                        </div>
                    </c:if>
                    <c:if test="${'email_change' eq change}">
                        <label class="control-label col-xs-3" for="email"><fmt:message key="changes.email"/>:</label>
                        <div class="col-xs-9">
                            <input type="email" class="form-control" id="email" name="email"
                                   data-parsley-required value="${email}">
                        </div>
                    </c:if>
                    <c:if test="${'card_change' eq change}">
                        <label class="control-label col-xs-3" for="card"><fmt:message key="changes.card"/>:</label>
                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="card" name="card" data-parsley-required
                                   data-parsley-range="[1000000000000,999999999999999999]" value="${card}">
                        </div>
                    </c:if>
                    <c:if test="${'password_change' eq change}">
                        <div class="form-group" style="padding-right: 8px; padding-left: 15px;">
                            <label class="control-label col-xs-3" for="passwordOld"><fmt:message
                                    key="changes.password.old"/>:</label>
                            <div class="col-xs-9">
                                <input type="password" class="form-control" id="passwordOld" name="passwordOld"
                                       required data-parsley-length="[4,20]" data-parsley-required
                                       value="${passwordOld}">
                            </div>
                        </div>
                        <div class="form-group" style="padding-right: 8px; padding-left: 15px;">
                            <label class="control-label col-xs-3" for="passwordNew"><fmt:message
                                    key="changes.password.new"/>:</label>
                            <div class="col-xs-9">
                                <input type="password" class="form-control" id="passwordNew" name="passwordNew"
                                       required data-parsley-length="[4,20]" data-parsley-required
                                       value="${passwordNew}">
                            </div>
                        </div>
                        <div class="form-group" style="padding-right: 8px; padding-left: 15px;">
                            <label class="control-label col-xs-3" for="passwordNewConf"><fmt:message
                                    key="changes.password.new.confirm"/>:</label>
                            <div class="col-xs-9">
                                <input type="password" class="form-control" id="passwordNewConf"
                                       name="passwordNewConfirm"
                                       data-parsley-equalto="#passwordNew" data-parsley-required
                                       value="${passwordNewConfirm}">
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${'money_change' eq change}">
                        <label class="control-label col-xs-3" for="money"><fmt:message key="changes.money"/>:</label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="money" name="money"
                                   data-parsley-required value="${money}">
                        </div>
                    </c:if>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <button type="submit" class="btn btn-primary" name="command"
                                value="${change}"><fmt:message key="changes.button.save"/></button>
                        <a href="${pageContext.request.contextPath}/jsp/client/account.jsp">
                            <button type="button" class="btn btn-default">
                                <fmt:message key="changes.button.back"/></button>
                        </a>
                    </div>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="../common/footer.jsp" %>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../../js/jquery-3.1.1.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/parsley.min.js"></script>
<script src="../../js/i18n/ru.js"></script>
<script src="../../js/i18n/en.js"></script>
<script>
    $(document).ready(function () {
        $('#changeform').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
    $(document).ready(function () {
        $('#form').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>
</body>

</html>


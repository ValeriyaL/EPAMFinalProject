<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ctg" uri="customtags"%><html>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.content" />
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><fmt:message key="tracks.alf.title"/></title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <link href="../css/main.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../js/jquery-3.1.1.min.js"></script>
</head>
<body>
<c:set var="page" value="path.page.tracksAZ" scope="session"/>
<%@ include file="menu.jsp"%>
<div class="container">
    <div class="row">
        <div class="col-xs-10 col-xs-offset-1">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <div class="panel-title">
                        <div class="row">
                            <div class="col-xs-6">
                                <h5><span class="glyphicon glyphicon-list"></span> <fmt:message key="tracks.order.ordered"/></h5>
                            </div>
                            <div class="col-xs-6">
                                <button type="button" class="btn btn-primary btn-sm btn-block">
                                    <span class="glyphicon glyphicon-refresh"></span> <fmt:message key="tracks.order.update"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <c:forEach var="tempTrack" items="${allTracks}" >
                        <div class="row">
                            <div class="col-xs-2"><img class="img-responsive" src="../images/iTunes-11-icon.jpg">
                            </div>
                            <div class="col-xs-4">
                                <h4 class="product-name"><strong><c:out value="${tempTrack.title}"/></strong></h4><h4><small><c:out value="${tempTrack.artist}"/></small></h4><h4><small><a href="#"><fmt:message key="tracks.order.more"/>...</a></small></h4>
                            </div>
                            <div class="col-xs-6">
                                <div class="col-xs-6 text-right">
                                    <h5><strong><fmt:message key="tracks.order.price"/>: <c:out value="${tempTrack.price}"/> BYN </strong></h5>
                                    <h5><strong><fmt:message key="tracks.order.length"/>: <c:out value="${tempTrack.length}"/> </strong></h5>
                                </div>
                                <div class="col-xs-2 right">
                                    <c:if test="${empty isLogin}">
                                        <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="btn btn-default btn-lg ind"><span class="glyphicon glyphicon-shopping-cart"></span> <fmt:message key="tracks.order.buy"/></a>
                                    </c:if>
                                    <ctg:isLogined>
                                        <a href="#" class="btn btn-default btn-lg ind" style="margin: 2px"><span class="glyphicon glyphicon-shopping-cart"></span> <fmt:message key="tracks.order.buy"/></a>
                                    </ctg:isLogined>
                                    <ctg:adminTag role="${role}">
                                        <a href="#" class="btn btn-primary btn-sm ind" style="margin: 2px"><span class="glyphicon glyphicon-pencil"></span> <fmt:message key="tracks.order.change"/></a>
                                        <a href="#" class="btn btn-primary btn-sm ind" style="margin: 2px"><span class="glyphicon glyphicon-trash"></span> <fmt:message key="tracks.order.delete"/></a>
                                    </ctg:adminTag>
                                </div>
                            </div>
                        </div>
                        <hr>
                    </c:forEach>
                    <hr>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
</body>
</html>

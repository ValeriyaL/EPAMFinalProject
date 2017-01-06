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
<c:set var="page" value="path.page.orders" scope="session"/>
<%@ include file="menu.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-xs-10 col-xs-offset-1">
            <div class="panel panel-info">
                <div class="panel-body">
                    <c:forEach var="tempTrack" items="${allTracks}">
                        <div class="row">
                            <div class="col-xs-2"><img class="img-responsive" src="../images/iTunes-11-icon.jpg">
                            </div>
                            <div class="col-xs-4">
                                <h4 class="product-name"><strong><c:out value="${tempTrack.title}"/></strong></h4>
                                <h4>
                                    <small><c:out value="${tempTrack.artist}"/></small>
                                </h4>
                                <h4>
                                    <small><c:out value="${tempTrack.genre}"/></small>
                                </h4>
                                <h4>
                                    <small><a
                                            href="${pageContext.request.contextPath}/controller?command=track_info&track=${tempTrack.id}"><fmt:message
                                            key="tracks.order.more"/>...</a></small>
                                </h4>
                            </div>
                            <div class="col-xs-6">
                                <div class="col-xs-6 text-right">
                                    <h5><strong><fmt:message key="tracks.order.length"/>: <c:out
                                            value="${tempTrack.lengthTranslated}"/> </strong></h5>
                                </div>
                                <div class="col-xs-2 right">
                                    <a href="${pageContext.request.contextPath}/controller?command=download&track=${tempTrack.id}" class="btn btn-primary btn-lg" style="margin: 2px"><span
                                            class="glyphicon glyphicon-shopping-cart"></span> <fmt:message
                                            key="tracks.order.download"/></a>
                                </div>
                            </div>
                        </div>
                        <hr>
                    </c:forEach>
                    <c:if test="${isPagination eq true}">
                        <ul class="pagination">
                            <c:forEach var="i" begin="1" end="${numOfPages}">
                                <c:choose>
                                    <c:when test="${pageNumber eq i}">
                                        <li class="active"><a
                                                href="${pageContext.request.contextPath}/controller?command=switch_page&pageNumber=${i}">${i}</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                            <a href="${pageContext.request.contextPath}/controller?command=switch_page&pageNumber=${i}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </c:if>
                    <hr>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
</body>
</html>

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
    <title><fmt:message key="buy.title"/></title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <link href="../css/main.css" rel="stylesheet">
    <link href="../css/single.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../js/jquery-3.1.1.min.js"></script>
</head>
<body>
<c:set var="page" value="path.page.buy" scope="session"/>
<%@ include file="menu.jsp" %>
<c:if test="${not empty mistake}">
    <div class="alert alert-danger col-lg-4 col-lg-offset-4">${mistake}</div>
</c:if>
<div class="col-sm-10 col-sm-offset-1">
    <div class="container-fluid cont">
        <div class="content-wrapper">
            <div class="item-container padd">
                <div class="container">
                    <div class="col-md-3">
                        <div class="product col-md-3 service-image-left">
                            <img id="item-display" src="../images/iTunes.jpg" alt=""/>
                        </div>
                    </div>
                    <div class="col-md-7">
                        <div class="product-title">${trackInfo.title}</div>
                        <div class="product-desc">${trackInfo.artist}</div>
                        <div class="product-stock">${trackinfo.genre}</div>
                        <hr>
                        <div class="product-stock">${trackInfo.lengthTranslated}</div>
                        <h4><fmt:message key="message.price.bonus"/>:</h4>
                        <div class="product-desc">${bonusPrice} BYN</div>
                        <hr>
                        <div class="btn-group cart">
                            <a href="${pageContext.request.contextPath}/controller?command=buy_track&trackId=${trackInfo.id}" class="btn btn-primary btn-lg" style="margin: 2px"><span
                                    class="glyphicon glyphicon-shopping-cart"></span><fmt:message
                                    key="tracks.order.do"/></a>
                        </div>
                    </div>
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


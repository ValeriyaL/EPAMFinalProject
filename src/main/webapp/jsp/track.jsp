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
    <title><fmt:message key="main.title"/></title>
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
<c:set var="page" value="path.page.track" scope="session"/>
<%@ include file="menu.jsp" %>
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
                    <div class="product-price">${trackInfo.price} BYN</div>
                    <hr>
                    <div class="btn-group cart">
                        <ctg:notLogined>
                            <a href="${pageContext.request.contextPath}/jsp/login.jsp"
                               class="btn btn-default btn-lg ind"><span
                                    class="glyphicon glyphicon-shopping-cart"></span> <fmt:message
                                    key="tracks.order.buy"/></a>
                        </ctg:notLogined>
                        <ctg:isLogined>
                            <a href="#" class="btn btn-default btn-lg" style="margin: 2px"><span
                                    class="glyphicon glyphicon-shopping-cart"></span> <fmt:message
                                    key="tracks.order.buy"/></a>
                        </ctg:isLogined>
                    </div>
                    <ctg:adminTag role="${role}">
                        <div class="btn-group wishlist">
                            <a href="#" class="btn btn-primary btn-sm" style="margin: 2px"><span
                                    class="glyphicon glyphicon-pencil"></span> <fmt:message
                                    key="tracks.order.change"/></a>
                            <a href="#" class="btn btn-primary btn-sm" style="margin: 2px"><span
                                    class="glyphicon glyphicon-trash"></span> <fmt:message
                                    key="tracks.order.delete"/></a>
                        </div>
                    </ctg:adminTag>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="col-md-12 product-info">
                <ul id="myTab" class="nav nav-tabs nav_tabs">
                    <li class="active"><fmt:message key="track.comments"/></li>
                </ul>
                <section class="marg">
                    <c:forEach var="temp" items="${comments}">
                        <article class="row">
                            <div class="col-md-2 col-sm-2 hidden-xs">
                                <figure class="thumbnail">
                                    <img class="img-responsive"
                                         src="../images/default-avatar-c5d8ec086224cb6fc4e395f4ba3018c2.jpg"/>
                                    <figcaption class="text-center">${temp.user}</figcaption>
                                </figure>
                            </div>
                            <div class="col-md-10 col-sm-10">
                                <div class="panel panel-default arrow left">
                                    <div class="panel-body">
                                        <header class="text-left">
                                            <time class="comment-date"><i
                                                    class=" glyphicon glyphicon-time"></i> ${temp.date}
                                            </time>
                                        </header>
                                        <div class="comment-post">
                                            <p>
                                                    ${temp.text}
                                            </p>
                                        </div>
                                        <ctg:adminTag role="${role}">
                                            <a href="#" class="btn btn-default btn-sm"><span
                                                    class="glyphicon glyphicon-trash"></span> <fmt:message
                                                    key="track.comment.delete"/></a>
                                        </ctg:adminTag>
                                    </div>
                                </div>
                            </div>
                        </article>
                    </c:forEach>
                    <div class="well">
                        <h4><fmt:message key="track.comment.leave"/>:</h4>
                        <form role="form">
                            <div class="form-group">
                                <textarea class="form-control" rows="5"></textarea>
                            </div>
                            <a href="#" class="btn btn-primary btn-lg"> <fmt:message
                                    key="track.comment.send"/></a>
                        </form>
                    </div>
                </section>
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

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
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../js/jquery-3.1.1.min.js"></script>
</head>
<body>
<c:set var="page" value="path.page.main" scope="session"/>
<%@ include file="menu.jsp" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-lg-12 col-md-12">
            <p class="lead text-justify"><fmt:message key="main.text"/></p>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
            <h3><fmt:message key="main.ordered.tracks"/></h3>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th><fmt:message key="main.table.track"/></th>
                    <th><fmt:message key="main.table.artist"/></th>
                    <th><fmt:message key="main.table.genre"/></th>
                    <th><fmt:message key="main.table.length"/></th>
                    <th><fmt:message key="main.table.price"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="row" items="${tracks}">
                <tr>
                    <td><c:out value="${row.title}"/></td>
                    <td><c:out value="${row.artist}"/></td>
                    <td><c:out value="${row.genre}"/></td>
                    <td><c:out value="${row.length}"/></td>
                    <td><c:out value="${row.price}"/></td>
                </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-lg-3 col-lg-offset-1 col-md-4 col-sm-4 col-xs-12">
            <aside>
                <div class="list-group">
                    <h3><fmt:message key="main.popularGenres"/></h3>
                    <form id="popform" method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="genre" value="pop"/>
                        <input type="hidden" name="command" value="genre_order">
                        <a href="#" class="list-group-item list-group-item-action"
                           onclick="document.getElementById('popform').submit(); return false;"><fmt:message
                                key="main.genre.pop"/></a>
                    </form>
                    <form id="rapform" method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="genre" value="rap"/>
                        <input type="hidden" name="command" value="genre_order">
                        <a href="#" class="list-group-item list-group-item-action"
                           onclick="document.getElementById('rapform').submit(); return false;"><fmt:message
                                key="main.genre.rap"/></a>
                    </form>
                    <form id="rockform" method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="genre" value="rock"/>
                        <input type="hidden" name="command" value="genre_order">
                        <a href="#" class="list-group-item list-group-item-action"
                           onclick="document.getElementById('rockform').submit(); return false;"><fmt:message
                                key="main.genre.rock"/></a>
                    </form>
                    <form id="jazzform" method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="genre" value="jazz"/>
                        <input type="hidden" name="command" value="genre_order">
                        <a href="#" class="list-group-item list-group-item-action"
                           onclick="document.getElementById('jazzform').submit(); return false;"><fmt:message
                                key="main.genre.jazz"/></a>
                    </form>
                    <form id="discoform" method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="genre" value="disco"/>
                        <input type="hidden" name="command" value="genre_order">
                        <a href="#" class="list-group-item list-group-item-action"
                           onclick="document.getElementById('discoform').submit(); return false;"><fmt:message
                                key="main.genre.disco"/></a>
                    </form>
                </div>
            </aside>
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

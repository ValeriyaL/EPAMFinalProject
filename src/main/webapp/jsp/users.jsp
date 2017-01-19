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
    <title><fmt:message key="users.title"/></title>
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
<c:set var="page" value="path.page.users" scope="session"/>
<%@ include file="menu.jsp" %>
<div class="container">
    <div class="row col-md-8 col-md-offset-2 custyle">
        <table class="table table-striped custab">
            <thead>
            <tr>
                <th><fmt:message key="users.id"/></th>
                <th><fmt:message key="users.nickname"/></th>
                <th><fmt:message key="users.email"/></th>
                <th><fmt:message key="users.bonus"/></th>
                <th class="text-center"><fmt:message key="users.change"/></th>
            </tr>
            </thead>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.nickname}</td>
                    <td>${user.email}</td>
                    <td>${user.bonus}</td>
                    <td class="text-center"><a class='btn btn-info btn-xs'
                                               href="${pageContext.request.contextPath}/controller?command=user_info&userNickname=${user.nickname}"><span
                            class="glyphicon glyphicon-edit"></span><fmt:message key="account.button.change"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<%@ include file="footer.jsp" %>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
</body>
</html>

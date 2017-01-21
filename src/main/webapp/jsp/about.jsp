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
    <title><fmt:message key="about.title"/></title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <link href="../css/main.css" rel="stylesheet">
    <link href="../css/about.css" rel="stylesheet">
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
<c:set var="page" value="path.page.about" scope="session"/>
<%@ include file="common/menu.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-sm-6">
            <div class="well">
                <h3 class="h3changed"><i class="fa fa-home fa-1x ich"></i>
                    <fmt:message key="about.address"/>:</h3>
                <p class="header"><fmt:message key="about.address.street"/></p>
                <br/>
                <br/>
                <h3 class="h3changed"><i class="fa fa-envelope fa-1x ich"></i>
                    <fmt:message key="about.mail"/>:</h3>
                <p class="header">leraliudchyk96@gmail.com</p>
                <br/>
                <br/>
                <h3 class="h3changed"><i class="fa fa-user fa-1x ich"></i>
                    <fmt:message key="about.phone"/>:</h3>
                <p class="header">375(29)385-98-58</p>
                <br/>
                <br/>
                <h3 class="h3changed"><i class="fa fa-yelp fa-1x ich"></i>
                    <fmt:message key="about.twitter"/>:</h3>
                <p class="header"><a href="https://twitter.com/ValeriyaLudchik" target="_blank">twitter.com/ValeriyaLudchik</a>
                </p>
            </div>
        </div>
        <div class="col-sm-6">
            <iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d18829.4370546344!2d27.56954916931152!3d53.848561779781186!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46dbd1a1e612a92b%3A0x286a96a33f5ae02d!2z0YPQu9C40YbQsCDQr9C90LrQuCDQm9GD0YfQuNC90YsgNiwg0JzQuNC90YHQuiwg0JHQtdC70LDRgNGD0YHRjA!5e0!3m2!1sru!2sby!4v1483309484775"
                    width="565" height="430" frameborder="0" allowfullscreen></iframe>
        </div>
    </div>
</div>
<%@ include file="common/footer.jsp" %>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/jquery-3.1.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
</body>
</html>

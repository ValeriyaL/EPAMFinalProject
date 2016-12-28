<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.content" />
<html>
<head>
    <meta charset="UTF-8">
    <link href="../css/footerx.css" rel="stylesheet">
</head>
<body>
<footer>
    <div class="container row-fluid">
        <p class="text-muted col-lg-5 col-md-5 col-sm-8 col-xs-8">&copy 2016 Audiotracks.by.<fmt:message key="footer.rights"/></p>
        <a class="col-lg-1 col-lg-offset-5 col-md-1 col-md-offset-5 col-sm-2
            col-sm-offset-0 col-xs-2 col-xs-offset-0"
           href="${pageContext.request.contextPath}/controller?command=lang&language=ru_RU">
            <img src="../images/rus.jpg"></a>
        <a class="col-lg-1 col-md-1 col-sm-2 col-xs-2"
            href="${pageContext.request.contextPath}/controller?command=lang&language=en_US">
            <img src="../images/usa.jpg"></a>
        <input type="hidden" id="locale" value="${locale}">
    </div>
</footer>
</body>
</html>

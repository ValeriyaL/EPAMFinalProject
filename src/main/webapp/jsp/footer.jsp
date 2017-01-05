<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.content"/>
<html>
<head>
    <meta charset="UTF-8">
    <link href="../css/footerx.css" rel="stylesheet">
</head>
<body>
<footer>
    <div class="container row-fluid">
        <p class="text-muted col-lg-5 col-md-5 col-sm-8 col-xs-8">&copy 2016 Audiotracks.by.<fmt:message
                key="footer.rights"/></p>
        <div class="col-lg-1 col-lg-offset-5 col-md-1 col-md-offset-5 col-sm-2
            col-sm-offset-0 col-xs-2 col-xs-offset-0">
            <form id="ruform" method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="language" value="ru_RU"/>
                <input type="hidden" name="command" value="lang">
                <a href="#"
                   onclick="document.getElementById('ruform').submit(); return false;"><img src="../images/rus.jpg"></a>
            </form>
        </div>
        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
            <form id="enform" method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="language" value="en_US"/>
                <input type="hidden" name="command" value="lang">
                <a href="#"
                   onclick="document.getElementById('enform').submit(); return false;"><img src="../images/usa.jpg"></a>
            </form>
        </div>
        <input type="hidden" id="locale" value="${locale}"/>
    </div>
</footer>
</body>
</html>

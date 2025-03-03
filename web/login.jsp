<%-- 
    Document   : login
    Created on : Feb 12, 2025, 5:27:09 PM
    Author     : datho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/login.css" >
    </head>
    <body>
        <div class="login-card">
            <h2>Login</h2>
            <h3>Wekcome</h3>
            <form class="login-form" action="ProcessServlet" method="post" accept-charset="utf-8">
                <input type="text" name="txtname" placeholder="Enter the full name">
                <c:if test="${not empty requestScope.ERROR}">
                    <div class="error-message">
                        ${requestScope.ERROR}
                    </div>
                </c:if>
                <input type="submit" name="btnAction" value="Login">
            </form>
        </div>
    </body>
</html>

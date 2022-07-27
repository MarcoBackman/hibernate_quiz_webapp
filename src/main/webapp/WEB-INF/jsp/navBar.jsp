<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style><%@include file="../../resources/css/navbar.css"%></style>
    <title>Navbar</title>
</head>
<body>
    <nav class="nav_bar">
        <c:if test="${sessionScope.userObject != null}">
            <c:if test="${!sessionScope.userObject.suspended}">
                <div class="nav_option" id="home">
                    <a href="/home">
                        Home
                    </a>
                </div>
            </c:if>
        </c:if>
        <div class="nav_option" id="login_logout">
            <c:if test="${sessionScope.userObject != null}">
                <a href="/logout">
                    Logout
                </a>
            </c:if>
            <c:if test="${sessionScope.userObject == null}">
                <a href="/login">
                    Login
                </a>
            </c:if>
        </div>
        <div class="nav_option" id="register">
            <a href="/register">
                Register
            </a>
        </div>
        <c:if test="${sessionScope.userObject != null}">
            <c:if test="${(!sessionScope.userObject.suspended)
             && (!sessionScope.userObject.hasRated)}">
                <div class="nav_option" id="feedback">
                    <a href="/feedback">
                        Feedback
                    </a>
                </div>
            </c:if>
        </c:if>
        <div class="nav_option" id="contact us">
            <a href="/contact-us">
                Contact us
            </a>
        </div>
        <c:if test="${sessionScope.userObject != null}">
            <c:if test="${sessionScope.userObject.admin}">
                <div class="nav_option" id="feedback">
                    <a href="/admin-page">
                        Admin page
                    </a>
                </div>
            </c:if>
        </c:if>
    </nav>
</body>
</html>

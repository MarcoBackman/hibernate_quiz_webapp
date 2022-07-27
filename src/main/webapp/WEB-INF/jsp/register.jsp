<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#000000" />

    <title>Register</title>
    <style><%@include file="../../resources/css/login_logout.css"%></style>
</head>
<body>
    <%@include file="navBar.jsp"%>
    <div id="box_wrapper">
        <h1 id="header">Register</h1>
        <form method="post" action="register">
            <fieldset class="input_field">
                <legend>Please Type In:</legend>
                <label>
                    Account name:
                    <input type="text" name="user_id">
                </label> <br>
                <label>
                    password:
                    <input type="password" name="password">
                </label> <br>
                <label>
                    first name:
                    <input type="text" name="first_name">
                </label> <br>
                <label>
                    last name:
                    <input type="text" name="last_name">
                </label> <br>
                <label>
                    email:
                    <input type="text" name="email">
                </label> <br>
                <button type="submit">Register</button>
                <c:if test="${not empty message}">
                    Error: ${message}
                </c:if>
            </fieldset>
        </form>
    </div>
</body>
</html>

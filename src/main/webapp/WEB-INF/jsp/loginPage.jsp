<%--
  Created by IntelliJ IDEA.
  User: Tony
  Date: 7/15/2022
  Time: 2:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#000000" />

    <title>Login</title>
    <style><%@include file="../../resources/css/login_logout.css"%></style>
</head>
<body>
    <%@include file="navBar.jsp"%>
    <div id="box_wrapper">
        <h1 id="header">Login</h1>
        <form method="post" action="login">
            <fieldset class="input_field">
                <legend>Please Type In:</legend>
                <div id="input_section">
                    <label>
                        user id:
                        <input type="text" name="user_id">
                    </label>
                    <label>
                        password:
                        <input type="password" name="password">
                    </label>
                </div>
                <button type="submit">login</button>
            </fieldset>
        </form>
    </div>
</body>
</html>

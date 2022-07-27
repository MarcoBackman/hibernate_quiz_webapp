<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User profile</title>
</head>
<body>
    <h1>User profile for user ID: ${user.userId} </h1>
    <p>User Account Name: ${user.userName}</p>
    <p>User First Name: ${user.firstName}</p>
    <p>User Last Name: ${user.lastName}</p>
    <p>User Primary Address: </p>
    <p>User Primary Address: </p>
    <p>User Email: ${user.email}</p>
    <p>User Suspended: ${user.suspended}</p>
    <p>User has Rated: ${user.hasRated}</p>
    <c:if test="${user.suspended == true}">
        <form method="post" action="activate_user">
            <button>Activate</button>
        </form>
    </c:if>
    <c:if test="${user.suspended == false}">
        <form method="post" action="suspend_user">
            <button>Suspend</button>
        </form>
    </c:if>

    <a href="./admin-page">
        <button> Go back </button>
    </a>
</body>
</html>

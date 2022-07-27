<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#000000" />

    <style><%@include file="../../resources/css/main_page.css"%></style>
    <title>Main page</title>
</head>
<body>
    <!-- Navbar location -->
    <%@include file="navBar.jsp"%>
    <!-- Main section -->
    <h2 id="welcome_title">Welcome! ${user.firstName}</h2>
    <!-- Display quiz types -->
    <div id="quiz_wrapper">
        <c:forEach items="${quiz_types}" var="quiz_type" varStatus="loop">
            <div class="quiz_link">
                <form class="quiz_request_form" method="get" action="takeQuiz">
                    <label style="visibility: hidden">
                        <input type="text" name="quiz_type" value="${quiz_type.quizTypeNumber}" style="visibility: hidden">
                    </label>
                    <a class="link" href="/take-quiz/${quiz_type.quizTypeNumber}" type="submit">
                        <div class="quiz_type_block">
                            <h1 class="quiz_type_name">
                                    ${quiz_type.quizDescription}
                            </h1>
                            <p>Press to take a quiz</p>
                        </div>
                    </a>
                </form>
            </div>
        </c:forEach>
    </div>
</body>
</html>

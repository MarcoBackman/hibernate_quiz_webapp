<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Feedback</title>
</head>
<body>
    <%@include file="navBar.jsp"%>
    <h2>Feedback</h2>
    <form method="post" action="feedback">
        <div class="input_field">
            <fieldset>
                <span class="star-cb-group">
                    <c:forEach var="i" begin="0" end="5" step="1">
                        <input type="radio" id="rating-${i}" name="rating" value="${i}" />
                        <label for="rating-${i}"> ${i}</label>
                    </c:forEach>
                </span>
            </fieldset>
            <label>
                User feedback:
                <input type="text" name="feedback">
            </label> <br>
        </div>
        <button type="submit">submit</button>
    </form>
</body>
</html>

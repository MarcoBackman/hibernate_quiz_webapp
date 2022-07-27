<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
    <%@include file="navBar.jsp"%>
    <h1>Admin page</h1>
    <h3>User lists</h3>
    <p>Click to choose</p>
    <div class="user_list_box" style="display:block; overflow-y:auto;height: 180px">
        <c:forEach items="${users}" var="user" varStatus="loop">
            <form action="view_user">
                <a href="${pageContext.request.contextPath}/user-profile?user_info=${user.userId}">
                    User: ${user.userName}<br>
                </a>
            </form>
        </c:forEach>
    </div>

    <h3>User rates</h3>
    <div class="feedback_list_box" style="display:block; overflow-y:auto;height: 180px">
        <c:forEach items="${feedbacks}" var="feedback" varStatus="loop">
            <div class="feedback_info" style="display: flex">
                <p style="margin: 0 1em 0 0">Rate: ${feedback.rate}</p>
                <p style="margin: 0 1em 0 0">Date: ${feedback.date}</p>
                <p style="margin: 0 1em 0 0">Content: ${feedback.content}</p>
            </div>
        </c:forEach>
    </div>

    <h3>Edit question lists</h3>
    <a href="./question-editor">
        Open editor
    </a>

    <h3>User Ques Results</h3>
    <!-- Overview of all quiz results -->
    <div>
        <!-- For each result:
        Table/Header -Taken date, Category, User name, Number of questions, Score
            -Ordered by - taken date with most recent listed on top
            -Filter to only display certain category result
            -Filter to only display certain user result
            -Quiz detail page on click
         -->
        <table>
            <tr>
                <td class="row" style="margin: 0 2em 0 0;width: 200px">
                    Taken date
                </td>
                <td style="margin: 0 2em 0 0;width: 100px">
                    Category
                </td>
                <td style="margin: 0 2em 0 0;width: 100px">
                    User Number
                </td>
                <td style="margin: 0 2em 0 0;width: 100px">
                    Number of Questions
                </td>
                <td style="margin: 0 2em 0 0;width: 100px">
                    Score
                </td>
            </tr>
            <div class="filter">
                <div>
                    <form id="filter_by_user" method="post">
                        <label>
                            Filter Log By ID
                            <input type="text" name="user_id" value=""/>
                        </label>
                        <button formaction="/admin-page/by-user">Apply</button>
                    </form>
                </div>
                <div class="quiz_type_filter" style="display: flex">
                    Filter By Category
                    <form id="filter_by_type" method="post">
                        <select id="filterByQuizType" name="filterByQuizType">
                            <c:forEach items="${quizTypes}" var="quizType" varStatus="loop">
                                <option value="${quizType.quizTypeNumber}">
                                        ${quizType.quizDescription}
                                </option>
                            </c:forEach>
                        </select>
                        <button formaction="/admin-page/by-category">Apply</button>
                    </form>
                </div>
            </div>
            <%--
                quizLinkedMapByQuizLog: Key = QuizLog, Value = Quiz
                typeDescriptionLinkedMapByQuiz: Key = Quiz, Value = QuizType
                userLinkedMapByQuiz: Key = Quiz, Value = User
            --%>
            <c:forEach items="${quizLinkedMapByQuizLog}" var="quiz" varStatus="loop">
                <div class="feedback_info" style="display: flex">
                        <tr style="border: 1px solid black"
                            onMouseOver="this.style.backgroundColor ='#88cfcf'"
                            onMouseOut="this.style.backgroundColor ='#ffffff'">
                            <td style="border: 1px solid black">
                                    <%--  Taken date--%>
                                    ${quiz.key.timeStart}
                            </td>
                            <td style="border: 1px solid black">
                                    <%-- Category--%>
                                    ${typeLinkedMapByQuiz.get(quiz.value).quizDescription}
                            </td>
                            <td style="border: 1px solid black">
                                    <%--User Name--%>
                                <c:set var="user" value="${userLinkedMapByQuiz.get(quiz.value)}" scope="page"/>
                                ${user.firstName} ${user.lastName}
                            </td>
                            <td style="border: 1px solid black">
                                <%-- Number of Questions--%>
                                ${questionAmountByQuiz.get(quiz.value)}
                            </td>
                            <td style="border: 1px solid black">
                                <%--Score--%>
                                ${quiz.value.score}
                            </td>
                            <td>
                                <a href="/admin-page/log-detail/${quiz.key.quizID}">
                                    <button> See detail </button>
                                </a>
                            </td>
                        </tr>
                </div>
            </c:forEach>
        </table>

    </div>


</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Log detail page</title>
</head>
<body>
        <%@include file="navBar.jsp"%>

        <!-- For each result:
            - Display all questions with all options and user questions
         -->
        <h1>Quiz Detail</h1>
        <div class="question_choices">
            <c:set var="questionList" value="${optionSetByQuestion.keySet()}" scope="page"/>
            <c:forEach items="${questionList}" var="question" varStatus="loop">
                <c:set var="userQuestion" value="${userQuestions.get(question)}" scope="page"/>
                <div class="question" style="border: 1px solid black">

                    <h3>${question.questionContent}</h3>

                        <%--          Multiple Question      --%>
                    <c:if test="${!question.shortQuestion}">
                        <c:set var="userAnswerIndex" value="${userQuestion.selectedOptionID}" scope="page"/>
                        <c:forEach var="option"
                                   items="${optionSetByQuestion.get(question)}"
                                   varStatus="status">
                            <%-- User has matched the answer --%>
                            <c:if test="${option.answer && !status.index.equals(userQuestion.selectedOptionID)}">
                                <p style="background-color: #e17d7d">
                                        ${status.index + 1}: ${option.optionContent} - ${option.answer}
                                </p>
                            </c:if>
                            <c:if test="${!option.answer && status.index.equals(userQuestion.selectedOptionID)}">
                                <p style="background-color: #c58765">
                                        ${status.index + 1}: ${option.optionContent} - ${option.answer}
                                </p>
                            </c:if>
                            <c:if test="${option.answer && status.index.equals(userQuestion.selectedOptionID)}">
                                <p style="background-color: #87c249">
                                        ${status.index + 1}: ${option.optionContent} - ${option.answer}
                                </p>
                            </c:if>
                            <c:if test="${!option.answer && !status.index.equals(userQuestion.selectedOptionID)}">
                                <p>${status.index + 1}: ${option.optionContent} - ${option.answer} </p>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <%--          Short Question      --%>
                    <c:if test="${question.shortQuestion}">
                        <p>User answered: ${userQuestion.userAnswer}</p>
                        <p>The answer is: ${question.shortQuestionAnswer}</p>
                        <c:if test="${question.shortQuestionAnswer.eqals(userQuestion.userAnswer)}">
                            Correct!
                        </c:if>
                        <c:if test="${!question.shortQuestionAnswer.eqals(userQuestion.userAnswer)}">
                            Wrong!
                        </c:if>
                    </c:if>
                </div>
            </c:forEach>
        </div>
        <button onclick="history.back()">
            Go Back
        </button>
</body>
</html>

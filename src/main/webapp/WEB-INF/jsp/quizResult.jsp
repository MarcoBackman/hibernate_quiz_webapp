<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#000000" />

    <title>Quiz Result</title>
</head>
<body>
    <div class="tester_info_block">
        <!-- Quiz Name -->
        <p>Quiz Result ${quizName.toString()}</p>
        <!-- User Name -->
        <p>${user.userName}</p>
        <!-- Start/End Time -->
        <p>Started Time: ${startTime.toString()}</p>
        <p>Ended Time: ${endTime.toString()}</p>
    </div>

    <!-- Display all questions -->
    <div class="question_choices" style="height: 500px;overflow-y: auto">
        <!-- Mark it with green if correct, red if incorrect with an answer -->
        <!-- Required: User Answers by Questions, Question, Options(Correct and incorrect),-->
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
                    <c:if test="${ empty  userQuestion.userAnswer}">
                        <p>No response!</p>
                    </c:if>
                    <c:if test="${not empty  userQuestion.userAnswer}">
                        <c:if test="${question.shortQuestionAnswer.eqals(userQuestion.userAnswer)}">
                            <p>Correct!</p>
                        </c:if>
                        <c:if test="${!question.shortQuestionAnswer.eqals(userQuestion.userAnswer)}">
                            <p>Wrong!</p>
                        </c:if>
                    </c:if>
                </c:if>
            </div>
        </c:forEach>
    </div>
    <!-- Pass or fail -->
    <div class="test_result">
        <h3>${totalScore} / 100 </h3>
        <c:if test="${totalScore < 40}">
            <h4>You have failed!</h4>
        </c:if>
        <c:if test="${totalScore >= 40}">
            <h4>You have passed!</h4>
        </c:if>
        <!-- Retry Button -->
        <a href="/home"><button>Take another Quiz</button></a>
    </div>

</body>
</html>

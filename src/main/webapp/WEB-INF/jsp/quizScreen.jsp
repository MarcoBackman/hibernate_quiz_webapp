<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#000000" />

    <title>Quiz Page</title>
    <style><%@include file="../../resources/css/quiz_screen.css"%></style>
</head>
<body>
    <%@include file="navBar.jsp"%>

    <div id="question_wrapper">
        <c:if test="${question.shortQuestion}">
            <div class="question_block" <c:if test="${!userQuestion.userAnswer.equals('')}"> style="background-color: beige" </c:if>>
                <h3 class="question_header">${questionNumber + 1}. ${question.questionContent}</h3>
                <p> Enter your answer</p>
                <input type="text" id="short_answer_input" name="short_answer" value="" form="save">
            </div>
        </c:if>

        <c:if test="${!question.shortQuestion}">
            <div class="question_block" <c:if test="${userQuestion.selectedOptionID != -1}"> style="background-color: beige" </c:if>>
                <h3 class="question_header">${questionNumber + 1}. ${question.questionContent}</h3>
                <c:set var="currentQuestionIndex" value="${questionNumber}" scope="page"/>
                    <%-- Create option input fields index from 0 to 4 --%>
                <c:forEach items="${options}" var="option" varStatus="status">
                    <p class="option"><%-- selectedAnswer and status.index Value range 0 to 4 --%>
                        <input type="radio" name="is_answer" value="${status.index}" form="save"
                        <c:if test="${userQuestion.selectedOptionID == status.index}">
                               checked="checked" style="background-color: aquamarine"
                        </c:if>
                        >
                            ${status.index + 1}. ${option.optionContent}
                    </p>
                </c:forEach>
                <br>
            </div>
        </c:if>
    </div>
    <div id="button_section">
        <form id="save" method="post">
            <c:set var="questionRequest" value="${question}" scope="session"/>
            <input type="hidden" name="questionRequest" value="${question}" />
            <div id="page_number_btn_section">
                <c:forEach begin="0" end="${quizAmount - 1}" varStatus="loop">
                    <c:if test="${loop.index == 0}">
                        <button class="page_number_btn"
                                style="border-radius: 12px 0 0 12px"
                                formaction="/take-quiz/${topic}/${loop.index + 1}?questionNumber=${currentQuestionIndex}">
                    </c:if>
                    <c:if test="${loop.index == (quizAmount - 1)}">
                    <button class="page_number_btn"
                            style="border-radius: 0 12px 12px 0"
                            formaction="/take-quiz/${topic}/${loop.index + 1}?questionNumber=${currentQuestionIndex}">
                    </c:if>
                    <c:if test="${loop.index < (quizAmount - 1) && loop.index > 0}">
                    <button class="page_number_btn"
                            formaction="/take-quiz/${topic}/${loop.index + 1}?questionNumber=${currentQuestionIndex}">
                    </c:if>
                        ${loop.index + 1}
                    </button>
                </c:forEach>
            </div>
            <div id="previous_next_btn">
                <c:if test="${questionNumber > 0}">
                    <button id="previous_btn" formaction="/take-quiz/${topic}/${questionNumber}?questionNumber=${currentQuestionIndex}"
                            style="width: 100px; height: 50px">
                        Previous
                    </button>
                </c:if>
                <button id="submit_btn" class="btn" type="submit" formaction="/submit_quiz/${topic}?questionNumber=${currentQuestionIndex}">
                    Submit Quiz
                </button>
                <c:if test="${questionNumber < quizAmount - 1}">
                    <button id="next_btn" formaction="/take-quiz/${topic}/${questionNumber + 2}?questionNumber=${currentQuestionIndex}"
                            style="width: 100px; height: 50px">
                        Next
                    </button>
                </c:if>
            </div>
        </form>
    </div>
    <br>
</body>
</html>

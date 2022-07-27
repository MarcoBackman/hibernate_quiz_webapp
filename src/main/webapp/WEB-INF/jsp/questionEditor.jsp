<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Question editor</title>
    <style><%@include file="../../resources/css/question_editor.css"%></style>
</head>
<body>
    <script>
        function alarm() {
            alert("Click!");
        }
    </script>
    <!-- Navbar location -->
    <%@include file="navBar.jsp"%>

    <!-- question control-->
    <c:forEach items="${questionCollection}" var="quizType" varStatus="loop">
        <h3>${quizType.key} Question List</h3>
        <h4>${quizType.key} Multiple Question List</h4>
        <p>Question amount: ${questionCollection.get(quizType.key).size()}</p>
        <div class="quiz_type_block" style="display:block; padding: 2em; overflow-y:auto;width:600px;border: 2px solid black">
            <c:forEach items="${questionCollection.get(quizType.key)}"
                       var="question"
                       varStatus="loop">
                <div class="question_set">
                    <form method="post">

                        <c:if test="${!question.isActive()}">
                        <div class="single_question" style="display:block;border: 1px solid darkblue; padding-top:1em; background-color: #e17d7d">
                            </c:if>
                            <c:if test="${question.isActive()}">
                            <div class="single_question" style="display:block;border: 1px solid darkblue; padding-top:1em;">
                                </c:if>

                                <div style="display:flex;">
                                    <p style="margin-right: 2em">Question ID: ${question.questionID}</p>

                                    <!-- Invisible text field for selected id pass-->
                                    <input style="display:none;visibility: hidden"
                                           name="question_id"
                                           value="${question.questionID}">

                                    <!-- Actual content change-->
                                    Question:
                                    <input type="text"
                                           name="question_content"
                                           value="${question.questionContent}" style="height:25px;width:50%;"/>

                                </div>
                                <c:if test="${question.shortQuestion}">
                                    <p>Short Question Answer</p>
                                    <p>${question.shortQuestionAnswer}</p>
                                </c:if>
                                <c:if test="${!question.shortQuestion}">
                                    <p>Question Options</p>
                                    <c:forEach items="${optionList.get(question.questionID)}"
                                               var="option"
                                               varStatus="status">
                                        <p>${status.index + 1}: ${option.optionContent} - ${option.answer}</p>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <button type="submit" formaction="/submit_multiple_change">Submit Change</button>
                            <c:if test="${question.isActive()}">
                            <button type="submit" formaction="/disable_question">Disable Question</button>
                            </c:if>
                            <c:if test="${!question.isActive()}">
                            <button type="submit" formaction="/activate_question">Activate Question</button>
                            </c:if>
                            <button type="submit" formaction="/delete_question">Delete Question</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </c:forEach>
    <br>
    <h3>Add Multiple Question</h3>
    <div class="quiz_add_block">
        <form method="post" action="/add_multiple_question">
            <label>
                Question Type:
                <select id="question_type_selector" name="question_type_selector">
                    <c:forEach items="${quizTypes}" var="quizType" varStatus="loop">
                        <option value="${quizType.quizTypeNumber}">${quizType.quizDescription}</option>
                    </c:forEach>
                </select>
            </label>
            <br>
            <!-- select if question type is multiple or not -->
            <label style="text-decoration-line: line-through">Choose question form type:
                <select id="question_answer_type" name="question_form_type">
                    <option value="multiple" selected>Multiple Question</option>
                    <option value="short">Single Question</option>
                </select>
            </label>
            <%
                String selected_form_type = request.getParameter("question_form_type");
                pageContext.setAttribute("selected_form_type", selected_form_type);
            %>
            <div style="display:block;">
                <label>
                    Question Content:<br>
                    <textarea name="question_content"></textarea>
                </label><br>
                <label>
                    Question Option 1:
                    <input type="text" name="question_option1"/>
                    Answer-
                    <input type="radio" name="is_answer" value="1">
                </label><br>
                <label>
                    Question Option 2:
                    <input type="text" name="question_option2"/>
                    Answer-
                    <input type="radio" name="is_answer" value="2">
                </label><br>
                <label>
                    Question Option 3:
                    <input type="text" name="question_option3"/>
                    Answer-
                    <input type="radio" name="is_answer" value="3">
                </label><br>
                <label>
                    Question Option 4:
                    <input type="text" name="question_option4"/>
                    Answer-
                    <input type="radio" name="is_answer" value="4">
                </label><br>
                <label>
                    Question Option 5:
                    <input type="text" name="question_option5"/>
                    Answer-
                    <input type="radio" name="is_answer" value="5">
                </label><br>
                <button type="submit" >Add Question</button>
            </div>

        </form>
    </div>

    <h3>Add Short Question</h3>
    <div class="quiz_add_block">
        <form method="post" action="/add_short_question">
            <label>
                Question Type:
                <select id="question_type_selector_short" name="question_type_selector_short">
                    <c:forEach items="${quizTypes}" var="quizType" varStatus="loop">
                        <option value="${quizType.quizTypeNumber}">${quizType.quizDescription}</option>
                    </c:forEach>
                </select>
            </label>
            <br>
            <div style="display:block;">
                <label>
                    Question Content:<br>
                    <textarea name="question_content"></textarea>
                </label><br>
                <label>
                    Question Answer:
                    <input type="text" name="question_answer"/>
                </label><br>
                <button type="submit" >Add Question</button>
            </div>
        </form>
    </div>
</body>
</html>

package com.example.week3day13project.controller;

import com.example.week3day13project.domain.hibernate.*;
import com.example.week3day13project.service.QuestionService;
import com.example.week3day13project.service.QuizLogService;
import com.example.week3day13project.service.QuizService;
import com.example.week3day13project.service.UserQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@Slf4j
public class QuizTakeController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final QuizLogService quizLogService;
    private final UserQuestionService userQuestionService;
    private int numberOfQuizzes;
    private String startDateTime = "";

    private LinkedHashMap<Question, List<QuestionOption>> questionList = null;
    private LinkedHashMap<Question, UserQuestion> userResponse = null; //User response on given questions

    private final int FIRST_PAGE = 1;

    private final int NO_OPTION_SELECTED = -1;

    public QuizTakeController(QuizService quizService,
                              QuestionService questionService,
                              QuizLogService quizLogService,
                              UserQuestionService userQuestionService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.quizLogService = quizLogService;
        this.userQuestionService = userQuestionService;
    }

    private void resetQuiz() {
        questionList = null;
        userResponse = null;
        numberOfQuizzes = 0;
    }

    private void initializeQuiz(int topicId) {
        questionList = questionService.getTenMultipleQuestionsByType(topicId);
        questionService.getTwoShortQuestionsByType(topicId)
                .forEach(question -> questionList.put(question, null));

        if (questionList.size() < 10) {
            log.warn("Not enough questions, need at least 10 questions ready.");
            resetQuiz();
            throw new RuntimeException();
        } else {
            userResponse = new LinkedHashMap<>();
            numberOfQuizzes = questionList.size();
        }
    }

    //Initial mapping
    @RequestMapping(value = "/take-quiz/{topic}", method = RequestMethod.GET)
    public String setupQuizTopicPage(@PathVariable(value="topic") String topicIdStr) {
        int topicId = 0;
        try {
            topicId = Integer.parseInt(topicIdStr);
        } catch (NumberFormatException ex) {
            log.error("Topic ID is not a number: " + topicIdStr, ex);
            return "redirect:/home";
        }

        //Fetch new random quiz data from DB
        try {
            initializeQuiz(topicId);
        } catch (RuntimeException exception) {
            log.error("Initialization failed", exception);
            return "redirect:/home";
        }

        userResponse = new LinkedHashMap<>();
        questionList.forEach((question, questionOptions) -> {
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setQuestionID(question.getQuestionID());

            if (question.isShortQuestion()) {
                userQuestion.setUserAnswer("");
                userQuestion.setShortQuestion(true);
            } else {
                userQuestion.setSelectedOptionID(NO_OPTION_SELECTED);
                userQuestion.setShortQuestion(false);
            }
            userResponse.put(question, userQuestion);
        });
        startDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "redirect:" + topicId + "/" + FIRST_PAGE;
    }

    @GetMapping(value = "/take-quiz/{topic}/{page}")
    public String setUpQuizPage(Model model,
                                @PathVariable(value="topic") String topic,
                                @PathVariable(value="page") String questionNumber) {
        if (questionList == null || userResponse == null || numberOfQuizzes == 0) {
            log.warn("Invalid entry.");
            return "redirect:/home";
        }

        AtomicInteger questionIndex;
        try {
            questionIndex =  new AtomicInteger(Integer.parseInt(questionNumber) - 1);
        } catch (NumberFormatException ex) {
            log.error("Topic ID is not a number: " + questionNumber, ex);
            return "redirect:/home";
        }
        Question question = (Question) questionList.keySet().toArray()[questionIndex.get()];
        model.addAttribute("topic", topic);
        model.addAttribute("question", question);
        model.addAttribute("options", question.getOptions());
        model.addAttribute("questionNumber", questionIndex);
        model.addAttribute("userQuestion", userResponse.get(question));
        model.addAttribute("quizAmount", numberOfQuizzes);
        return "quizScreen";
    }

    @PostMapping(value = "/take-quiz/{topic}/{page}")
    public String saveProgress(HttpServletRequest request,
                               Model model,
                               @PathVariable(value="topic") String topic,
                               @PathVariable(value="page") String questionNumber) {
        if (questionList == null || userResponse == null || numberOfQuizzes == 0) {
            log.warn("Invalid entry.");
            return "redirect:/home";
        }

        Object obj = request.getSession().getAttribute("questionRequest");
        Question question = (Question) obj;

        //Todo refactor - simplify logic
        if (request.getParameterValues("short_answer") != null) { //Short question
            Optional<String> value
                    = Arrays.stream(request.getParameterValues("short_answer")).findAny();
            if (value.isPresent()) { //Save short answer
                String answer = value.get().trim();
                userResponse.get(question).setUserAnswer(answer);
            } else { //User has not written down anything.
                userResponse.get(question).setUserAnswer("");
            }
        } else if (request.getParameterValues("is_answer") == null) { //Multiple question - user has not selected
            userResponse.get(question).setSelectedOptionID(-1);
        } else { //Multiple question selection
            Optional<String> value
                    = Arrays.stream(request.getParameterValues("is_answer")).findAny();
            if (value.isPresent()) {
                int index = Integer.parseInt(value.get());
                userResponse.get(question).setSelectedOptionID(index);
            } else {
                log.warn("User not selected");
                userResponse.get(question).setSelectedOptionID(-1);
            }
        }
        model.addAttribute("userQuestions", userResponse);
        return "redirect:/take-quiz/" + topic + "/" + questionNumber;
    }

    @PostMapping(value = "/submit_quiz/{topic}")
    public String submitQuiz(HttpServletRequest request,
                             Model model,
                             @PathVariable(value="topic") String topic) {

        Object obj = request.getSession().getAttribute("questionRequest");
        Question question = (Question) obj;

        //Check last user answer on submission
        HttpSession currentSession = request.getSession();
        User user = (User)currentSession.getAttribute("userObject");

        Optional<String> selectedOption = Optional.empty();
        Optional<String> shortAnswer = Optional.empty();

        if (request.getParameterValues("is_answer") != null) {
            selectedOption = Arrays.stream(request.getParameterValues("is_answer")).findAny();
        } else if (request.getParameterValues("short_answer") != null) {
            shortAnswer = Arrays.stream(request.getParameterValues("short_answer")).findAny();
        }

        if (shortAnswer.isPresent()) { //short answer
            userResponse.get(question).setUserAnswer(shortAnswer.get());
        } else if (selectedOption.isPresent()) { //option selected
            userResponse.get(question)
                    .setSelectedOptionID(Integer.parseInt(selectedOption.get()));
        } else { //none selected
            userResponse.get(question).setSelectedOptionID(-1);
            userResponse.get(question).setUserAnswer("");
        }


        //Measure end DateTime
        Date date = new Date(); //consider using java.time.Instant
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDateTime = sdf.format(date);

        //Grade short questions
        float totalScore = 0;
        List<Question> questionArray = new ArrayList<>(questionList.keySet());
        for (Question tempQuestion : questionArray) {
            UserQuestion userQuestion = userResponse.get(tempQuestion);
            //Short questions
            if (tempQuestion.isShortQuestion()) {
                if (userQuestion.getUserAnswer().equals(tempQuestion.getShortQuestionAnswer())) {
                    totalScore += (float) (100.0 / questionList.size());
                }
            } else { //Multiple questions
                List<QuestionOption> options = questionList.get(tempQuestion);
                int optionIndex = 0;
                for (QuestionOption option : options) {
                    if ((userQuestion.getSelectedOptionID() == optionIndex) && option.isAnswer()) {
                        totalScore += (float) (100.0 / questionList.size());
                        log.debug("Answer Selected!");
                        break;
                    }
                    optionIndex++;
                }
            }
        }

        //Get quiz name
        int quizTypeNumber = Integer.parseInt(topic);
        QuizType quizType = quizService.getQuizTypesByTypeNumber(quizTypeNumber);

        model.addAttribute("optionSetByQuestion", questionList);
        model.addAttribute("totalScore", (int)totalScore);
        model.addAttribute("userQuestions", userResponse);
        model.addAttribute("quizName", quizType.getQuizDescription());
        model.addAttribute("user", user);
        model.addAttribute("startTime", startDateTime);
        model.addAttribute("endTime", endDateTime);

        //create Quiz
        int quizID = quizService.createQuizObject(quizType.getQuizTypeNumber(), (int)totalScore);

        //Create QuizLog
        quizLogService.createQuizLog(
                user.getUserId(),
                startDateTime,
                endDateTime,
                quizID);

        //Update UserQuestion for all questions
        userResponse.forEach((question1, userQuestion) -> userQuestion.setQuizID(quizID));

        //Insert final User questions to DB
        userResponse.forEach((question1, userQuestion) -> userQuestionService.insertUserQuestion(userQuestion));
        resetQuiz();
        return "quizResult";
    }
}

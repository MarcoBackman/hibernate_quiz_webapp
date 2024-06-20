package com.example.week3day13project.controller;

import com.example.week3day13project.domain.hibernate.*;
import com.example.week3day13project.service.QuestionService;
import com.example.week3day13project.service.QuizLogService;
import com.example.week3day13project.service.QuizService;
import com.example.week3day13project.service.UserQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class QuizTakeController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final QuizLogService quizLogService;
    private final UserQuestionService userQuestionService;
    static LinkedHashMap<Question, List<QuestionOption>> optionSetByQuestion;
    static LinkedHashMap<Question, UserQuestion> userQuestions;
    static int numberOfQuizzes;
    private String startDateTime = "";

    public QuizTakeController(QuizService quizService,
                              QuestionService questionService,
                              QuizLogService quizLogService,
                              UserQuestionService userQuestionService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.quizLogService = quizLogService;
        this.userQuestionService = userQuestionService;
    }


    //Initial mapping
    @RequestMapping(value = "/take-quiz/{topic}", method = RequestMethod.GET)
    public String setupQuizTopicPage(@PathVariable(value="topic") String topicId) {

        //Get multiple question set
        optionSetByQuestion = questionService.getTenMultipleQuestionsByType(Integer.parseInt(topicId));

        //Get two short questions
        List<Question> shortQuestions = questionService.getTwoShortQuestionsByType(Integer.parseInt(topicId));

        //append only when short question is present
        if (shortQuestions.size() > 0) {
            shortQuestions.forEach(shortQuestion -> optionSetByQuestion.put(shortQuestion, null));
        }

        numberOfQuizzes = optionSetByQuestion.size();

        //Go back to home if the total question amount is smaller than 10
        if (optionSetByQuestion.size() < 10) {
            //Better if it can give alert
            return "redirect:mainPage";
        }

        //Initialize user answer sheet
        userQuestions = new LinkedHashMap<>();
        List<Question> questions = new ArrayList<>(optionSetByQuestion.keySet());
        for (Question question : questions) {
            UserQuestion userQuestion;
            if (question.isShortQuestion()) {
                userQuestion = new UserQuestion();
                userQuestion.setUserAnswer("");
                userQuestion.setShortQuestion(true);
            } else {
                userQuestion = new UserQuestion();
                userQuestion.setSelectedOptionID(-1);
                userQuestion.setShortQuestion(false);
            }
            userQuestion.setQuestionID(question.getQuestionID());
            userQuestions.put(question, userQuestion);
        }

        //record start time
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startDateTime = sdf.format(date);

        return "redirect:" + topicId + "/1";
    }

    @GetMapping(value = "/take-quiz/{topic}/{page}")
    public String setUpQuizPage(Model model,
                                @PathVariable(value="topic") String topic,
                                @PathVariable(value="page") String questionNumber) {

        int questionIndex = new Integer(questionNumber) - 1;
        Question question = (Question) optionSetByQuestion.keySet().toArray()[questionIndex];
        model.addAttribute("topic", topic);
        model.addAttribute("question", question);
        model.addAttribute("options", optionSetByQuestion.get(question));
        model.addAttribute("questionNumber", questionIndex);
        model.addAttribute("userQuestion", userQuestions.get(question));
        model.addAttribute("quizAmount", numberOfQuizzes);
        return "quizScreen";
    }

    @PostMapping(value = "/take-quiz/{topic}/{page}")
    public String saveProgress(HttpServletRequest request,
                               Model model,
                               @PathVariable(value="topic") String topic,
                               @PathVariable(value="page") String questionNumber) {
        Object obj = request.getSession().getAttribute("questionRequest");
        Question question = (Question) obj;
        //Short question
        if (request.getParameterValues("short_answer") != null) {
            Optional<String> value
                    = Arrays.stream(request.getParameterValues("short_answer")).findAny();
            if (value.isPresent()) { //Save short answer
                String answer = value.get();
                userQuestions.get(question).setUserAnswer(answer);
            } else { //User has not written down anything.
                userQuestions.get(question).setUserAnswer("");
            }
        } else if (request.getParameterValues("is_answer") == null) {
            //Multiple question - user hasn't select
            userQuestions.get(question).setSelectedOptionID(-1);
        } else { //Multiple question selection
            Optional<String> value
                    = Arrays.stream(request.getParameterValues("is_answer")).findAny();
            if (value.isPresent()) {
                int index = Integer.parseInt(value.get());
                userQuestions.get(question).setSelectedOptionID(index);
            } else {
                System.out.println("User not selected");
                userQuestions.get(question).setSelectedOptionID(-1);
            }
        }
        model.addAttribute("userQuestions", userQuestions);
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
            userQuestions.get(question).setUserAnswer(shortAnswer.get());
        } else if (selectedOption.isPresent()) { //option selected
            userQuestions.get(question)
                    .setSelectedOptionID(Integer.parseInt(selectedOption.get()));
        } else { //none selected
            userQuestions.get(question).setSelectedOptionID(-1);
            userQuestions.get(question).setUserAnswer("");
        }


        //Measure end DateTime
        Date date = new Date(); //consider using java.time.Instant
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDateTime = sdf.format(date);

        //Grade short questions
        float totalScore = 0;
        List<Question> questionArray = new ArrayList<>(optionSetByQuestion.keySet());
        for (Question tempQuestion : questionArray) {
            UserQuestion userQuestion = userQuestions.get(tempQuestion);
            //Short questions
            if (tempQuestion.isShortQuestion()) {
                if (userQuestion.getUserAnswer().equals(tempQuestion.getShortQuestionAnswer())) {
                    totalScore += (100.0 / optionSetByQuestion.size());
                }
            } else { //Multiple questions
                List<QuestionOption> options = optionSetByQuestion.get(tempQuestion);
                int optionIndex = 0;
                for (QuestionOption option : options) {
                    if ((userQuestion.getSelectedOptionID() == optionIndex) && option.isAnswer()) {
                        totalScore += (100.0 / optionSetByQuestion.size());
                        System.out.println("Answer Selected!");
                        break;
                    }
                    optionIndex++;
                }
            }
        }

        //Get quiz name
        int quizTypeNumber = Integer.parseInt(topic);
        QuizType quizType = quizService.getQuizTypesByTypeNumber(quizTypeNumber);

        model.addAttribute("optionSetByQuestion", optionSetByQuestion);
        model.addAttribute("totalScore", (int)totalScore);
        model.addAttribute("userQuestions", userQuestions);
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
        userQuestions.forEach((question1, userQuestion) -> userQuestion.setQuizID(quizID));

        //Insert final User questions to DB
        userQuestions.forEach((question1, userQuestion) -> userQuestionService.insertUserQuestion(userQuestion));

        return "quizResult";
    }
}

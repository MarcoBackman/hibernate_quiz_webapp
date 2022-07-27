package com.example.week3day13project.controller;

import com.example.week3day13project.domain.hibernate.Question;
import com.example.week3day13project.domain.hibernate.QuestionOption;
import com.example.week3day13project.domain.hibernate.QuizType;
import com.example.week3day13project.service.QuestionService;
import com.example.week3day13project.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionEditorController {

    private final QuizService quizService;
    private final QuestionService questionService;

    public QuestionEditorController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @RequestMapping(value = "/question-editor")
    public String getQuestionEditor(Model model) {
        //Load all quizzes by types
        List<QuizType> quizTypes = quizService.getAllQuizTypes();
        model.addAttribute("quizTypes", quizTypes);

        //K - QuizTypeDescription V - List<Question>
        Hashtable<String, List<Question>> nestedCollection = new Hashtable<>();

        for (QuizType quizType : quizTypes) {
            nestedCollection.put(quizType.getQuizDescription(),
                    questionService.getAllQuestionsByType(quizType.getQuizTypeNumber()));
        }

        //Load all options and pass as model
        Map<Integer, List<QuestionOption>> optionList = questionService.getAllOptionsByGroup();

        model.addAttribute("questionCollection", nestedCollection);
        model.addAttribute("optionList", optionList);

        return "questionEditor";
    }

    @PostMapping(value = "/add_multiple_question")
    public String addMultipleQuestion(@RequestParam("question_type_selector") String questionType,
                                      @RequestParam("question_content") String questionContent,
                                      @RequestParam("question_option1") String questionOption1,
                                      @RequestParam("question_option2") String questionOption2,
                                      @RequestParam("question_option3") String questionOption3,
                                      @RequestParam("question_option4") String questionOption4,
                                      @RequestParam("question_option5") String questionOption5,
                                      @RequestParam("is_answer") String answer) {

        //Add a full set of question
        List<QuestionOption> optionList = new ArrayList<>();

        //Question_id will be injected lately in the service class
        optionList.add(QuestionOption.builder()
                .optionContent(questionOption1).build());
        optionList.add(QuestionOption.builder()
                .optionContent(questionOption2).build());
        optionList.add(QuestionOption.builder()
                .optionContent(questionOption3).build());
        optionList.add(QuestionOption.builder()
                .optionContent(questionOption4).build());
        optionList.add(QuestionOption.builder()
                .optionContent(questionOption5).build());

        int answerIndex = Integer.parseInt(answer) - 1;
        optionList.get(answerIndex).setAnswer(true);

        questionService.addMultipleQuestionWithOptions(questionContent,
                Integer.parseInt(questionType),
                optionList);

        return "redirect:/question-editor";
    }

    @PostMapping(value = "/add_short_question")
    public String addShortQuestion(@RequestParam("question_type_selector_short") String questionType,
                                   @RequestParam("question_content") String questionContent,
                                   @RequestParam("question_answer") String questionAnswer) {


        questionService.addShortQuestion(questionContent,
                questionAnswer,
                Integer.parseInt(questionType));
        return "redirect:/question-editor";
    }

    /**
     * This will only change the question content, not options.
     */
    @PostMapping(value = "/submit_multiple_change")
    public String changeMultipleQuestion(@RequestParam("question_id") String questionID,
                                 @RequestParam("question_content") String questionContent) {


        questionService.changeMultipleQuestion(Integer.parseInt(questionID), questionContent);
        return "redirect:/question-editor";
    }

    /**
     * This will remove a question only
     * Need to call questionOptionDAO.deleteAllOption(id) too
     */
    @PostMapping(value = "/delete_question")
    public String deleteQuestion(@RequestParam("question_id") String questionID) {
        questionService.deleteMultipleQuestion(Integer.parseInt(questionID));
        return "redirect:/question-editor";
    }

    @PostMapping(value = "/disable_question")
    public String disableQuestion(@RequestParam("question_id") String questionID) {
        questionService.disableQuestion(Integer.parseInt(questionID));
        return "redirect:/question-editor";
    }

    @PostMapping(value = "/activate_question")
    public String activateQuestion(@RequestParam("question_id") String questionID) {
        questionService.activateQuestion(Integer.parseInt(questionID));
        return "redirect:/question-editor";
    }
}

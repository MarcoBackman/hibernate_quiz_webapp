package com.example.week3day13project.domain.jdbc;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserQuestion {
    private int userQuestionID;
    private int quizID;
    private int questionID;
    private String userAnswer;
    private int selected_option_id;
    private boolean short_question;
}

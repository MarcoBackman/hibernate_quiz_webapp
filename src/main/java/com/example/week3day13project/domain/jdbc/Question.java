package com.example.week3day13project.domain.jdbc;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Question {
    private int questionID;
    private String questionContent;
    private String shortQuestionAnswer;
    private boolean active;
    private boolean shortQuestion;
    private int quizType;
}

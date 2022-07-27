package com.example.week3day13project.domain.jdbc;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Quiz {
    private int quizID;
    private int quizType;
    private int score;
}

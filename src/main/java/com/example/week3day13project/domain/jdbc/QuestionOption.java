package com.example.week3day13project.domain.jdbc;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QuestionOption {
    private int optionID;
    private String optionContent;
    private boolean answer;
    private int questionID;
}

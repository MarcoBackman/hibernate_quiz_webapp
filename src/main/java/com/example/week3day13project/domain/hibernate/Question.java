package com.example.week3day13project.domain.hibernate;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Question")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "question_id")
    private int questionID;

    @Column(name = "question_content")
    private String questionContent;

    @Nullable
    @Column(name = "short_question_answer")
    private String shortQuestionAnswer;

    @Column(name = "is_active")
    private boolean active;


    @Column(name = "is_short_question")
    private boolean shortQuestion;

    @Column(name = "quiz_type")
    private int quizType;
}

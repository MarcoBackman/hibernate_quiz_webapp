package com.example.week3day13project.domain.hibernate;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="UserQuestion")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQuestion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_question_id")
    private int userQuestionID;

    @Column(name = "question_id")
    private int questionID;

    @Column(name = "user_answer")
    private String userAnswer;

    @Column(name = "selected_option_id")
    private int selectedOptionID;

    @Column(name = "quiz_id")
    private int quizID;

    @Column(name = "is_short_question")
    private boolean shortQuestion;
}

package com.example.week3day13project.domain.hibernate;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="QuestionOption")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "option_id")
    private int optionID;

    @Column(name = "option_content")
    private String optionContent;

    @Column(name = "is_answer")
    private boolean answer;

    @Column(name = "question_id")
    private int questionID;
}

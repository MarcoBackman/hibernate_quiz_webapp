package com.example.week3day13project.domain.hibernate;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="QuizType")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "quiz_type")
    private int quizTypeNumber;

    @Column(name = "type_detail")
    private String quizDescription;
}

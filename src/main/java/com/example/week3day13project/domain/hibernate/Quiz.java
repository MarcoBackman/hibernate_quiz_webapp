package com.example.week3day13project.domain.hibernate;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="Quiz")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "quiz_id")
    private int quizID;

    @Column(name = "quiz_type")
    private Integer quizTypeIndex;

    @Column(name = "score")
    private int score;
}

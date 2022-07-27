package com.example.week3day13project.domain.hibernate;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="QuizLog")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "log_id")
    private int logID;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "time_end")
    private String timeEnd;

    @Column(name = "quiz_id")
    private Integer quizID;
}

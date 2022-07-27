package com.example.week3day13project.domain.hibernate;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="Feedback")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    @Basic(optional = false)
    private Integer id;

    private float rate;

    private String date;

    private String content;
}

package com.example.week3day13project.domain.jdbc;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Feedback {
    private float rate;
    private Date date;
    private String content;
}

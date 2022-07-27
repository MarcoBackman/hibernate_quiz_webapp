package com.example.week3day13project.domain.jdbc;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private int userId;
    private String userName;
    private String userPW;
    private boolean isAdmin;
    private boolean isSuspended;
    private boolean hasRated;
    private String email;
    private String firstName;
    private String lastName;
}

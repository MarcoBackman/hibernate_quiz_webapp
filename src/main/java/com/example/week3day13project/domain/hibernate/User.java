package com.example.week3day13project.domain.hibernate;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="User")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_pw")
    private String userPW;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "is_suspended")
    private boolean isSuspended;

    @Column(name = "has_rated")
    private boolean hasRated;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPW='" + userPW + '\'' +
                ", isAdmin=" + isAdmin +
                ", isSuspended=" + isSuspended +
                ", hasRated=" + hasRated +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

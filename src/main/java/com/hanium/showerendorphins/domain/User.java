package com.hanium.showerendorphins.domain;

import com.hanium.showerendorphins.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private Integer code;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "joindate")
    @CreatedDate
    private LocalDateTime user_joinDate;

//    @OneToMany(mappedBy = "user")
//    private List<Shower> userShowerList = new ArrayList<>();

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "age")
    private int age;

    @Builder
    public User(String userId,  String username, LocalDateTime user_joinDate, Gender gender, int age) {
        this.userId = userId;
        this.username = username;
        this.user_joinDate = user_joinDate;
        this.gender = gender;
        this.age = age;
    }

    public User() {

    }

}

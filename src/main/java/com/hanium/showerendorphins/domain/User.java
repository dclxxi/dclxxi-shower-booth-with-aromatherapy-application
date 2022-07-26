package com.hanium.showerendorphins.domain;

import com.hanium.showerendorphins.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private Integer code;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pw")
    private String password;

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
    public User(Integer code, String userId, String password, String username, Gender gender, int age) {
        this.code = code;
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.age = age;
    }

    public User() {

    }

//    public void updateUserShowerList(Shower newShower){
//        userShowerList.add(newShower);
//    }
}

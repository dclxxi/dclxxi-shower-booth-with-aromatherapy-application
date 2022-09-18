package com.hanium.showerendorphins.dto;

import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.enums.Gender;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class UserDto {
    private Integer code;
    private String userId;
    private String username;
    private Gender gender;
    private int age;

    @Builder
    public UserDto(Integer code, String userId, String username, Gender gender, int age) {
        this.code = code;
        this.userId = userId;
        this.username = username;
        this.gender = gender;
        this.age = age;
    }

    public User toEntity() {
        return User.builder()
                .code(code)
                .userId(userId)
                .username(username)
                .gender(gender)
                .age(age)
                .build();
    }
}

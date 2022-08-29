package com.hanium.showerendorphins.userTest;

import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.enums.Gender;
import com.hanium.showerendorphins.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDomainTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void 유저객체_생성(){
        //given
        UserDto user = UserDto.builder()
                .userId("testuser")
                .username("테스트")
                .gender(Gender.MALE)
                .age(20)
                .build();

        //then
        User savedUser = userRepository.save(user.toEntity());

        //when
        Assertions.assertEquals(savedUser.getUserId(),"testuser");

    }

}

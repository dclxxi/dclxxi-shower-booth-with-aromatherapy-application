package com.hanium.showerendorphins;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.AromaDto;
import com.hanium.showerendorphins.dto.ShowerDto;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.enums.FeelingStatus;
import com.hanium.showerendorphins.enums.Gender;
import com.hanium.showerendorphins.enums.Note;
import com.hanium.showerendorphins.repository.AromaRepository;
import com.hanium.showerendorphins.repository.ShowerRepository;
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
@Rollback(value = false)
public class createTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowerRepository showerRepository;

    @Autowired
    AromaRepository aromaRepository;

    @Test
    public void 유저_회원가입(){
        //given
        UserDto user = UserDto.builder()
                .userId("testuser")
                .password("12345")
                .username("테스트")
                .gender(Gender.MALE)
                .age(20)
                .build();

        //then
        User savedUser = userRepository.save(user.toEntity());

        //when
        Assertions.assertEquals(savedUser.getUserId(),"testuser");

    }




    @Test
    public void 아로마정보_저장(){
        //given
        AromaDto aromaData = AromaDto.builder()
                .koName("라벤더")
                .enName("Lavender")
                .note(Note.MIDDLE)
                .feeling(FeelingStatus.HAPPY)
                .scent("우아함")
                .summary("아로마테라피에서 가장 폭넓게 쓰이는 오일. 피부에 직접 바를 수 있음")
                .caution("광과민성 반응(강한 햇빛에 노출될 시에 피부 자극 유발)에 주의")
                .imgURL("https://cdn.imweb.me/thumbnail/20170923/59c560f0b0783.jpg")
                .build();
        //사용자id
        //사용자 키, 기분, 체온 측정 데이터
        //추천 아로마오일
        //샤워만족도
        //샤워날짜

        //when
        Aroma savedData = aromaRepository.save(aromaData.toEntity());

        //then
        Assertions.assertEquals(savedData.getKoName(),"라벤더");
    }


    @Test
    public void 샤워정보_저장(){
        //given
        User user = userRepository.findById(1).get();
        Aroma aroma = aromaRepository.findByKoName("라벤더").get();

        ShowerDto userShowerData = ShowerDto.builder()
                .user(user)
                .height(165.5)
                .bodyTemperature(35.6)
                .feeling(FeelingStatus.HAPPY)
                .aroma(aroma)
                .rating(4.0)
                .build();

        //when
        Shower savedData = showerRepository.save(userShowerData.toEntity());

        //then
        Assertions.assertEquals(savedData.getAroma().getKoName(),"라벤더");

    }
}

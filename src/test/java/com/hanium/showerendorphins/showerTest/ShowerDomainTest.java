package com.hanium.showerendorphins.showerTest;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.AromaDto;
import com.hanium.showerendorphins.dto.ShowerDto;
import com.hanium.showerendorphins.enums.FeelingStatus;
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

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class ShowerDomainTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowerRepository showerRepository;

    @Autowired
    AromaRepository aromaRepository;

    @Test
    public void 샤워정보_저장() {
        //given
        User user = userRepository.findById(1).get();
        Aroma aroma = aromaRepository.findByKoName("라벤더").get();

        ShowerDto userShowerData1 = ShowerDto.builder()
                .user(user)
                .height(165.5)
                .bodyTemperature(35.6)
                .feeling(FeelingStatus.HAPPY)
                .aroma(aroma)
                .rating(3.0)
                .build();
        ShowerDto userShowerData2 = ShowerDto.builder()
                .user(user)
                .height(165.5)
                .bodyTemperature(35.6)
                .feeling(FeelingStatus.HAPPY)
                .aroma(aroma)
                .rating(4.0)
                .build();
        ShowerDto userShowerData3 = ShowerDto.builder()
                .user(user)
                .height(165.5)
                .bodyTemperature(35.6)
                .feeling(FeelingStatus.HAPPY)
                .aroma(aroma)
                .rating(5.0)
                .build();
        //when
        showerRepository.save(userShowerData1.toEntity());
        showerRepository.save(userShowerData2.toEntity());
        showerRepository.save(userShowerData3.toEntity());
        List<Shower> showerList = showerRepository.findAll();

        //then
        Assertions.assertEquals(showerList.size(), 3);

    }
}

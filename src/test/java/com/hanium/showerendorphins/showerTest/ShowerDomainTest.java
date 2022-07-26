package com.hanium.showerendorphins.showerTest;

import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.dto.ShowerDto;
import com.hanium.showerendorphins.enums.FeelingStatus;
import com.hanium.showerendorphins.repository.ShowerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class ShowerDomainTest {

    @Autowired
    ShowerRepository showerRepository;

    @Test
    public void 샤워정보를_저장하는_경우(){
        //given
        ShowerDto userShowerData = ShowerDto.builder()
                .height(165.5)
                .bodyTemperature(35.6)
                .feeling(FeelingStatus.HAPPY)
                .build();
        //사용자id
        //사용자 키, 기분, 체온 측정 데이터
        //추천 아로마오일
        //샤워만족도
        //샤워날짜

        //when
        Shower savedData = showerRepository.save(userShowerData.toEntity());

        //then
        System.out.println(savedData.getFeeling());
    }
}

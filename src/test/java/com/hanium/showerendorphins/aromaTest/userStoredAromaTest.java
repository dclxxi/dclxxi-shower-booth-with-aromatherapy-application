package com.hanium.showerendorphins.aromaTest;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.domain.UserStoredAroma;
import com.hanium.showerendorphins.dto.AromaDto;
import com.hanium.showerendorphins.dto.AromaListDto;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.dto.UserStoredAromaDto;
import com.hanium.showerendorphins.enums.FeelingStatus;
import com.hanium.showerendorphins.enums.Gender;
import com.hanium.showerendorphins.enums.Note;
import com.hanium.showerendorphins.repository.AromaRepository;
import com.hanium.showerendorphins.repository.UserRepository;
import com.hanium.showerendorphins.repository.UserStoredAromaRepository;
import com.hanium.showerendorphins.service.AromaService;
import com.hanium.showerendorphins.service.UserService;
import com.hanium.showerendorphins.service.UserStoredAromaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
//@Rollback(value = false)
@Transactional
public class userStoredAromaTest {

    @Autowired
    AromaRepository aromaRepository;

    @Autowired
    AromaService aromaService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserStoredAromaRepository userStoredAromaRepository;

    @Autowired
    UserStoredAromaService userStoredAromaService;


    @Test
    public void 사용자_보유_아로마_저장() {
        //given
        UserDto userDto = UserDto.builder()
                .userId("test")
                .password("12345")
                .username("테스트")
                .gender(Gender.MALE)
                .age(20)
                .build();

        String userId = userService.registerUser(userDto);
        User user = userRepository.findByUserId(userId).get();

        AromaDto aromaDto = AromaDto.builder()
                .koName("라벤더")
                .enName("Lavender")
                .note(Note.MIDDLE)
                .feeling(FeelingStatus.HAPPY)
                .scent("우아함")
                .summary("아로마테라피에서 가장 폭넓게 쓰이는 오일. 피부에 직접 바를 수 있음")
                .caution("광과민성 반응(강한 햇빛에 노출될 시에 피부 자극 유발)에 주의")
                .imgURL("https://cdn.imweb.me/thumbnail/20170923/59c560f0b0783.jpg")
                .build();

        Integer aromaId = aromaService.registerAroma(aromaDto);
        Aroma aroma = aromaRepository.findById(aromaId).get();

        UserStoredAromaDto userStoredAromaDto = UserStoredAromaDto.builder()
                .user(user)
                .aroma(aroma)
                .build();

        //when
        Integer userStoredAromaId = userStoredAromaService.saveUserStoredAroma(userStoredAromaDto);
        UserStoredAroma userStoredAroma = userStoredAromaRepository.findById(userStoredAromaId).get();

        //then
        assertEquals(userStoredAroma.getUser(), user);
        assertEquals(userStoredAroma.getAroma(), aroma);
    }

    @Test
    public void 사용자_보유_아로마_중복_예외() {
        //given
        UserDto userDto = UserDto.builder()
                .userId("test")
                .password("12345")
                .username("테스트")
                .gender(Gender.MALE)
                .age(20)
                .build();

        String userId = userService.registerUser(userDto);
        User user = userRepository.findByUserId(userId).get();

        AromaDto aromaDto = AromaDto.builder()
                .koName("라벤더")
                .enName("Lavender")
                .note(Note.MIDDLE)
                .feeling(FeelingStatus.HAPPY)
                .scent("우아함")
                .summary("아로마테라피에서 가장 폭넓게 쓰이는 오일. 피부에 직접 바를 수 있음")
                .caution("광과민성 반응(강한 햇빛에 노출될 시에 피부 자극 유발)에 주의")
                .imgURL("https://cdn.imweb.me/thumbnail/20170923/59c560f0b0783.jpg")
                .build();

        Integer aromaId = aromaService.registerAroma(aromaDto);
        Aroma aroma = aromaRepository.findById(aromaId).get();

        UserStoredAromaDto userStoredAromaDto = UserStoredAromaDto.builder()
                .user(user)
                .aroma(aroma)
                .build();

        UserStoredAromaDto userStoredAromaDto2 = UserStoredAromaDto.builder()
                .user(user)
                .aroma(aroma)
                .build();


        //when
        userStoredAromaService.saveUserStoredAroma(userStoredAromaDto);

        //then
        assertThrows(IllegalStateException.class,
                () -> userStoredAromaService.saveUserStoredAroma(userStoredAromaDto2));
    }

    @Test
    public void 사용자_보유_아로마_수정() {
        //given
        UserDto userDto = UserDto.builder()
                .userId("test")
                .password("12345")
                .username("테스트")
                .gender(Gender.MALE)
                .age(20)
                .build();

        String userId = userService.registerUser(userDto);
        User user = userRepository.findByUserId(userId).get();

        AromaDto aromaDto = AromaDto.builder()
                .koName("라벤더")
                .enName("Lavender")
                .note(Note.MIDDLE)
                .feeling(FeelingStatus.HAPPY)
                .scent("우아함")
                .summary("아로마테라피에서 가장 폭넓게 쓰이는 오일. 피부에 직접 바를 수 있음")
                .caution("광과민성 반응(강한 햇빛에 노출될 시에 피부 자극 유발)에 주의")
                .imgURL("https://cdn.imweb.me/thumbnail/20170923/59c560f0b0783.jpg")
                .build();

        Integer aromaId = aromaService.registerAroma(aromaDto);
        Aroma aroma = aromaRepository.findById(aromaId).get();

        AromaDto modifyAromaDto = AromaDto.builder()
                .koName("레몬")
                .build();

        AromaDto modifyAromaDto2 = AromaDto.builder()
                .koName("로즈마리")
                .build();

        Integer modifyAromaId = aromaService.registerAroma(modifyAromaDto);
        Aroma modifyAroma = aromaRepository.findById(modifyAromaId).get();

        Integer modifyAromaId2 = aromaService.registerAroma(modifyAromaDto2);
        Aroma modifyAroma2 = aromaRepository.findById(modifyAromaId2).get();


        UserStoredAromaDto userStoredAromaDto = UserStoredAromaDto.builder()
                .user(user)
                .aroma(aroma)
                .build();

        Integer userStoredAromaId = userStoredAromaService.saveUserStoredAroma(userStoredAromaDto);
        UserStoredAroma userStoredAroma = userStoredAromaRepository.findById(userStoredAromaId).get();

        UserStoredAromaDto modifyUserStoredAromaDto = UserStoredAromaDto.builder()
                .user(user)
                .aroma(modifyAroma)
                .build();

        UserStoredAromaDto modifyUserStoredAromaDto2 = UserStoredAromaDto.builder()
                .user(user)
                .aroma(modifyAroma2)
                .build();

        List<UserStoredAromaDto> userStoredAromaList = new ArrayList<>();
        userStoredAromaList.add(modifyUserStoredAromaDto);
        userStoredAromaList.add(modifyUserStoredAromaDto2);

        //when
        userStoredAromaService.modifyUserStoredAroma(userStoredAromaList);

        //then
        List<AromaListDto> aromaList = userStoredAromaService.findUserStoredAromaListByUserId(userId);

        assertEquals(2, aromaList.size());
        assertEquals(aromaList.get(0).getKoName(), modifyAroma.getKoName());
        assertEquals(aromaList.get(1).getKoName(), modifyAroma2.getKoName());
    }


    @Test
    public void 사용자_보유_아로마_삭제() {
        //given
        UserDto userDto = UserDto.builder()
                .userId("test")
                .password("12345")
                .username("테스트")
                .gender(Gender.MALE)
                .age(20)
                .build();

        String userId = userService.registerUser(userDto);
        User user = userRepository.findByUserId(userId).get();

        AromaDto aromaDto = AromaDto.builder()
                .koName("라벤더")
                .enName("Lavender")
                .note(Note.MIDDLE)
                .feeling(FeelingStatus.HAPPY)
                .scent("우아함")
                .summary("아로마테라피에서 가장 폭넓게 쓰이는 오일. 피부에 직접 바를 수 있음")
                .caution("광과민성 반응(강한 햇빛에 노출될 시에 피부 자극 유발)에 주의")
                .imgURL("https://cdn.imweb.me/thumbnail/20170923/59c560f0b0783.jpg")
                .build();

        Integer aromaId = aromaService.registerAroma(aromaDto);
        Aroma aroma = aromaRepository.findById(aromaId).get();

        UserStoredAromaDto userStoredAromaDto = UserStoredAromaDto.builder()
                .user(user)
                .aroma(aroma)
                .build();

        Integer userStoredAromaId = userStoredAromaService.saveUserStoredAroma(userStoredAromaDto);


        //when
        userStoredAromaService.deleteById(userStoredAromaId);

        //then
        Optional<UserStoredAroma> deleteUserStoredAroma = userStoredAromaRepository.findById(userStoredAromaId);
        assertFalse(deleteUserStoredAroma.isPresent());
    }

    @Test
    public void 사용자_보유_아로마_목록_조회() {
        //given
        UserDto userDto = UserDto.builder()
                .userId("test")
                .password("12345")
                .username("테스트")
                .gender(Gender.MALE)
                .age(20)
                .build();

        String userId = userService.registerUser(userDto);
        User user = userRepository.findByUserId(userId).get();


        IntStream.rangeClosed(1, 5).forEach(i -> {
            AromaDto aromaDto = AromaDto.builder()
                    .koName("라벤더" + i)
                    .enName("Lavender")
                    .note(Note.MIDDLE)
                    .feeling(FeelingStatus.HAPPY)
                    .scent("우아함")
                    .summary("아로마테라피에서 가장 폭넓게 쓰이는 오일. 피부에 직접 바를 수 있음")
                    .caution("광과민성 반응(강한 햇빛에 노출될 시에 피부 자극 유발)에 주의")
                    .imgURL("https://cdn.imweb.me/thumbnail/20170923/59c560f0b0783.jpg")
                    .build();

            AromaDto aromaDto2 = AromaDto.builder()
                    .koName("레몬" + i)
                    .enName("Lemon")
                    .note(Note.TOP)
                    .feeling(FeelingStatus.SAD)
                    .scent("상큼")
                    .summary("진정 작용이 있으며 기분을 밝게 바꿔 준다. 고혈압과 빈혈로 효과적이다면역체를 활성화시켜 면역력 높이고," +
                            "지혈효과가 있으며 인후통,기침,감기,유행성독감 등 발열을 동반한 때에 유효하다.")
                    .caution("민감한 피부에는 소량만 사용하고 광과민성 반응에 주의")
                    .imgURL("https://cdn.imweb.me/thumbnail/20170923/59c560f0b0783.jpg")
                    .build();

            Integer aromaId = aromaService.registerAroma(aromaDto);
            Aroma aroma = aromaRepository.findById(aromaId).get();

            aromaService.registerAroma(aromaDto2);

            UserStoredAromaDto userStoredAromaDto = UserStoredAromaDto.builder()
                    .user(user)
                    .aroma(aroma)
                    .build();

            userStoredAromaService.saveUserStoredAroma(userStoredAromaDto);
        });

        //when
        List<AromaListDto> allUserStoredAromaList = userStoredAromaService.findUserStoredAromaListByUserId(userId);

        //then
        assertEquals(5, allUserStoredAromaList.size());
        IntStream.rangeClosed(1, 5).forEach(i -> {
            assertEquals("라벤더" + i, allUserStoredAromaList.get(i - 1).getKoName());
        });
    }
}

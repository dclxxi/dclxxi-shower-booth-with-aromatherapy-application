package com.hanium.showerendorphins.aromaTest;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.dto.AromaDto;
import com.hanium.showerendorphins.enums.FeelingStatus;
import com.hanium.showerendorphins.enums.Note;
import com.hanium.showerendorphins.repository.AromaRepository;
import com.hanium.showerendorphins.service.AromaService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
//@Rollback(value = false)
@Transactional
public class AromaTest {

    @Autowired
    AromaRepository aromaRepository;

    @Autowired
    AromaService aromaService;

    @Test
    public void 아로마_저장() {
        //given
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

        //when
        Integer aromaId = aromaService.registerAroma(aromaDto);

        //then
        Aroma aroma = aromaRepository.findById(aromaId).get();
        List<Aroma> aromaList = aromaRepository.findAll();

        assertEquals(1, aromaList.size());
        assertEquals(aroma.getKoName(), aroma.getKoName());
    }

    @Test
    public void 아로마_중복_예외() {
        //given
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

        AromaDto aromaDto2 = AromaDto.builder()
                .koName("라벤더")
                .enName("Lavender")
                .note(Note.MIDDLE)
                .feeling(FeelingStatus.HAPPY)
                .scent("우아함")
                .summary("아로마테라피에서 가장 폭넓게 쓰이는 오일. 피부에 직접 바를 수 있음")
                .caution("광과민성 반응(강한 햇빛에 노출될 시에 피부 자극 유발)에 주의")
                .imgURL("https://cdn.imweb.me/thumbnail/20170923/59c560f0b0783.jpg")
                .build();

        //when
        aromaService.registerAroma(aromaDto);

        //then
        assertThrows(IllegalStateException.class,
                () -> aromaService.registerAroma(aromaDto2));
    }

    @Test
    public void 아로마_목록_조회() {
        //given
        IntStream.rangeClosed(1, 5).forEach(i -> {
            AromaDto aromaDto = AromaDto.builder()
                    .koName("라벤더" + i)
                    .build();

            aromaService.registerAroma(aromaDto);
        });

        //when
        List<Aroma> aromaList = aromaService.findAllAromaList();

        //then
        assertEquals(5, aromaList.size());
        IntStream.rangeClosed(1, 5).forEach(i -> {
            assertEquals("라벤더" + i, aromaList.get(i - 1).getKoName());
        });
    }


}

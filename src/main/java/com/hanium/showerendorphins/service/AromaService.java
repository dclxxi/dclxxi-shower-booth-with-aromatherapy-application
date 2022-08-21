package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.dto.AromaDto;
import com.hanium.showerendorphins.repository.AromaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AromaService {

    @Autowired
    private AromaRepository aromaRepository;

    public List<Aroma> findAllAromaList() {
        return aromaRepository.findAll();
    }

    @Transactional
    public Integer registerAroma(AromaDto aromaDto) {
        validateDuplicateAroma(aromaDto); // 중복 아로마 검증

        return aromaRepository.save(aromaDto.toEntity()).getId();
    }

    private void validateDuplicateAroma(AromaDto aromaDto) {
        Optional<Aroma> aroma = aromaRepository.findByKoName(aromaDto.getKoName());

        if (aroma.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아로마 오일입니다.");
        }
    }

}

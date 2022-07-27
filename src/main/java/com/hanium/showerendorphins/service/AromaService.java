package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.dto.GroupingRatingDto;
import com.hanium.showerendorphins.repository.AromaRepository;
import com.hanium.showerendorphins.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AromaService {

    @Autowired
    private AromaRepository aromaRepository;

    /*/Aroma/All_Aroma_List*/
    public List<Aroma> findAllAromaList(){
        return aromaRepository.findAll();
    }


}

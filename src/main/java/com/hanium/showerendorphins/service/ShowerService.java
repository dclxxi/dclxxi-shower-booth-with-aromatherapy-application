package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.dto.ShowerDto;
import com.hanium.showerendorphins.repository.ShowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ShowerService {

    @Autowired
    private ShowerRepository showerRepository;

    public Shower createShowerLog(ShowerDto showerlog){
        Shower savedData = showerRepository.save(showerlog.toEntity());
        return savedData;
    }
}

package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.dto.GroupingRatingDto;
import com.hanium.showerendorphins.repository.ShowerRepository;
import com.hanium.showerendorphins.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RatingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowerRepository showerRepository;

    public List<GroupingRatingDto> userRatingList(Integer userCode){
        List<GroupingRatingDto> ratingDtos = showerRepository.userRatingCount(userCode);
        return ratingDtos;
    }

    public List<GroupingRatingDto> allRatingList(){
        List<GroupingRatingDto> ratingDtos = showerRepository.allRatingCount();
        return ratingDtos;
    }
}

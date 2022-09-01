package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.GroupingRatingDto;
import com.hanium.showerendorphins.dto.ShowerDto;
import com.hanium.showerendorphins.exception.DoesNotExistException;
import com.hanium.showerendorphins.repository.ShowerRepository;
import com.hanium.showerendorphins.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShowerService {

    @Autowired
    private ShowerRepository showerRepository;

    @Autowired
    private UserRepository userRepository;

    public Integer createShowerLog(ShowerDto showerlog, Integer usercode){
        User user = userRepository.findById(usercode).orElseThrow(() -> new DoesNotExistException());
        showerlog.setUser(user);
        Shower savedData = showerRepository.save(showerlog.toEntity());
        return savedData.getId();
    }

    public List<Shower> findShowerLogList(Integer usercode) {
        return showerRepository.showerLogListByUserCode(usercode);
    }

    public List<GroupingRatingDto> userRatingCount(Integer usercode) {
        return showerRepository.userRatingCount(usercode);
    }

    public List<GroupingRatingDto> allRatingCount() {
        return showerRepository.allRatingCount();
    }
}

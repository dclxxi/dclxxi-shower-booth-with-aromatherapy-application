package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.exception.DoesNotExistException;
import com.hanium.showerendorphins.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public String saveUser(UserDto user) {
        //패스워드 인코딩
        User savedUser = userRepository.save(user.toEntity());
        return savedUser.getUserId();
    }

    public Integer signIn(String userEmail) {
        User user = userRepository.findByUserId(userEmail).orElseThrow(() -> new DoesNotExistException());
        return user.getCode();
    }
}

package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.enums.Gender;
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
        User savedUser = userRepository.save(user.toEntity());
        return savedUser.getUserId();
    }

    public User signIn(String userEmail) {
        return userRepository.findByUserId(userEmail).orElseThrow(() -> new DoesNotExistException());
    }
}

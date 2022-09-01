package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hanium.showerendorphins.exception.DoesNotExistException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    private void validateDuplicateUser(UserDto userDto) {
        Optional<User> user = userRepository.findByUserId(userDto.getUserId());

        if (user.isPresent()) {
            throw new IllegalStateException("이미 존재하는 사용자 아이디입니다.");
        }
    }

    @Transactional
    public String saveUser(UserDto user) {
        validateDuplicateUser(userDto);
        User savedUser = userRepository.save(user.toEntity());
        return savedUser.getUserId();
    }

    public User signIn(String userEmail) {
        return userRepository.findByUserId(userEmail).orElseThrow(() -> new DoesNotExistException());
    }
}

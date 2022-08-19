package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public String registerUser(UserDto userDto) {
        validateDuplicateUser(userDto);

        return userRepository.save(userDto.toEntity()).getUserId();
    }

    private void validateDuplicateUser(UserDto userDto) {
        Optional<User> user = userRepository.findByUserId(userDto.getUserId());

        if (user.isPresent()) {
            throw new IllegalStateException("이미 존재하는 사용자 아이디입니다.");
        }
    }
}

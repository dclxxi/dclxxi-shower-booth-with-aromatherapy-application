package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.domain.UserStoredAroma;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.dto.UserStoredAromaDto;
import com.hanium.showerendorphins.repository.UserStoredAromaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserStoredAromaService {

    @Autowired
    UserStoredAromaRepository userStoredAromaRepository;

    @Transactional
    public Integer registerUserStoredAroma(UserStoredAromaDto userStoredAromaDto) {
        validateDuplicateUserStoredAroma(userStoredAromaDto);

        return userStoredAromaRepository.save(userStoredAromaDto.toEntity()).getId();
    }

    private void validateDuplicateUserStoredAroma(UserStoredAromaDto userStoredAromaDto) {
        Optional<UserStoredAroma> userStoredAroma = userStoredAromaRepository.findByAroma(userStoredAromaDto.getAroma());

        if (userStoredAroma.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아로마 오일입니다.");
        }
    }

    public void deleteById(Integer userStoredAromaId) {
        userStoredAromaRepository.deleteById(userStoredAromaId);
    }

    public List<UserStoredAroma> findAllUserStoredAromaList() {
        return userStoredAromaRepository.findAll();
    }
}

package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.domain.UserStoredAroma;
import com.hanium.showerendorphins.dto.AromaListDto;
import com.hanium.showerendorphins.dto.UserStoredAromaDto;
import com.hanium.showerendorphins.repository.AromaRepository;
import com.hanium.showerendorphins.repository.UserRepository;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    AromaRepository aromaRepository;

/*    @Transactional
    public Integer registerUserStoredAroma(String userId, Integer aromaId) {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Aroma> aroma = aromaRepository.findById(aromaId);

        if (user.isPresent()) {
            throw new IllegalStateException("존재하지 않는 사용자입니다.");
        }

        if (aroma.isPresent()) {
            throw new IllegalStateException("존재하지 않는 아로마 오일입니다.");
        }

        UserStoredAromaDto userStoredAromaDto = UserStoredAromaDto.builder()
                .user(user.get())
                .aroma(aroma.get())
                .build();

        return saveUserStoredAroma(userStoredAromaDto);
    }*/

    @Transactional
    public void registerUserStoredAroma(List<UserStoredAromaDto> userStoredAromaDtoList) {
        for (UserStoredAromaDto userStoredAromaDto : userStoredAromaDtoList) {
            saveUserStoredAroma(userStoredAromaDto);
        }
    }

    @Transactional
    public Integer saveUserStoredAroma(UserStoredAromaDto userStoredAromaDto) {
        validateDuplicateUserStoredAroma(userStoredAromaDto);

        return userStoredAromaRepository.save(userStoredAromaDto.toEntity()).getId();
    }

    private void validateDuplicateUserStoredAroma(UserStoredAromaDto userStoredAromaDto) {
        Optional<UserStoredAroma> userStoredAroma = userStoredAromaRepository.findByAroma(userStoredAromaDto.getAroma());

        if (userStoredAroma.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아로마 오일입니다.");
        }
    }

    @Transactional
    public void modifyUserStoredAroma(List<UserStoredAromaDto> userStoredAromaDtoList) {
        String userId = userStoredAromaDtoList.get(0).getUser().getUserId();

        deleteByUserId(userId);
        registerUserStoredAroma(userStoredAromaDtoList);
    }

    @Transactional
    public void deleteByUserId(String userId) {
        userStoredAromaRepository.deleteByUserId(userId);
    }

    @Transactional
    public void deleteById(Integer userStoredAromaId) {
        userStoredAromaRepository.deleteById(userStoredAromaId);
    }

    public List<AromaListDto> findUserStoredAromaListByUserId(String userId) {
        return userStoredAromaRepository.findUserStoredAromaListByUserId(userId);
    }

}

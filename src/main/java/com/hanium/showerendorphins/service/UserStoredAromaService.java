package com.hanium.showerendorphins.service;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.AromaListDto;
import com.hanium.showerendorphins.dto.UserStoredAromaDto;
import com.hanium.showerendorphins.dto.UserStoredAromaListDto;
import com.hanium.showerendorphins.dto.UserStoredAromaModifyDto;
import com.hanium.showerendorphins.repository.AromaRepository;
import com.hanium.showerendorphins.repository.UserRepository;
import com.hanium.showerendorphins.repository.UserStoredAromaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        Optional<Integer> userStoredAromaId = userStoredAromaRepository.findIdByAromaAndUser(userStoredAromaDto.getAroma(), userStoredAromaDto.getUser());

        if (userStoredAromaId.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아로마 오일입니다.");
        }
    }

    @Transactional
    public void modifyUserStoredAroma(UserStoredAromaModifyDto userStoredAromaModifyDto) {
        List<UserStoredAromaDto> userStoredAromaDtoList = setUserStoredAromaDtoList(userStoredAromaModifyDto);
        registerUserStoredAroma(userStoredAromaDtoList);
    }

    public List<UserStoredAromaDto> setUserStoredAromaDtoList(UserStoredAromaModifyDto userStoredAromaModifyDto) {
        List<UserStoredAromaDto> userStoredAromaDtoList = new ArrayList<>();

        Optional<User> user = userRepository.findByUserId(userStoredAromaModifyDto.getUserId());
        userStoredAromaRepository.deleteByUserCode(user.get().getCode());

        if (user.isPresent()) {
            for (Integer aromaId : userStoredAromaModifyDto.getAromaId()) {
                Optional<Aroma> aroma = aromaRepository.findById(aromaId);

                if (aroma.isPresent()) {
                    UserStoredAromaDto userStoredAromaDto = UserStoredAromaDto.builder()
                            .user(user.get())
                            .aroma(aroma.get())
                            .build();

                    userStoredAromaDtoList.add(userStoredAromaDto);
                }
            }
        }

        return userStoredAromaDtoList;
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

    public List<UserStoredAromaListDto> findAllUserStoredAromaList(String userId) {
        List<UserStoredAromaListDto> userStoredAromaList = aromaRepository.findAllAromaModifyList();
        List<Integer> userStoredAromaIdList = findUserStoredAromaIdByUserId(userId);

        for (UserStoredAromaListDto userStoredAromaListDto : userStoredAromaList) {
            if (userStoredAromaIdList.contains(userStoredAromaListDto.getAromaId())) {
                userStoredAromaListDto.setIsChecked(true);
            } else {
                userStoredAromaListDto.setIsChecked(false);
            }
        }
        return userStoredAromaList;
    }

    public List<Integer> findUserStoredAromaIdByUserId(String userId) {
        return userStoredAromaRepository.findUserStoredAromaIdByUserId(userId);
    }
}

package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.domain.UserStoredAroma;
import com.hanium.showerendorphins.dto.AromaListDto;
import com.hanium.showerendorphins.dto.UserStoredAromaListDto;
import com.hanium.showerendorphins.dto.UserStoredAromaRecommendationDto;
import com.hanium.showerendorphins.dto.UserStoredAromaSelectionDto;
import com.hanium.showerendorphins.enums.FeelingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserStoredAromaRepository extends JpaRepository<UserStoredAroma, Integer> {

    @Query("select u.id from UserStoredAroma u where u.aroma = :aroma and u.user = :user")
    Optional<Integer> findIdByAromaAndUser(@Param("aroma") Aroma aroma, @Param("user") User user);

    @Query("select new com.hanium.showerendorphins.dto.AromaListDto(u.aroma.id, u.aroma.koName, u.aroma.enName, u.aroma.note, u.aroma.scent, u.aroma.imgURL) " +
            "from UserStoredAroma u " +
            "where u.user.userId = :userId")
    List<AromaListDto> findUserStoredAromaListByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("delete from UserStoredAroma u where u.user.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);

    @Query("select new com.hanium.showerendorphins.dto.UserStoredAromaListDto(a.id, a.koName, a.enName, a.imgURL, false) from Aroma a")
//            "case u.user.userId when :userId then true else false end) "
    List<UserStoredAromaListDto> findAllUserStoredAromaList(@Param("userId") String userId);

    @Query("select u.aroma.id from UserStoredAroma u where u.user.userId = :userId")
    List<Integer> findUserStoredAromaIdByUserId(@Param("userId") String userId);

    @Query("select new com.hanium.showerendorphins.dto.UserStoredAromaRecommendationDto(u.aroma.id, u.aroma.koName, u.aroma.imgURL) from UserStoredAroma u where u.user.userId = :userId and u.aroma.feeling = :feeling")
    Optional<UserStoredAromaRecommendationDto> findUserStoredAromaRecommendationByUserIdAndFeeling(@Param("userId") String userId, @Param("feeling") FeelingStatus feeling);

    @Query("select new com.hanium.showerendorphins.dto.UserStoredAromaSelectionDto(u.aroma.id, u.aroma.koName, u.aroma.feeling, u.aroma.imgURL) from UserStoredAroma u where u.user.userId = :userId")
    List<UserStoredAromaSelectionDto> findUserStoredAromaSelectionByUserIdAndFeeling(@Param("userId") String userId);


    @Modifying
    @Transactional
    @Query("delete from UserStoredAroma u where u.user.code = :code")
    void deleteByUserCode(@Param("code") Integer code);
}

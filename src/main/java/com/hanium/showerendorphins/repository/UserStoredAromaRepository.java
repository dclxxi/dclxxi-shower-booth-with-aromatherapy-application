package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.domain.UserStoredAroma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserStoredAromaRepository extends JpaRepository<UserStoredAroma, Integer> {

    Optional<UserStoredAroma> findByAroma(Aroma aroma);

    @Query("select u.aroma from UserStoredAroma u where u.user.userId = :userId")
    List<Aroma> findByUserId(@Param("userId") String userId);

    @Query("select u.aroma from UserStoredAroma u where u.user.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);
}

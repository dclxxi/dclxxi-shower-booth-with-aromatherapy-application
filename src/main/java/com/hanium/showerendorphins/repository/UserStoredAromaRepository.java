package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.UserStoredAroma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStoredAromaRepository extends JpaRepository<UserStoredAroma, Integer> {

    Optional<UserStoredAroma> findByAroma(Aroma aroma);
}

package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Shower> findByUserId(String userId);
}
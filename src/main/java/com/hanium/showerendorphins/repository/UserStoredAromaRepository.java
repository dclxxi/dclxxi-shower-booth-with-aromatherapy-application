package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.domain.UserStoredAroma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoredAromaRepository extends JpaRepository<UserStoredAroma, Integer> {

}

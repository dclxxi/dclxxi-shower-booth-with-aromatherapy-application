package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

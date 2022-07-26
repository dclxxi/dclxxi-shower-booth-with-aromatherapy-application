package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.Shower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowerRepository extends JpaRepository<Shower, Integer> {
    Optional<Shower> findById(Integer id);

}

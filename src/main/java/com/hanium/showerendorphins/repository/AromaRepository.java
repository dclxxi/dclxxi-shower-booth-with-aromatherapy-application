package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.Aroma;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AromaRepository extends JpaRepository<Aroma, Integer> {
    Optional<Aroma> findByKoName(String koName);


}

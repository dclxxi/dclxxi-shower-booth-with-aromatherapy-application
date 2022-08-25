package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.Aroma;

import com.hanium.showerendorphins.dto.AromaDetailDto;
import com.hanium.showerendorphins.dto.AromaListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AromaRepository extends JpaRepository<Aroma, Integer> {
    Optional<Aroma> findByKoName(String koName);

    @Query("select new com.hanium.showerendorphins.dto.AromaListDto(a.id, a.koName, a.enName, a.note, a.scent, a.imgURL) from Aroma a")
    List<AromaListDto> findAllList();

    @Query("select new com.hanium.showerendorphins.dto.AromaDetailDto(a.feeling, a.scent, a.summary, a.caution) from Aroma a where a.id = :id")
    Optional<AromaDetailDto> findInfoDetailById(@Param("id") Integer id);
}

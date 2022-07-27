package com.hanium.showerendorphins.repository;

import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.dto.GroupingRatingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShowerRepository extends JpaRepository<Shower, Integer> {
    Optional<Shower> findById(Integer id);

    //SELECT RATING, COUNT(*) FROM SHOWER  WHERE USER_ID=1 GROUP BY RATING;
    @Query("select new com.hanium.showerendorphins.dto.GroupingRatingDto(s.rating, count(s))" +
            "from Shower s where s.user.code = :userCode group by s.rating")
    List<GroupingRatingDto> userRatingCount(@Param("userCode") Integer userCode);

    //SELECT RATING, COUNT(*) FROM SHOWER GROUP BY RATING;
    @Query("select new com.hanium.showerendorphins.dto.GroupingRatingDto(s.rating, count(s)) " +
            "from Shower s group by s.rating")
    List<GroupingRatingDto> allRatingCount();
}

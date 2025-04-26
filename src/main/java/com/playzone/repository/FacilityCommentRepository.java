package com.playzone.repository;

import com.playzone.model.FacilityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacilityCommentRepository extends JpaRepository<FacilityComment, Long> {

    List<FacilityComment> findAllBySportFacilityId(Long sportFacilityId);

    @Query("SELECT AVG(c.rating) FROM FacilityComment c WHERE c.sportFacility.id = :facilityId")
    Double findAverageRatingByFacilityId(Long facilityId);
    boolean existsByUserIdAndSportFacilityId(Long userId, Long facilityId);
}

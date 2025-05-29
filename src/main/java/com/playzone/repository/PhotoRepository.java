package com.playzone.repository;

import com.playzone.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllBySportFacilityId(Long sportFacilityId);
    Optional<Photo> findByFileKey(String fileKey);
}
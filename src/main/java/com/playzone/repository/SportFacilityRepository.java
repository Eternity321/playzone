package com.playzone.repository;

import com.playzone.model.SportFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportFacilityRepository extends JpaRepository<SportFacility, Integer> {
}

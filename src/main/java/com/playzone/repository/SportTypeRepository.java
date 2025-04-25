package com.playzone.repository;

import com.playzone.model.SportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SportTypeRepository extends JpaRepository<SportType, Integer> {
    Optional<SportType> findByName(String name);
}

package com.playzone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "photo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sport_facility_id", nullable = false)
    private SportFacility sportFacility;

    @Column(name = "file_key", nullable = false, unique = true)
    private String fileKey;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
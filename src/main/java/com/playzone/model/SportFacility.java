package com.playzone.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Сущность локкации")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sport_facility")
public class SportFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_facility_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(precision = 10)
    private Double latitude;

    @Column(precision = 10)
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "sport_type_id")
    private SportType sportType;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "sportFacility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;
}

package com.playzone.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность видов спорта")
@Entity
@Table(name = "sport_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sport_type_id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;
}

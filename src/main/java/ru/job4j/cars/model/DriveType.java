package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "drive_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DriveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
package ru.job4j.cars.model;


import lombok.*;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;

@Entity
@Table(name = "engines")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String name;
}
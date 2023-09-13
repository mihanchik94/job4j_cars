package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "cars")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;
    @Column(name = "car_year")
    private int year;
    private long vin;

    @ManyToOne
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "ENGINE_ID_FK"))
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "body_id", foreignKey = @ForeignKey(name = "BODY_ID_FK"))
    private Body body;

    @ManyToOne
    @JoinColumn(name = "gear_box_id", foreignKey = @ForeignKey(name = "GEAR_ID_FK"))
    private GearBox gearBox;

    @ManyToOne
    @JoinColumn(name = "fuel_type_id", foreignKey = @ForeignKey(name = "FUEL_ID_FK"))
    private FuelType fuelType;

    @ManyToOne
    @JoinColumn(name = "drive_type_id", foreignKey = @ForeignKey(name = "DRIVE_TYPE_ID_FK"))
    private DriveType driveType;

    @ManyToOne
    @JoinColumn(name = "color_id", foreignKey = @ForeignKey(name = "COLOR_ID_FK"))
    private Color color;
}
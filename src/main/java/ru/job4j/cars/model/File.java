package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String path;
    @Column(name = "post_id")
    private int postId;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public File(String name, String path, int postId) {
        this.name = name;
        this.path = path;
        this.postId = postId;
    }
}
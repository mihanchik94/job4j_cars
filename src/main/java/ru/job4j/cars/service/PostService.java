package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostService {
    void save(Post post, Set<FileDto> images);
    List<Post> findAll();
    List<Post> findPostsOfToday();
    List<Post> findPostsOnlyWithPicture();
    List<Post> findPostsByName(String carName);
    Optional<Post> findPostById(int id);
    boolean delete(int id);
}
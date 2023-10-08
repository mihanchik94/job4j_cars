package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    List<Post> findAll();
    List<Post> findPostsOfToday();
    List<Post> findPostsOnlyWithPicture();
    List<Post> findPostsByName(String carName);
    Optional<Post> findPostById(int id);
    boolean delete(int id);
}
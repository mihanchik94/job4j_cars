package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;

public interface PostRepository {
    void save(Post post);
    List<Post> findAll();
    List<Post> findPostsOfToday();
    List<Post> findPostsOnlyWithPicture();
    List<Post> findPostsByName(String carName);
    void delete(int id);
}
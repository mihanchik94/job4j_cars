package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private PostRepository postRepository;

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findPostsOfToday() {
        return postRepository.findPostsOfToday();
    }

    @Override
    public List<Post> findPostsOnlyWithPicture() {
        return postRepository.findPostsOnlyWithPicture();
    }

    @Override
    public List<Post> findPostsByName(String carName) {
        return postRepository.findPostsByName(carName);
    }

    @Override
    public void delete(int id) {
        postRepository.delete(id);
    }
}
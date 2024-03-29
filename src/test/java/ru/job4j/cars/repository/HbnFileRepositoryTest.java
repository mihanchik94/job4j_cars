package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.file.FileRepository;
import ru.job4j.cars.repository.file.HbnFileRepository;
import ru.job4j.cars.repository.post.HbnPostRepository;
import ru.job4j.cars.repository.post.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnFileRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static FileRepository fileRepository;
    private static PostRepository postRepository;

    @BeforeAll
    static void initRepositories() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        postRepository = new HbnPostRepository(new CrudRepository(sf));
        fileRepository = new HbnFileRepository(new CrudRepository(sf));
    }

    @AfterEach
    void deleteModels() {
        List<File> files = fileRepository.findAll();
        for (File file : files) {
            fileRepository.deleteById(file.getId());
        }

        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            postRepository.delete(post.getId());
        }
    }

    @Test
    void whenFindByIdThenGetFile() {
        File file = new File();

        Post post = new Post();
        Post savedPost = postRepository.save(post);

        file.setPostId(savedPost.getId());

        fileRepository.save(file);

        assertThat(fileRepository.findById(file.getId())).isEqualTo(Optional.of(file));
    }


    @Test
    void whenFindAllFilesThenGetList() {
        File file1 = new File();
        File file2 = new File();

        Post post = new Post();
        Post savedPost = postRepository.save(post);

        file1.setPostId(savedPost.getId());
        file2.setPostId(savedPost.getId());

        fileRepository.save(file1);
        fileRepository.save(file2);

        assertThat(fileRepository.findAll()).isEqualTo(List.of(file1, file2));
    }
}
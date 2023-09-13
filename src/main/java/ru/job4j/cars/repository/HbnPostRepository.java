package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HbnPostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public void save(Post post) {
        crudRepository.run(session -> session.persist(post));
    }

    @Override
    public List<Post> findAll() {
        return crudRepository.query("from Post", Post.class);
    }

    @Override
    public List<Post> findPostsOfToday() {
        return crudRepository.query("from Post where created >= :fDate", Post.class,
                Map.of("fDate", LocalDateTime.now().minusDays(1).withSecond(0)));
    }

    @Override
    public List<Post> findPostsOnlyWithPicture() {
        return crudRepository.query("from Post p where size(p.photos) > 0", Post.class);
    }

    @Override
    public List<Post> findPostsByName(String carBrand) {
        return crudRepository.query("from Post p join fetch p.car where p.car.brand = :fCarBrand", Post.class,
                Map.of("fCarBrand", carBrand));
    }

    @Override
    public void delete(int id) {
        crudRepository.run("delete from Post p where p.id = :fId",
                Map.of("fId", id));
    }
}
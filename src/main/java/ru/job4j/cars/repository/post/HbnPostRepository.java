package ru.job4j.cars.repository.post;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HbnPostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public Post save(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public List<Post> findAll() {
        return crudRepository.query("from Post", Post.class);
    }

    @Override
    public List<Post> findPostsByBrand(String carBrand) {
        return crudRepository.query("from Post p join fetch p.car where p.car.carBrand.name = :fCarBrand", Post.class,
                Map.of("fCarBrand", carBrand));
    }

    @Override
    public List<Post> findPostsByModel(String carModel) {
        return crudRepository.query("from Post p join fetch p.car where p.car.carModel.name = :fCarModel", Post.class,
                Map.of("fCarModel", carModel));
    }

    @Override
    public List<Post> findPostByPriceInterval(int priceFrom, int priceUntil) {
        return crudRepository.query("from Post p where p.price >= :fPriceFrom and p.price <= :fPriceUntil", Post.class,
                Map.of("fPriceFrom", priceFrom,
                        "fPriceUntil", priceUntil));
    }

    @Override
    public List<Post> findPostByMileageInterval(long mileageFrom, long mileageUntil) {
        return crudRepository.query("from Post p join fetch p.car where p.car.mileage >= :fMileageFrom and p.car.mileage <= :fMileageUntil", Post.class,
                Map.of("fMileageFrom", mileageFrom,
                        "fMileageUntil", mileageUntil
                ));
    }

    @Override
    public List<Post> findPostsOfUser(int userId) {
        return crudRepository.query("from Post p where p.userId = :fUserId", Post.class,
                Map.of("fUserId", userId));
    }

    @Override
    public Optional<Post> findPostById(int id) {
        return crudRepository.optional("from Post where id = :fId", Post.class,
                Map.of("fId", id));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.query("delete from Post p where p.id = :fId",
                Map.of("fId", id));
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.update(post));
    }

    @Override
    public boolean changePrice(int id, int price) {
        return crudRepository.query("update Post set price = :fPrice where id = :fId",
                Map.of("fPrice", price,
                        "fId", id));
    }
}
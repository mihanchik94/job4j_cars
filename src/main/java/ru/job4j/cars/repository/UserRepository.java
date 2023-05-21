package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sessionFactory;

    public User create(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    public void update(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void delete(int id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User where id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public List<User> findAllOrderById() {
        Session session = sessionFactory.openSession();
        List<User> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("from User u order by u.id", User.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    public Optional<User> findById(int userId) {
        Session session = sessionFactory.openSession();
        Optional<User> result = Optional.empty();
        try {
            session.beginTransaction();
           result = session.createQuery(
                    "from User u where u.id = :fId", User.class
            ).setParameter("fId", userId).uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    public List<User> findByLikeLogin(String key) {
        Session session = sessionFactory.openSession();
        List<User> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery(
                    "from User u where u.login like :uKey", User.class)
                    .setParameter("uKey", "%" + key + "%")
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.openSession();
        Optional<User> result = Optional.empty();
        try {
            session.beginTransaction();
            result = session.createQuery("from User u where u.login = :fLogin", User.class)
                    .setParameter("fLogin", login)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.BodyRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBodyService implements BodyService {
    private final BodyRepository bodyRepository;

    @Override
    public List<Body> findAll() {
        return bodyRepository.findAll();
    }

    @Override
    public void save(Body body) {
        bodyRepository.save(body);
    }

    @Override
    public void delete(int id) {
        bodyRepository.delete(id);
    }

    @Override
    public Optional<Body> findById(int id) {
        return bodyRepository.findById(id);
    }

    @Override
    public Optional<Body> findByName(String name) {
        return bodyRepository.findByName(name);
    }
}
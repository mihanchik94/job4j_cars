package ru.job4j.cars.service.color;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Color;
import ru.job4j.cars.repository.color.ColorRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleColorService implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public void save(Color color) {
        colorRepository.save(color);
    }

    @Override
    public void delete(int id) {
        colorRepository.delete(id);
    }

    @Override
    public Optional<Color> findById(int id) {
        return colorRepository.findById(id);
    }

    @Override
    public Optional<Color> findByName(String name) {
        return colorRepository.findByName(name);
    }
}
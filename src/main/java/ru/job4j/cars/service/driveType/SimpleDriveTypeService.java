package ru.job4j.cars.service.driveType;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.DriveType;
import ru.job4j.cars.repository.driveType.DriveTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleDriveTypeService implements DriveTypeService {
    private final DriveTypeRepository driveTypeRepository;

    @Override
    public List<DriveType> findAll() {
        return driveTypeRepository.findAll();
    }

    @Override
    public void save(DriveType driveType) {
        driveTypeRepository.save(driveType);
    }

    @Override
    public void delete(int id) {
        driveTypeRepository.delete(id);
    }

    @Override
    public Optional<DriveType> findById(int id) {
        return driveTypeRepository.findById(id);
    }

    @Override
    public Optional<DriveType> findByName(String name) {
        return driveTypeRepository.findByName(name);
    }
}
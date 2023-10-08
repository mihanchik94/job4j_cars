package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimpleFileService implements FileService {
    private final FileRepository fileRepository;
    private final String storageDirectory;

    public SimpleFileService(FileRepository fileRepository, @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
           throw new RuntimeException(e.getCause());
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File save(FileDto file, Post post) {
        String path = createNewFilePath(file.getName());
        writeFileBytes(path, file.getContent());
        File resultFile = new File(file.getName(), path);
        resultFile.setPostId(post.getId());
        fileRepository.save(resultFile);
        return resultFile;
    }



    @Override
    public Optional<FileDto> findById(int id) {
        Optional<File> optionalFile = fileRepository.findById(id);
        if (optionalFile.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFileAsBytes(optionalFile.get().getPath());
        return Optional.of(new FileDto(optionalFile.get().getName(), content));
    }


    @Override
    public void deleteById(int id) {
        Optional<File> optionalFile = fileRepository.findById(id);
        if (optionalFile.isPresent()) {
            deleteFile(optionalFile.get().getPath());
            fileRepository.deleteById(id);
        }
    }


    @Override
    public List<FileDto> findAll() {
        List<File> files = fileRepository.findAll();
        List<FileDto> result = new ArrayList<>();
        for (File file : files) {
            byte[] content = readFileAsBytes(file.getPath());
            result.add(new FileDto(file.getName(), content));
        }
        return result;
    }
}
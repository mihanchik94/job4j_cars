package ru.job4j.cars.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class FileDtoConverter {
    public static Set<FileDto> convert(Set<MultipartFile> files) {
        return files.stream().map(file -> {
            FileDto result = null;
            try {
                result = new FileDto(file.getOriginalFilename(), file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }).collect(Collectors.toSet());
    }
}
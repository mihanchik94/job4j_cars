package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.service.FileService;

import java.util.Optional;

@Controller
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<FileDto> optionalFile = fileService.findById(id);
        if (optionalFile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalFile.get().getContent());
    }
}
package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cars")
public class StartPageController {
    @GetMapping({"/", "/index"})
    public String getIndex() {
        return "index";
    }
}
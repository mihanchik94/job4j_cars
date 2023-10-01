package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.PostService;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/all";
    }

    @GetMapping("{id}")
    public String getById(@PathVariable int id, Model model){
        Optional<Post> optionalPost = postService.findPostById(id);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление с указанным id не найдено");
            return "redirect:/errors/404";
        }
        model.addAttribute("post", optionalPost.get());
        model.addAttribute("car", optionalPost.get().getCar());
        model.addAttribute("body", optionalPost.get().getCar().getBody());
        model.addAttribute("transmission", optionalPost.get().getCar().getGearBox());
        model.addAttribute("driveType", optionalPost.get().getCar().getDriveType());
        model.addAttribute("fuelType", optionalPost.get().getCar().getFuelType());
        model.addAttribute("engine", optionalPost.get().getCar().getEngine());
        model.addAttribute("color", optionalPost.get().getCar().getColor());
        return "posts/one";
    }
}
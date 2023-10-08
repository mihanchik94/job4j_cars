package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
@NoArgsConstructor
public class PostController {
    private PostService postService;
    @Autowired
    private BodyService bodyService;
    @Autowired
    private EngineService engineService;
    @Autowired
    private DriveTypeService driveTypeService;
    @Autowired
    private FuelTypeService fuelTypeService;
    @Autowired
    private GearBoxService gearBoxService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private CarService carService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

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
        model.addAttribute("gearBox", optionalPost.get().getCar().getGearBox());
        model.addAttribute("driveType", optionalPost.get().getCar().getDriveType());
        model.addAttribute("fuelType", optionalPost.get().getCar().getFuelType());
        model.addAttribute("engine", optionalPost.get().getCar().getEngine());
        model.addAttribute("color", optionalPost.get().getCar().getColor());
        return "posts/one";
    }

    @GetMapping("/addNewPost")
    public String getAddPostPage(Model model) {
        model.addAttribute("bodies", bodyService.findAll());
        model.addAttribute("gearBoxes", gearBoxService.findAll());
        model.addAttribute("driveTypes", driveTypeService.findAll());
        model.addAttribute("fuelTypes", fuelTypeService.findAll());
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("colors", colorService.findAll());
        return "posts/addNewPost";
    }

    @PostMapping("/addNewPost")
    public String addNewPost(@ModelAttribute Car car, @RequestParam String description, @RequestParam Integer price,
                             @RequestParam Set<MultipartFile> files, HttpServletRequest request) {
        carService.save(car);
        Post post = new Post();
        post.setCar(car);
        post.setDescription(description);
        post.setPrice(price);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        post.setUserId(user.getId());
        Set<FileDto> postFiles = files.stream().map(file -> {
            FileDto result = null;
            try {
                result = new FileDto(file.getOriginalFilename(), file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }).collect(Collectors.toSet());
        postService.save(post, postFiles);
        return "redirect:/posts/all";
    }
}
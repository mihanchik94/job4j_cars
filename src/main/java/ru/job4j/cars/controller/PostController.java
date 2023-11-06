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
import ru.job4j.cars.utils.FileDtoConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;

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

    @GetMapping("/{id}")
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
        Set<FileDto> postFiles = FileDtoConverter.convert(files);
        postService.save(post, postFiles);
        return "redirect:/posts/all";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable int id, Model model) {
        boolean isDeleted = postService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Объявление с указанным id не найдено");
            return "redirect:/errors/404";
        }
        return "redirect:/posts/all";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePostPage(@PathVariable int id, Model model) {
        Optional<Post> optionalPost = postService.findPostById(id);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление с указанным id не найдено");
            return "redirect:/errors/404";
        }
        model.addAttribute("bodies", bodyService.findAll());
        model.addAttribute("post", optionalPost.get());
        model.addAttribute("car", optionalPost.get().getCar());
        model.addAttribute("gearBoxes", gearBoxService.findAll());
        model.addAttribute("driveTypes", driveTypeService.findAll());
        model.addAttribute("fuelTypes", fuelTypeService.findAll());
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("colors", colorService.findAll());
        return "posts/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute Car car, Model model, @RequestParam Set<MultipartFile> files, HttpServletRequest request) {
        Optional<Post> optionalPost = postService.findPostById(id);
        HttpSession session = request.getSession();
        User checkedUser = (User) session.getAttribute("user");
        if (checkedUser.getId() != optionalPost.get().getUserId()) {
            model.addAttribute("message", "У вас нет возможности обновлять объявление");
            return "redirect:/errors/404";
        }
        carService.update(car);
        Set<FileDto> postFiles = FileDtoConverter.convert(files);
        optionalPost.get().setCar(car);
        postService.update(optionalPost.get(), postFiles);
        return "redirect:/posts/all";
    }

    @GetMapping("/myPosts")
    public String getPostsOfUserPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        model.addAttribute("posts", postService.findPostsOfUser(user.getId()));
        return "posts/userPosts";
    }

    @GetMapping("/changePrice/{id}")
    public String getChangePricePage(@PathVariable int id, HttpServletRequest request, Model model) {
        Optional<Post> optionalPost = postService.findPostById(id);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление с указанным id не найдено");
            return "redirect:/errors/404";
        }
        HttpSession session = request.getSession();
        User checkedUser = (User) session.getAttribute("user");
        if (checkedUser.getId() != optionalPost.get().getUserId()) {
            model.addAttribute("message", "У вас нет возможности обновлять объявление");
            return "redirect:/errors/404";
        }
        model.addAttribute("post", optionalPost.get());
        model.addAttribute("price", optionalPost.get().getPrice());
        return "posts/changePrice";
    }

    @PostMapping("/changePrice/{id}")
    public String changePrice(@PathVariable int id, @RequestParam Integer price, Model model) {
        boolean isChanged = postService.changePrice(id, price);
        if (!isChanged) {
            model.addAttribute("message", "Произлщла ошибка при обновлении стоимости объявления");
            return "redirect:/errors/404";
        }
        return "redirect:/posts/myPosts";
    }
}
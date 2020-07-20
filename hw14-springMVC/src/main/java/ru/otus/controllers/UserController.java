package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.core.model.User;
import ru.otus.core.service.CachedDbServiceUserImpl;

import java.util.List;

@Controller
public class UserController {
    private final CachedDbServiceUserImpl userService;

    public UserController(CachedDbServiceUserImpl userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/user"})
    public String userListView(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "users";
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        model.addAttribute("user", new User());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user) {
        userService.saveUser(user);
        return new RedirectView("/", true);
    }
}

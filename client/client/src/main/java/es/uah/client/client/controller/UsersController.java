package es.uah.client.client.controller;

import es.uah.client.client.model.User;
import es.uah.client.client.service.IUsersService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    IUsersService userService;


    @PostMapping("/save/")
    public User save(@RequestBody User user) {
        boolean r = userService.saveUser(user);
        if(r) return userService.findUsersByUsername(user.getUsername()).get(0);
        else return null;
    }

    @PostMapping("/update/")
    public void update(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PostMapping("/login/")
    @ResponseBody
    public User login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }

    @GetMapping("/id/")
    @ResponseBody
    public User findUsersById(@RequestBody Integer id) {
        return userService.findUsersById(id);
    }


    @GetMapping("/update/{id}")
    public String updateUser(Model model, @PathVariable("id") Integer id) {
        User user = userService.findUsersById(id);
        model.addAttribute("title", "Editar usuario");
        model.addAttribute("user", user);
        return "register";
    }
}


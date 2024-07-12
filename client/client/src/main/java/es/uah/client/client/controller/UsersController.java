package es.uah.client.client.controller;

import es.uah.client.client.model.User;
import es.uah.client.client.service.IUsersService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    IUsersService userService;

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        User u = new User();
        model.addAttribute("user", u);
        return "forward:/vaadin/";
    }

    @GetMapping(value = {"/index"})
    public String home2(Model model) {
        User u = new User();
        model.addAttribute("user", u);
        return "home";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("title", "Registro");
        User u = new User();
        model.addAttribute("user", u);
        return "register";
    }

    @PostMapping("/save/")
    public String save(Model model, User r, RedirectAttributes attributes) {
        userService.saveUser(r);
        model.addAttribute("title", "Nuevo usuario");
        attributes.addFlashAttribute("msg", "El usuario ha sido creado correctamente!");
        return "login";
    }

    @PostMapping("/login/")
    @ResponseBody
    public Boolean login(@RequestBody User user) {
        Boolean result = userService.login(user.getUsername(), user.getPassword());
        if(!result){
            return false;
        }
        return true;
    }

    @GetMapping("/update/{id}")
    public String updateUser(Model model, @PathVariable("id") Integer id) {
        User user = userService.findUsersById(id);
        model.addAttribute("title", "Editar usuario");
        model.addAttribute("user", user);
        return "register";
    }
}


package es.uah.users.users.controller;

import es.uah.users.users.model.Users;
import es.uah.users.users.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {
    @Autowired
    IUsersService usersService;

    @GetMapping("/users")
    public List<Users> findAll(){
        return usersService.findAll();
    }

    @GetMapping("/users/{id}")
    public Users findUsersById(@PathVariable("id") Integer id) {
        return usersService.findUsersById(id);
    }

    @GetMapping("/users/login/{username}/{password}")
    public Boolean login(@PathVariable("username") String username, @PathVariable("password") String password) {
        System.out.println("Service " + username + " " + password);
        return usersService.login(username, password);
    }

    @GetMapping("/users/find/mail/{mail}")
    public Users findUsersByEmail(@PathVariable("mail") String mail) {
        return usersService.findUsersByEmail(mail);
    }

    @GetMapping("/users/find/{username}")
    public List<Users> findUsersByUsername(@PathVariable("username") String username) {
        return usersService.findUsersByUsername(username);
    }

    @PostMapping("/users")
    public void saveUser(@RequestBody Users user) {
        usersService.saveUser(user);
    }

    @PutMapping("/users")
    public void updateActor(@RequestBody Users user) {
        usersService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        usersService.deleteUser(id);
    }
}

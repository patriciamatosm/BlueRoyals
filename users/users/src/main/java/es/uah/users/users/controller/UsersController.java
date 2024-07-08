package es.uah.users.users.controller;

import es.uah.users.users.model.Users;
import es.uah.users.users.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {
    @Autowired
    IUsersService usersService;

    @GetMapping("/users")
    public List<Users> findAll(){
        return usersService.findAll();
    }
}

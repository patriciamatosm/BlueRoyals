package es.uah.users.users.service;

import es.uah.users.users.model.Users;

import java.util.List;

public interface IUsersService {
    List<Users> findAll();

    Users findUsersById(Integer id);

    List<Users> findUsersByUsername(String username);

    Users findUsersByEmail(String email);

    void saveUser(Users user);

    void deleteUser(Integer id);

    void updateUser(Users user);

    Boolean login(String username, String password);
}

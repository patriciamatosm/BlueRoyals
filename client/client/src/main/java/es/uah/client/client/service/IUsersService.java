package es.uah.client.client.service;

import es.uah.client.client.model.User;

import java.util.List;

public interface IUsersService {
    List<User> findAll();

    User findUsersById(Integer id);

    List<User> findUsersByUsername(String username);

    User findUsersByEmail(String email);

    boolean saveUser(User user);

    void deleteUser(Integer id);

    User login(String username, String password);
}

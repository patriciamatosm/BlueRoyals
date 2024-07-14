package es.uah.users.users.dao;

import es.uah.users.users.model.Users;

import java.util.List;

public interface IUsersDAO {
    List<Users> findAll();

    Users findUsersById(Integer id);

    List<Users> findUsersByUsername(String username);

    Users findUsersByEmail(String email);

    void saveUser(Users user);

    void deleteUser(Integer id);

    void updateUser(Users user);

    Users login(String username, String password);
}

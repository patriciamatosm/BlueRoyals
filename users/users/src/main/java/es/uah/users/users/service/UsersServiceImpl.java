package es.uah.users.users.service;

import es.uah.users.users.dao.IUsersDAO;
import es.uah.users.users.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements IUsersService{
    @Autowired
    IUsersDAO usersDAO;

    @Override
    public List<Users> findAll() {
        return usersDAO.findAll();
    }

    @Override
    public Users findUsersById(Integer id) {
        return usersDAO.findUsersById(id);
    }

    @Override
    public List<Users> findUsersByUsername(String username) {
        return usersDAO.findUsersByUsername(username);
    }

    @Override
    public Users findUsersByEmail(String email) {
        return usersDAO.findUsersByEmail(email);
    }

    @Override
    public void saveUser(Users user) {
        usersDAO.saveUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        usersDAO.deleteUser(id);
    }

    @Override
    public void updateUser(Users user) {
        usersDAO.updateUser(user);
    }

    @Override
    public Boolean login(String username, String password) {
        return usersDAO.login(username, password);
    }
}

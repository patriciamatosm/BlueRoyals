package es.uah.users.users.dao;

import es.uah.users.users.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsersImplDAO implements  IUsersDAO{

    @Autowired
    IUsersJPA usersJPA;

    @Override
    public List<Users> findAll() {
        return usersJPA.findAll();
    }

    @Override
    public Users findUsersById(Integer id) {
        Optional<Users> optional = usersJPA.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Users> findUsersByUsername(String username) {
        return usersJPA.findByUsernameContainingIgnoreCase(username);
    }

    @Override
    public Users findUsersByEmail(String email) {
        return usersJPA.findByEmail(email);
    }

    @Override
    public void saveUser(Users user) {
        usersJPA.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        usersJPA.deleteById(id);
    }

    @Override
    public void updateUser(Users user) {
        usersJPA.save(user);
    }

    @Override
    public Boolean login(String username, String password) {
        Users user = usersJPA.findByUsername(username);
        if(user == null) return false;

        return user.getPassword().equals(password);
    }
}

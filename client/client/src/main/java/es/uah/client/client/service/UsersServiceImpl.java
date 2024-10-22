package es.uah.client.client.service;

import es.uah.client.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UsersServiceImpl implements IUsersService{

    @Autowired
    RestTemplate template;

    String url = "http://localhost:8091/api/users/users";

    @Override
    public List<User> findAll() {
        User[] users = template.getForObject(url, User[].class);
        return Arrays.asList(users);
    }

    @Override
    public User findUsersById(Integer id) {
        return template.getForObject(url + "/" + id, User.class);
    }

    @Override
    public List<User> findUsersByUsername(String username) {
        User[] user = template.getForObject(url + "/find/" + username, User[].class);
        return Arrays.asList(user);
    }

    @Override
    public User findUsersByEmail(String email) {
        return template.getForObject(url + "/find/mail/" + email, User.class);
    }

    @Override
    public boolean saveUser(User user) {
        if (user.getId() != null && user.getId() > 0) {
            template.put(url, user);
            return true;
        } else {
            User[] user2 = template.getForObject(url + "/find/" + user.getUsername(), User[].class);
            System.out.println("Exist user:  " + Arrays.toString(user2));
            if (user2 == null || user2.length == 0) {
                System.out.println("eo");
                user.setId(0);
                template.postForObject(url, user, User.class);
                return true;
            } else {
                return false;
            }
        }
    }
    @Override
    public void deleteUser(Integer id) {
        template.delete(url + "/" + id);
    }

    @Override
    public User login(String username, String password) {
        return template.getForObject(url + "/login/" + username + "/" + password, User.class);
    }
}

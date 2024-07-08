package es.uah.users.users.dao;

import es.uah.users.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUsersJPA extends JpaRepository<Users, Integer> {
    List<Users> findByUsernameContainingIgnoreCase(String username);

    Users findByUsername(String username);
    Users findByEmail(String email);
}

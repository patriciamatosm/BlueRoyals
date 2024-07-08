package es.uah.users.users.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "Users.findAll", query = "SELECT r FROM Users r"),
        @NamedQuery(name = "Users.findById", query = "SELECT r FROM Users r WHERE r.id = :id"),
        @NamedQuery(name = "Users.findByEventName", query = "SELECT r FROM Users r WHERE r.username = :username")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "is_delete")
    private Boolean isDelete;

    public Users() {
    }

    public Users(Integer id, String username, String email, String password, String name, String surname, Boolean isDelete) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.isDelete = isDelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}

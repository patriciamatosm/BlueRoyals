package es.uah.events.events.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "events")
@NamedQueries({
        @NamedQuery(name = "Events.findAll", query = "SELECT r FROM Events r"),
        @NamedQuery(name = "Events.findById", query = "SELECT r FROM Events r WHERE r.id = :id"),
        @NamedQuery(name = "Events.findByEventName", query = "SELECT r FROM Events r WHERE r.eventName = :eventName")})
public class Events implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "max_user")
    private int maxUser;

    @Column(name = "event_name")
    private String movie;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private int createDate;

    public Events() {
    }

    public Events(Integer id) {
        this.id = id;
    }

    public Events(Integer id, int maxUser, String movie, String createUser, int createDate) {
        this.id = id;
        this.maxUser = maxUser;
        this.movie = movie;
        this.createUser = createUser;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public int getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }
}

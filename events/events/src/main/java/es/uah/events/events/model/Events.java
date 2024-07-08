package es.uah.events.events.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event")
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
    private String eventName;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "is_delete")
    private Boolean isDelete;

    public Events() {
    }

    public Events(Integer id) {
        this.id = id;
    }

    public Events(Integer id, int maxUser, String eventName, String createUser, Date createDate, Boolean isDelete) {
        this.id = id;
        this.maxUser = maxUser;
        this.eventName = eventName;
        this.createUser = createUser;
        this.createDate = createDate;
        this.isDelete = isDelete;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}

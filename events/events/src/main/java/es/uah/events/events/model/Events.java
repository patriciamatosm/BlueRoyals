package es.uah.events.events.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event")
@NamedQueries({
        @NamedQuery(name = "Events.findAll", query = "SELECT r FROM Events r WHERE isDelete = false"),
        @NamedQuery(name = "Events.findById", query = "SELECT r FROM Events r WHERE r.id = :id AND isDelete = false"),
        @NamedQuery(name = "Events.findByCreateUser", query = "SELECT r FROM Events r WHERE r.createUser = :createUser " +
                "AND isDelete = false"),
        @NamedQuery(name = "Events.findByEventName", query = "SELECT r FROM Events r WHERE r.eventName = :eventName " +
                "AND isDelete = false")})
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

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "image")
    private String image;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "event_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;

    @Column(name = "is_delete")
    private Boolean isDelete;

    public Events() {
    }

    public Events(Integer id) {
        this.id = id;
    }

    public Events(Integer id, int maxUser, String eventName, String description, String location,
                  Double longitude, Double latitude, Integer createUser, Date createDate, Date eventDate,
                  Boolean isDelete, String image) {
        this.id = id;
        this.maxUser = maxUser;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createUser = createUser;
        this.createDate = createDate;
        this.eventDate = eventDate;
        this.isDelete = isDelete;
        this.image = image;
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

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

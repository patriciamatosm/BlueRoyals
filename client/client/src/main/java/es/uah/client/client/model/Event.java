package es.uah.client.client.model;

import java.util.Date;

public class Event {
    private Integer id;

    private int maxUser;

    private String eventName;

    private Integer createUser;

    private Date createDate;

    private Boolean isDelete;


    private Date eventDate;
    private String description;
    private String location;

    private Double longitude;
    private Double latitude;
    private String image;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Event() {
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

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
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

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", maxUser=" + maxUser +
                ", eventName='" + eventName + '\'' +
                ", createUser=" + createUser +
                ", createDate=" + createDate +
                ", isDelete=" + isDelete +
                ", eventDate=" + eventDate +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", image='" + image + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

package es.uah.client.client.model;

import java.util.Date;

public class Event {
    private Integer id;

    private int maxUser;

    private String eventName;

    private String createUser;

    private Date createDate;

    private Boolean isDelete;

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

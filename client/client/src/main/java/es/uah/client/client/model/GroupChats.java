package es.uah.client.client.model;

import java.util.Date;

public class GroupChats {

    private Integer id;

    private String chatName;

    private Integer createUser;

    private Integer idEvent;

    private Date createDate;

    public GroupChats(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "GroupChats{" +
                "id=" + id +
                ", chatName='" + chatName + '\'' +
                ", createUser=" + createUser +
                ", idEvent=" + idEvent +
                ", createDate=" + createDate +
                '}';
    }
}

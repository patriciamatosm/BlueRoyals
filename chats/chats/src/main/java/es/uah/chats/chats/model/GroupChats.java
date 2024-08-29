package es.uah.chats.chats.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "group_chat")
@NamedQueries({
        @NamedQuery(name = "GroupChats.findAll", query = "select g from GroupChats g"),
        @NamedQuery(name = "GroupChats.findById", query = "select g from GroupChats g where g.id = :id"),
        @NamedQuery(name = "GroupChats.findByCreateUser", query = "select g from GroupChats g where g.createUser = :createUser"),
        @NamedQuery(name = "GroupChats.findByChatName", query = "select g from GroupChats g where g.chatName = :chatName"),
        @NamedQuery(name = "GroupChats.findByIdEvent", query = "select g from GroupChats g where g.idEvent = :idEvent")
})
public class GroupChats implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "chat_name")
    private String chatName;

    @Column(name = "id_user")
    private Integer createUser;

    @Column(name = "id_event")
    private Integer idEvent;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
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
}

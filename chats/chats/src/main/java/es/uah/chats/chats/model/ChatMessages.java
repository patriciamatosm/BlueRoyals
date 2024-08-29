package es.uah.chats.chats.model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "chat_message")
@NamedQueries({
        @NamedQuery(name = "ChatMessages.findAll", query = "select c from ChatMessages c"),
        @NamedQuery(name = "ChatMessages.findById", query = "select c from ChatMessages c where c.id = :id"),
        @NamedQuery(name = "ChatMessages.findByIdChat", query = "select c from ChatMessages c where c.idChat = :idChat")
})
public class ChatMessages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "text_msg")
    private String textMsg;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "id_chat")
    private Integer idChat;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public ChatMessages(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextMsg() {
        return textMsg;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdChat() {
        return idChat;
    }

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

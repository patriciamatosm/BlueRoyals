package es.uah.client.client.model;

import java.util.Date;

public class ChatMessages  {

    private Integer id;

    private String textMsg;

    private Integer idUser;

    private Integer idChat;

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

    @Override
    public String toString() {
        return "ChatMessages{" +
                "id=" + id +
                ", textMsg='" + textMsg + '\'' +
                ", idUser=" + idUser +
                ", idChat=" + idChat +
                ", date=" + date +
                '}';
    }
}

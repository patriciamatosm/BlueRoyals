package es.uah.client.client.model;

public class Subscription {
    private Integer id;

    private int idEvent;

    private int idUser;

    private Boolean isDelete;

    public Subscription(int idEvent, int idUser, Boolean isDelete) {
        this.idEvent = idEvent;
        this.idUser = idUser;
        this.isDelete = isDelete;
    }

    public Subscription() {
    }


    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", idEvent=" + idEvent +
                ", idUser=" + idUser +
                ", isDelete=" + isDelete +
                '}';
    }
}

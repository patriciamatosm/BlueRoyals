package es.uah.subscriptions.subscription.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "subscription")
@NamedQueries({
        @NamedQuery(name = "Subscriptions.findAll", query = "SELECT r FROM Subscriptions r"),
        @NamedQuery(name = "Subscriptions.findById", query = "SELECT r FROM Subscriptions r WHERE r.id = :id")})
public class Subscriptions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_event")
    private int idEvent;

    @Column(name = "id_user")
    private int idUser;

    public Subscriptions() {
    }

    public Subscriptions(int idEvent, int idUser) {
        this.idEvent = idEvent;
        this.idUser = idUser;
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
}

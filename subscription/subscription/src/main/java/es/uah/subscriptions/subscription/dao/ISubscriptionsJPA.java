package es.uah.subscriptions.subscription.dao;

import es.uah.subscriptions.subscription.model.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISubscriptionsJPA extends JpaRepository<Subscriptions, Integer> {

    List<Subscriptions> findByIdEvent(int idEvent);
    List<Subscriptions> findByIdUser(int idUser);
    Subscriptions findByIdUserAndIdEvent(int idEvent, int idUser);
}

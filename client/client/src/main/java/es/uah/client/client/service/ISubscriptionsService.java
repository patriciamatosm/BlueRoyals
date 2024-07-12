package es.uah.client.client.service;

import es.uah.client.client.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISubscriptionsService {
    List<Subscription> findAll();

    Page<Subscription> findAll(Pageable pageable);

    List<Subscription> findSubscriptionsByEvent(Integer idEvent);

    Subscription findSubscriptionsById(Integer id);

    List<Subscription> findUserSubscriptions(Integer idUser);

    void saveSubscription(Subscription sub);

    void deleteSubscription(Integer id);

}

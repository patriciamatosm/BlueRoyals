package es.uah.subscriptions.subscription.service;

import es.uah.subscriptions.subscription.model.Subscriptions;

import java.util.List;

public interface ISubscriptionsService {
    List<Subscriptions> findAll();
    Subscriptions findSubscriptionById(Integer id);

    List<Subscriptions> findSubscriptionsByEvent(Integer idEvent);

    List<Subscriptions> findUserSubscriptions(Integer idUser);

    void saveSubscription(Subscriptions sub);

    void deleteSubscription(Integer id);

    void updateSubscription(Subscriptions sub);

    Subscriptions findByIdUserAndIdEvent(int idEvent, int idUser);

}

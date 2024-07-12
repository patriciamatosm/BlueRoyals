package es.uah.subscriptions.subscription.dao;

import es.uah.subscriptions.subscription.model.Subscriptions;

import java.util.List;

public interface ISubscriptionsDAO {
    List<Subscriptions> findAll();

    Subscriptions findById(Integer id);

    List<Subscriptions> findSubscriptionsByEvent(Integer idEvent);

    List<Subscriptions> findUserSubscriptions(Integer idUser);

    void saveSubscription(Subscriptions sub);

    void deleteSubscription(Integer id);

    void updateSubscription(Subscriptions sub);
}

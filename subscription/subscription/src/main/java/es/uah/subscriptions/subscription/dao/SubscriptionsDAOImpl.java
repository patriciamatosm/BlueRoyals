package es.uah.subscriptions.subscription.dao;

import es.uah.subscriptions.subscription.model.Subscriptions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SubscriptionsDAOImpl implements ISubscriptionsDAO{

    @Autowired
    ISubscriptionsJPA subscriptionsJPA;

    @Override
    public List<Subscriptions> findAll() {
        return subscriptionsJPA.findAll();
    }

    @Override
    public List<Subscriptions> findSubscriptionsByEvent(Integer idEvent) {
        return subscriptionsJPA.findByIdEvent(idEvent);
    }

    @Override
    public List<Subscriptions> findUserSubscriptions(Integer idUser) {
        return subscriptionsJPA.findByIdUser(idUser);
    }

    @Override
    public void saveSubscription(Subscriptions sub) {
        subscriptionsJPA.save(sub);
    }

    @Override
    public void deleteSubscription(Integer id) {
        subscriptionsJPA.deleteById(id);
    }

    @Override
    public void updateSubscription(Subscriptions sub) {
        subscriptionsJPA.save(sub);
    }
}

package es.uah.subscriptions.subscription.dao;

import es.uah.subscriptions.subscription.model.Subscriptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionsDAOImpl implements ISubscriptionsDAO{

    @Autowired
    ISubscriptionsJPA subscriptionsJPA;

    @Override
    public List<Subscriptions> findAll() {
        return subscriptionsJPA.findAll();
    }

    @Override
    public Subscriptions findById(Integer id) {
        Optional<Subscriptions> optional = subscriptionsJPA.findById(id);
        return optional.orElse(null);
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

    @Override
    public Subscriptions findByIdUserAndIdEvent(int idEvent, int idUser) {
        return subscriptionsJPA.findByIdUserAndIdEvent(idEvent, idUser);
    }
}

package es.uah.subscriptions.subscription.service;

import es.uah.subscriptions.subscription.dao.ISubscriptionsDAO;
import es.uah.subscriptions.subscription.model.Subscriptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionsServiceImpl implements ISubscriptionsService{
    @Autowired
    ISubscriptionsDAO subscriptionsDAO;


    @Override
    public List<Subscriptions> findAll() {
        return subscriptionsDAO.findAll();
    }

    @Override
    public Subscriptions findSubscriptionById(Integer id) {
        return subscriptionsDAO.findById(id);
    }

    @Override
    public List<Subscriptions> findSubscriptionsByEvent(Integer idEvent) {
        return subscriptionsDAO.findSubscriptionsByEvent(idEvent);
    }

    @Override
    public List<Subscriptions> findUserSubscriptions(Integer idUser) {
        return subscriptionsDAO.findUserSubscriptions(idUser);
    }

    @Override
    public void saveSubscription(Subscriptions sub) {
        subscriptionsDAO.saveSubscription(sub);
    }

    @Override
    public void deleteSubscription(Integer id) {
        subscriptionsDAO.deleteSubscription(id);
    }

    @Override
    public void updateSubscription(Subscriptions sub) {
        subscriptionsDAO.updateSubscription(sub);
    }

    @Override
    public Subscriptions findByIdUserAndIdEvent(int idEvent, int idUser) {
        return subscriptionsDAO.findByIdUserAndIdEvent(idEvent, idUser);
    }
}

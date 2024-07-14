package es.uah.client.client.service;

import es.uah.client.client.model.Event;
import es.uah.client.client.model.Subscription;
import es.uah.client.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class SubscriptionsServiceImpl implements ISubscriptionsService{

    @Autowired
    RestTemplate template;

    String url = "http://localhost:8091/api/subs/subs";

    @Override
    public List<Subscription> findAll() {
        return Arrays.asList(Objects.requireNonNull(template.getForObject(url, Subscription[].class)));
    }

    @Override
    public Page<Subscription> findAll(Pageable pageable) {
        Subscription[] Subscription = template.getForObject(url, Subscription[].class);
        List<Subscription> subsList = Arrays.asList(Subscription);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Subscription> list;

        if (subsList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, subsList.size());
            list = subsList.subList(startItem, toIndex);
        }

        Page<Subscription> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), subsList.size());
        return page;
    }


    @Override
    public Subscription findSubscriptionsById(Integer id) {
        return template.getForObject(url, Subscription.class);
    }

    @Override
    public List<Subscription> findSubscriptionsByEvent(Integer idEvent) {
        return Arrays.asList(Objects.requireNonNull(template.getForObject(url + "/event/" + idEvent, Subscription[].class)));
        
    }

    @Override
    public List<Subscription> findUserSubscriptions(Integer idUser) {
        return Arrays.asList(Objects.requireNonNull(template.getForObject(url + "/user/" + idUser, Subscription[].class)));

    }

    @Override
    public void saveSubscription(Subscription sub) {
        if (sub.getId() != null && sub.getId() > 0) {
            template.postForObject(url, sub,  String.class);
        } else {
            sub.setId(0);
            template.postForObject(url, sub, String.class);
        }
    }

    @Override
    public void deleteSubscription(Integer id) {
        template.delete(url + "/" + id);
    }
}

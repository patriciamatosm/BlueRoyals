package es.uah.client.client.service;

import es.uah.client.client.model.Event;
import es.uah.client.client.model.GroupChats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GroupChatsServiceImpl implements IGroupChatsService{
    @Autowired
    RestTemplate template;

    String url = "http://localhost:8091/api/chats/chats";

    @Override
    public List<GroupChats> findAll() {
        GroupChats[] gc = template.getForObject(url, GroupChats[].class);
        return Arrays.asList(gc);
    }

    @Override
    public GroupChats findGroupChatsById(Integer id) {
        return template.getForObject(url + '/' + id, GroupChats.class);
    }

    @Override
    public GroupChats findGroupChatsByIdEvent(Integer idEvent) {
        return template.getForObject(url + "/event/" + idEvent, GroupChats.class);
    }

    @Override
    public void saveGroupChats(GroupChats chat) {
        if (chat.getId() != null && chat.getId() > 0) {
            template.postForObject(url, chat,  String.class);
        } else {
            chat.setId(0);
            template.postForObject(url, chat, String.class);
        }
    }

    @Override
    public void deleteGroupChats(Integer id) {
        template.delete(url + "/" + id);
    }

    @Override
    public List<GroupChats> findGroupChatsByCreateUser(Integer idUser) {
        return Arrays.asList(template.getForObject(url + "/user/" + idUser, GroupChats[].class));
    }
}

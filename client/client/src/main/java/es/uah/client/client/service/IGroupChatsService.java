package es.uah.client.client.service;


import es.uah.client.client.model.GroupChats;

import java.util.List;

public interface IGroupChatsService {
    List<GroupChats> findAll();

    GroupChats findGroupChatsById(Integer id);

    GroupChats findGroupChatsByIdEvent(Integer idEvent);

    void saveGroupChats(GroupChats chat);

    void deleteGroupChats(Integer id);

    List<GroupChats> findGroupChatsByCreateUser(Integer idUser);

}

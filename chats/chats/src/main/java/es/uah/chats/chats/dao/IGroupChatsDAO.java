package es.uah.chats.chats.dao;

import es.uah.chats.chats.model.GroupChats;

import java.util.List;

public interface IGroupChatsDAO {
    List<GroupChats> findAll();

    GroupChats findGroupChatsById(Integer id);

    GroupChats findGroupChatsByIdEvent(Integer idEvent);

    void saveGroupChats(GroupChats chat);

    void deleteGroupChats(Integer id);

}

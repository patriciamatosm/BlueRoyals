package es.uah.chats.chats.dao;

import es.uah.chats.chats.model.ChatMessages;
import es.uah.chats.chats.model.GroupChats;

import java.util.List;

public interface IGroupChatsDAO {
    List<GroupChats> findAll();

    GroupChats findGroupChatsById(Integer id);

    GroupChats findGroupChatsByIdEvent(Integer idEvent);

    List<GroupChats> findGroupChatsByCreateUser(Integer idUser);

    void saveGroupChats(GroupChats chat);

    void deleteGroupChats(Integer id);

}

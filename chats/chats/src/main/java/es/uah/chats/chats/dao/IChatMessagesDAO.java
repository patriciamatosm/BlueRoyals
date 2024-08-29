package es.uah.chats.chats.dao;

import es.uah.chats.chats.model.ChatMessages;

import java.util.List;

public interface IChatMessagesDAO {
    List<ChatMessages> findAll();
    
    ChatMessages findChatMessagesById(Integer id);

    ChatMessages findChatMessagesByIdChat(Integer idChat);

    List<ChatMessages> findChatMessagesByIdUser(Integer idUser);

    void saveChatMessages(ChatMessages chat);

    void deleteChatMessages(Integer id);

}

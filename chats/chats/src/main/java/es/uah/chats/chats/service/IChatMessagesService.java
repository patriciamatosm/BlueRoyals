package es.uah.chats.chats.service;

import es.uah.chats.chats.model.ChatMessages;
import es.uah.chats.chats.model.GroupChats;

import java.util.List;

public interface IChatMessagesService {
    List<ChatMessages> findAll();

    ChatMessages findChatMessagesById(Integer id);

    List<ChatMessages> findChatMessagesByIdChat(Integer idChat);

    void saveChatMessages(ChatMessages chat);

    void deleteChatMessages(Integer id);

    List<ChatMessages> findChatMessagesByIdUser(Integer idUser);

}

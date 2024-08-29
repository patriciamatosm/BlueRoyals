package es.uah.client.client.service;

import es.uah.client.client.model.ChatMessages;

import java.util.List;

public interface IChatMessagesService {
    List<ChatMessages> findAll();

    ChatMessages findChatMessagesById(Integer id);

    List<ChatMessages> findChatMessagesByIdChat(Integer idChat);

    void saveChatMessages(ChatMessages chat);

    void deleteChatMessages(Integer id);
}

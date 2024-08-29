package es.uah.chats.chats.dao;

import es.uah.chats.chats.model.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChatMessagesJPA extends JpaRepository<ChatMessages, Integer> {
    ChatMessages findByIdChat(Integer idChat);
}

package es.uah.chats.chats.dao;

import es.uah.chats.chats.model.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IChatMessagesJPA extends JpaRepository<ChatMessages, Integer> {
    List<ChatMessages> findByIdChat(Integer idChat);
    List<ChatMessages> findChatMessagesByIdUser(Integer idUser);

}

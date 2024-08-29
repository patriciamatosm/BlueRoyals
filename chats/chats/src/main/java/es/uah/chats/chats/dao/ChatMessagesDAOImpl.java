package es.uah.chats.chats.dao;

import es.uah.chats.chats.model.ChatMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChatMessagesDAOImpl implements  IChatMessagesDAO{
    @Autowired
    IChatMessagesJPA chatMessagesJPA;

    @Override
    public List<ChatMessages> findAll() {
        return chatMessagesJPA.findAll();
    }

    @Override
    public ChatMessages findChatMessagesById(Integer id) {
        Optional<ChatMessages> optional = chatMessagesJPA.findById(id);
        return optional.orElse(null);
    }

    @Override
    public ChatMessages findChatMessagesByIdChat(Integer idChat) {
        return chatMessagesJPA.findByIdChat(idChat);
    }

    @Override
    public void saveChatMessages(ChatMessages chat) {
        chatMessagesJPA.save(chat);
    }

    @Override
    public void deleteChatMessages(Integer id) {
        chatMessagesJPA.deleteById(id);
    }
}

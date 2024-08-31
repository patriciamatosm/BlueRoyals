package es.uah.chats.chats.service;

import es.uah.chats.chats.dao.IChatMessagesDAO;
import es.uah.chats.chats.dao.IGroupChatsDAO;
import es.uah.chats.chats.model.ChatMessages;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessagesServiceImpl implements IChatMessagesService{
    @Autowired
    IChatMessagesDAO chatMessagesDAO;

    @Override
    public List<ChatMessages> findAll() {
        return chatMessagesDAO.findAll();
    }

    @Override
    public ChatMessages findChatMessagesById(Integer id) {
        return chatMessagesDAO.findChatMessagesById(id);
    }

    @Override
    public List<ChatMessages> findChatMessagesByIdChat(Integer idChat) {
        return chatMessagesDAO.findChatMessagesByIdChat(idChat);
    }

    @Override
    public ChatMessages saveChatMessages(ChatMessages chat) {
        if (chat.getTextMsg().length() > 255) {
            throw new ConstraintViolationException("Message cannot exceed 255 characters", null);
        }
        return chatMessagesDAO.saveChatMessages(chat);
    }

    @Override
    public void deleteChatMessages(Integer id) {
        chatMessagesDAO.deleteChatMessages(id);
    }

    @Override
    public List<ChatMessages> findChatMessagesByIdUser(Integer idUser) {
        return chatMessagesDAO.findChatMessagesByIdUser(idUser);
    }
}

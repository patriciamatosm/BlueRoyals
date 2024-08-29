package es.uah.chats.chats.service;

import es.uah.chats.chats.dao.IChatMessagesDAO;
import es.uah.chats.chats.dao.IGroupChatsDAO;
import es.uah.chats.chats.model.ChatMessages;
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
    public ChatMessages findChatMessagesByIdChat(Integer idChat) {
        return chatMessagesDAO.findChatMessagesByIdChat(idChat);
    }

    @Override
    public void saveChatMessages(ChatMessages chat) {
        chatMessagesDAO.saveChatMessages(chat);
    }

    @Override
    public void deleteChatMessages(Integer id) {
        chatMessagesDAO.deleteChatMessages(id);
    }
}

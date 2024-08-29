package es.uah.client.client.service;

import es.uah.client.client.model.ChatMessages;
import es.uah.client.client.model.GroupChats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ChatMessagesServiceImpl implements IChatMessagesService {
    @Autowired
    RestTemplate template;

    String url = "http://localhost:8091/api/chats/msg";

    @Override
    public List<ChatMessages> findAll() {
        ChatMessages[] gc = template.getForObject(url, ChatMessages[].class);
        return Arrays.asList(gc);
    }

    @Override
    public ChatMessages findChatMessagesById(Integer id) {
        return template.getForObject(url + '/' + id, ChatMessages.class);
    }

    @Override
    public ChatMessages findChatMessagesByIdChat(Integer idChat) {
        return template.getForObject(url + "/chat/" + idChat, ChatMessages.class);
    }

    @Override
    public void saveChatMessages(ChatMessages chat) {
        if (chat.getId() != null && chat.getId() > 0) {
            template.postForObject(url, chat,  String.class);
        } else {
            chat.setId(0);
            template.postForObject(url, chat, String.class);
        }
    }

    @Override
    public void deleteChatMessages(Integer id) {
        template.delete(url + "/" + id);
    }
}

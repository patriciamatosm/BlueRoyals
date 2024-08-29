package es.uah.chats.chats.controller;

import es.uah.chats.chats.model.ChatMessages;
import es.uah.chats.chats.service.IChatMessagesService;
import es.uah.chats.chats.service.IGroupChatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatMessagesController {
    @Autowired
    IChatMessagesService chatMessagesService;

    @GetMapping("/msg")
    public List<ChatMessages> findAll() {
        return chatMessagesService.findAll();
    }

    @GetMapping("/msg/{id}")
    public ChatMessages findChatMessagesById(@PathVariable("id") Integer id) {
        return chatMessagesService.findChatMessagesById(id);
    }

    @GetMapping("/msg/chat/{idChat}")
    public List<ChatMessages> findChatMessagesByIdChat(@PathVariable("idChat") Integer idChat) {
        return chatMessagesService.findChatMessagesByIdChat(idChat);
    }

    @PostMapping("/msg")
    public void saveChatMessages(@RequestBody ChatMessages chat) {
        chatMessagesService.saveChatMessages(chat);
    }

    @DeleteMapping("/msg/{id}")
    public void deleteChatMessages(@PathVariable("id") Integer id) {
        chatMessagesService.deleteChatMessages(id);
    }
}

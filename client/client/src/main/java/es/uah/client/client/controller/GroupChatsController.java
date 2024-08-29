package es.uah.client.client.controller;

import es.uah.client.client.model.ChatMessages;
import es.uah.client.client.model.Event;
import es.uah.client.client.model.GroupChats;
import es.uah.client.client.model.User;
import es.uah.client.client.service.IChatMessagesService;
import es.uah.client.client.service.IGroupChatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/chats")
public class GroupChatsController {
    @Autowired
    IGroupChatsService groupChatsService;

    @Autowired
    IChatMessagesService chatMessagesService;


    public List<GroupChats> findAll(){
        return groupChatsService.findAll();
    }

    @ResponseBody
    public List<GroupChats> getChatsByUser(@RequestBody Integer userId) {
        return groupChatsService.findGroupChatsByCreateUser(userId);
    }

    @ResponseBody
    public GroupChats createChat(@RequestBody String chatName, @RequestBody Event event, @RequestBody User user) {
       if(groupChatsService.findGroupChatsByIdEvent(event.getId()) == null){
           GroupChats newChat = new GroupChats();
           newChat.setChatName(chatName);
           newChat.setIdEvent(event.getId());
           newChat.setCreateUser(user.getId());
           groupChatsService.saveGroupChats(newChat);
           return groupChatsService.findGroupChatsByIdEvent(event.getId());
       }

       return null;
    }

    @ResponseBody
    public List<ChatMessages> getMessagesByChat(@RequestBody Integer chatId) {
        return Arrays.asList(chatMessagesService.findChatMessagesByIdChat(chatId));
    }

}

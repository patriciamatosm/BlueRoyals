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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    public void createChatMesage(@RequestBody GroupChats chat, @RequestBody User user, @RequestBody String content) {
        ChatMessages msg = new ChatMessages();
        msg.setTextMsg(content);
        msg.setIdUser(user.getId());
        msg.setIdChat(chat.getId());

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        msg.setDate(date);

        chatMessagesService.saveChatMessages(msg);

    }

    @ResponseBody
    public List<ChatMessages> getMessagesByChat(@RequestBody Integer chatId) {
        return chatMessagesService.findChatMessagesByIdChat(chatId);
    }

    @ResponseBody
    public List<GroupChats> getChatByEvent(@RequestBody List<Event> events) {
        List<GroupChats> chats = new ArrayList<>();
        for (Event e : events){
            GroupChats gc = groupChatsService.findGroupChatsByIdEvent(e.getId());
            chats.add(gc);
        }
        return chats;
    }

}

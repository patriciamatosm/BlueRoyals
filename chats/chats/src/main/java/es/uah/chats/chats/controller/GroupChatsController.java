package es.uah.chats.chats.controller;

import es.uah.chats.chats.model.GroupChats;
import es.uah.chats.chats.service.IGroupChatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupChatsController {
    @Autowired
    IGroupChatsService groupChatsService;

    @GetMapping("/chats")
    public List<GroupChats> findAll(){
        return groupChatsService.findAll();
    }

    @GetMapping("/chats/{id}")
    public GroupChats findGroupChatsById(@PathVariable("id") Integer id) {
        return groupChatsService.findGroupChatsById(id);
    }

    @GetMapping("/chats/event/{idEvent}")
    public GroupChats findGroupChatsByIdEvent(@PathVariable("idEvent") Integer idEvent) {
        return groupChatsService.findGroupChatsByIdEvent(idEvent);
    }

    @PostMapping("/chats")
    public void saveGroupChats(@RequestBody GroupChats chat) {
        groupChatsService.saveGroupChats(chat);
    }

    @DeleteMapping("/chats/{id}")
    public void deleteGroupChats(@PathVariable("id") Integer id) {
        groupChatsService.deleteGroupChats(id);
    }
}

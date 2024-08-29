package es.uah.chats.chats.service;

import es.uah.chats.chats.dao.IGroupChatsDAO;
import es.uah.chats.chats.model.GroupChats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupChatsServiceImpl implements IGroupChatsService{
    @Autowired
    IGroupChatsDAO groupChatsDAO;

    @Override
    public List<GroupChats> findAll() {
        return groupChatsDAO.findAll();
    }

    @Override
    public GroupChats findGroupChatsById(Integer id) {
        return groupChatsDAO.findGroupChatsById(id);
    }

    @Override
    public GroupChats findGroupChatsByIdEvent(Integer idEvent) {
        return groupChatsDAO.findGroupChatsByIdEvent(idEvent);
    }

    @Override
    public void saveGroupChats(GroupChats chat) {
        groupChatsDAO.saveGroupChats(chat);
    }

    @Override
    public void deleteGroupChats(Integer id) {
        groupChatsDAO.deleteGroupChats(id);
    }

    @Override
    public List<GroupChats> findGroupChatsByCreateUser(Integer idUser) {
        return groupChatsDAO.findGroupChatsByCreateUser(idUser);
    }
}

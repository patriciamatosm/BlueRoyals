package es.uah.chats.chats.dao;

import es.uah.chats.chats.model.GroupChats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GroupChatsDAOImpl implements  IGroupChatsDAO{
    @Autowired
    IGroupChatsJPA groupChatsJPA;

    @Override
    public List<GroupChats> findAll() {
        return groupChatsJPA.findAll();
    }

    @Override
    public GroupChats findGroupChatsById(Integer id) {
        Optional<GroupChats> optional = groupChatsJPA.findById(id);
        return optional.orElse(null);
    }

    @Override
    public GroupChats findGroupChatsByIdEvent(Integer idEvent) {
        return groupChatsJPA.findByIdEvent(idEvent);
    }

    @Override
    public List<GroupChats> findGroupChatsByCreateUser(Integer idUser) {
        return groupChatsJPA.findGroupChatsByCreateUser(idUser);
    }

    @Override
    public void saveGroupChats(GroupChats chat) {
        groupChatsJPA.save(chat);
    }

    @Override
    public void deleteGroupChats(Integer id) {
        groupChatsJPA.deleteById(id);
    }
}

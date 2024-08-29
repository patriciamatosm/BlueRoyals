package es.uah.chats.chats.dao;

import es.uah.chats.chats.model.GroupChats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IGroupChatsJPA extends JpaRepository<GroupChats, Integer> {
    GroupChats findByIdEvent(Integer idEvent);

    List<GroupChats> findGroupChatsByCreateUser(Integer idUser);

}

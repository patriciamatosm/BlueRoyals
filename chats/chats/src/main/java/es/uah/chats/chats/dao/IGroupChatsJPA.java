package es.uah.chats.chats.dao;

import es.uah.chats.chats.model.GroupChats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupChatsJPA extends JpaRepository<GroupChats, Integer> {
    GroupChats findByIdEvent(Integer idEvent);
}

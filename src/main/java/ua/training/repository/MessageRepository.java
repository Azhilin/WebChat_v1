package ua.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.training.domain.Message;
import ua.training.domain.User;

import java.util.List;

/**
 * Created by Adrien on 09.03.2017.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM ua.training.domain.Message m " +
            "WHERE m.receiver.login= :receiver")
    List<Message> getMessagesByReceiver(@Param("receiver")String receiver);

    @Modifying
    @Query("DELETE FROM ua.training.domain.Message m " +
            "WHERE m.receiver= :receiver")
    void deleteMessagesByReceiver(@Param("receiver")User receiver);
}

package ua.training.service;

import javafx.util.Pair;
import ua.training.dto.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Adrien on 09.03.2017.
 */
public interface WebChatService {
    UserDTO getUserBySessionId(String sessionId);
    List<Pair<String, String>> getMessagesForUser(UserDTO userDTO);
    void saveBroadcastMessage(String login, String message);
    void savePrivateMessage(String receiver, String sender,
                            String messageReceiver);
    void invalidateHttpSession(String login);
}

package ua.training.service.impl;


import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.domain.Message;
import ua.training.domain.User;
import ua.training.dto.UserDTO;
import ua.training.listener.CreateHttpSessionListener;
import ua.training.repository.MessageRepository;
import ua.training.repository.UserRepository;
import ua.training.dao.RedisDAO;
import ua.training.service.WebChatService;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Adrien on 09.03.2017.
 */
@Service
public class WebChatServiceImpl implements WebChatService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisDAO redisDAO;

    @Override
    public UserDTO getUserBySessionId(String sessionId) {
        HttpSession session = CreateHttpSessionListener
                .getSessionById(sessionId);
        if(session == null){
            return null;
        }
        User user = (User)session.getAttribute("user");
        if(user == null){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        if(user.getBlackList() != null){
            userDTO.setBanned(true);
        }
        return userDTO;
    }

    @Override
    @Transactional
    public List<Pair<String, String>> getMessagesForUser(UserDTO userDTO) {
        List<Message> privateMessages = messageRepository
                .getMessagesByReceiver(userDTO.getLogin());
        List<String> broadcastMessages = redisDAO.getAllBroadcastMessages();
        List<Pair<String, String>> allMessages = new ArrayList<>();
        for(Message privateMessage: privateMessages){
            allMessages.add(new Pair<>(privateMessage.getSender().getLogin()
                    , privateMessage.getBody()));
        }
        User u = userRepository.getUserByLogin(userDTO.getLogin());
        messageRepository.deleteMessagesByReceiver(u);
            for (String broadcastMessage : broadcastMessages) {

                String[] arrayBroadcastMessage = broadcastMessage.split(":");
                if(arrayBroadcastMessage.length < 2){
                    allMessages.add(new Pair<>(arrayBroadcastMessage[0], ""));
                } else {
                    allMessages.add(new Pair<>(arrayBroadcastMessage[0],
                            arrayBroadcastMessage[1]));
                }
            }
        return allMessages;
    }

    @Override
    public void saveBroadcastMessage(String login, String message) {
        redisDAO.saveMessage(login, message);
    }

    @Override
    public void savePrivateMessage(String receiver, String sender,
                                   String messageReceiver) {
        User recev = userRepository.getUserByLogin(receiver);
        User sendr = userRepository.getUserByLogin(sender);
        Message mes = new Message();
        mes.setBody(messageReceiver);
        mes.setSender(sendr);
        mes.setReceiver(recev);
        messageRepository.save(mes);
    }

    @Override
    public void invalidateHttpSession(String login) {
        HttpSession httpSession = CreateHttpSessionListener
                .getSessionByLogin(login);
        if(httpSession != null){
            CreateHttpSessionListener.removeSession(httpSession.getId());
            httpSession.invalidate();
        }
    }
}

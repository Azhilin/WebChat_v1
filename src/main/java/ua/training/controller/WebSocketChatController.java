package ua.training.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ua.training.domain.BlackList;
import ua.training.domain.User;
import ua.training.dto.UserDTO;
import ua.training.listener.CreateHttpSessionListener;
import ua.training.service.UserService;
import ua.training.service.WebChatService;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Adrien on 07.03.2017.
 */
@Controller
public class WebSocketChatController extends TextWebSocketHandler {
    @Autowired
    private WebChatService webChatService;

    @Autowired
    private UserService userService;

    private final Map<String, WebSocketSession> clients
            = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session,
                                  TextMessage message) throws Exception {
        if(isBanned(session)){
            session.sendMessage(new TextMessage("{\"auth\":\"no\"}"));
            return;
        }

        String mes = message.getPayload();
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap<String, String>>(){}
                                                       .getType();
        Map<String, String> clientRequest = gson.fromJson(mes, gsonType);
        if(clientRequest.containsKey("sessionId")){
            String idSession = clientRequest.get("sessionId");
            UserDTO userDTO = webChatService.getUserBySessionId(idSession);
            if(userDTO == null){
                session.sendMessage(new TextMessage("{\"auth\":\"no\"}"));
                return;
            } else {
                clients.put(userDTO.getLogin(), session);
                session.sendMessage(new TextMessage("{\"auth\":\"yes\"}"));
                List<Pair<String, String>> newMessages = webChatService
                        .getMessagesForUser(userDTO);
                for(Pair<String, String> item: newMessages){
                    JsonObject jMessage = new JsonObject();
                    jMessage.addProperty("name", item.getKey());
                    jMessage.addProperty("message", item.getValue());
                    session.sendMessage(new TextMessage(jMessage.toString()));
                }

                return;
            }
        }
        if(clients.values().contains(session) &&
                clientRequest.containsKey("list")){
            Set<String> clientLogin = clients.keySet();
            JsonObject clientList = new JsonObject();
            clientList.add("list", gson.toJsonTree(clientLogin));
            session.sendMessage(new TextMessage(clientList.toString()));
            return;
        }
        if(clients.values().contains(session) &&
                clientRequest.containsKey("broadcast")){
            String login = getLoginBySession(session);
            String mess = clientRequest.get("broadcast");
            JsonObject broadcastMessage = new JsonObject();
            broadcastMessage.addProperty("name", login);
            broadcastMessage.addProperty("message", mess);
            webChatService.saveBroadcastMessage(login, mess);
            for(WebSocketSession clientsSession: clients.values()){
                clientsSession.sendMessage(new TextMessage(broadcastMessage.toString()));
            }
            return;
        }
        if(clients.values().contains(session) &&
                clientRequest.containsKey("name")){
            String receiver = clientRequest.get("name");
            String messageReceiver = clientRequest.get("message");
            String sender = getLoginBySession(session);
            WebSocketSession receiverSession = clients.get(receiver);
            if(receiverSession == null){
                webChatService.savePrivateMessage(receiver, sender,
                        messageReceiver);
                return;
            } else {
                JsonObject privateMessage = new JsonObject();
                privateMessage.addProperty("name", sender);
                privateMessage.addProperty("message", messageReceiver);
                receiverSession.sendMessage(new TextMessage(privateMessage.toString()));
                return;
            }
        }
        if(clients.values().contains(session) &&
                clientRequest.containsKey("disconnect")){
            String login = getLoginBySession(session);
            clients.remove(login);
            webChatService.invalidateHttpSession(login);
            session.sendMessage(new TextMessage("{\"disconnect\":\"yes\"}"));
            return;
        }
    }


    private boolean isBanned(WebSocketSession session){
        String login = getLoginBySession(session);
        if(login != null){
            HttpSession httpSession = CreateHttpSessionListener
                    .getSessionByLogin(login);
            User user = (User)httpSession.getAttribute("user");
            BlackList b = userService.getBanUser(user);
            System.out.println("BLACKLIST  " + b);
            if(b != null){
                return true;
            }
            System.out.println("----------------------");
        }
        return false;
    }

    private String getLoginBySession(WebSocketSession session){
        for(Map.Entry<String, WebSocketSession> item: clients.entrySet()){
            if(item.getValue() == session){
                return item.getKey();
            }
        }
        return null;
    }
}

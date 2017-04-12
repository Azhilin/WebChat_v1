package ua.training.listener;

import ua.training.domain.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adrien on 09.03.2017.
 */
@WebListener
public class CreateHttpSessionListener implements HttpSessionListener {
    private static Map<String, HttpSession> sessions = new HashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String sessionId = session.getId();
        sessions.put(sessionId, session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String sessionId = session.getId();
        sessions.remove(sessionId);
    }

    public static HttpSession getSessionById(String sessionId){
        return sessions.get(sessionId);
    }
    public static HttpSession getSessionByLogin(String login){
        for(HttpSession session: sessions.values()){
            User u = (User)session.getAttribute("user");
            if(u.getLogin().equals(login)){
                return session;
            }
        }
        return null;
    }
    public static void removeSession(String sessionId){
        sessions.remove(sessionId);
    }
}

package ua.training.service;

import org.springframework.stereotype.Service;
import ua.training.domain.User;
import ua.training.dto.UserDTO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 * Created by Adrien on 28.02.2017.
 */
@Service
public interface LoginService {
    public void registrationUser(UserDTO user);
    public User verifyUserLogin(String login, String password);

}

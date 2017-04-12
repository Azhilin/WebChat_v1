package ua.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.training.domain.Role;
import ua.training.domain.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Adrien on 23.03.2017.
 */
@Controller
public class AdminController {
    @RequestMapping(value= "/admin", method = RequestMethod.GET)
    public String getAdminPage(HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        if(user != null && user.getUserRole().getRole() == Role.ADMIN){
            return "admin";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/ban", method = RequestMethod.GET)
    public String getBanPage(){
        return "ban";
    }

}

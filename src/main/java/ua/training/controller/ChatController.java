package ua.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.training.domain.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Adrien on 02.03.2017.
 */
@Controller
public class ChatController {
    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public ModelAndView chatPage(HttpSession session){
        ModelAndView mv = new ModelAndView();
        User user = (User)session.getAttribute("user");
        if(user == null){
            mv.setViewName("redirect:/");
        } else {
            mv.setViewName("chat");
            mv.addObject("username", user.getName());
        }
        return mv;
    }
}

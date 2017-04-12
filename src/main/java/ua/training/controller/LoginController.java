package ua.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.training.domain.Role;
import ua.training.domain.User;
import ua.training.service.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Adrien on 28.02.2017.
 */
@Controller
@PropertySource("classpath:reg.properties")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private Environment environment;

    @RequestMapping(value = "/login",
            method = RequestMethod.GET,
            name = "loginLink")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
    @RequestMapping(value = "/login",
    method = RequestMethod.POST)
    public String verifyLogin(@RequestParam("login")String login,
                              @RequestParam("password")String password,
                              HttpSession session, HttpServletRequest request,
                              HttpServletResponse response){
        User user = loginService.verifyUserLogin(login, password);
        session.setAttribute("user", user);

        System.out.println("session = " + session.getAttribute("user"));
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("JSESSIONID")){
                cookie.setHttpOnly(false);
                response.addCookie(cookie);
                break;
            }
        }
        if(user.getBlackList() != null) {
            return "redirect:ban";
        }
        if(user.getUserRole().getRole() == Role.USER){
            session.setAttribute("sockurl",
                    environment.getProperty("reg.sockurl"));
            return "redirect:chat";
        }
        if(user.getUserRole().getRole() == Role.ADMIN){
            session.setAttribute("usersURL", "/users");
            return "redirect:admin";
        }
        return "redirect:registration";
    }
}

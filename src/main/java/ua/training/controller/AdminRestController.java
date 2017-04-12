package ua.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.training.domain.Role;
import ua.training.domain.User;
import ua.training.dto.BanDTO;
import ua.training.dto.ResponseBanDTO;
import ua.training.dto.ResponseDTO;
import ua.training.dto.UserDTO;
import ua.training.service.UserService;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Adrien on 23.03.2017.
 */
@RestController
public class AdminRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getAllUsersExceptAdmins(HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");

        System.out.println("user = [" + (user == null) + "]");
        if(user == null || user.getUserRole().getRole() != Role.ADMIN){
            ResponseDTO ban = new ResponseDTO();
            ban.setAuth("no");
            return new ResponseEntity<ResponseDTO>(ban, HttpStatus.OK);
        }
        List<UserDTO> users = userService.getAllUsersWithoutAdmins();
        ResponseDTO ban = new ResponseDTO();
        ban.setAuth("yes");
        List<BanDTO> bans = new ArrayList<>();
        for(UserDTO u: users){
            BanDTO banUser = new BanDTO();
            banUser.setUserId(u.getId());
            banUser.setLogin(u.getLogin());
            if(u.isBanned()){
                ResponseEntity<ResponseBanDTO> removeFromBanList =
                        methodOn(AdminRestController.class)
                                .deleteFromBanList(u.getId(),
                                        httpSession);
                Link link = linkTo(removeFromBanList).withRel("remove");
                banUser.add(link);
            } else {
                ResponseEntity<ResponseBanDTO> addToList =
                        methodOn(AdminRestController.class)
                                .addToBanList(u, httpSession);
                Link link = linkTo(addToList).withRel("add");
                banUser.add(link);
            }
            bans.add(banUser);
        }
        ban.setUsers(bans);
        return new ResponseEntity<>(ban, HttpStatus.OK);
    }

    @RequestMapping(value = "/ban/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseBanDTO> addToBanList(@RequestBody UserDTO userDTO,
                                                       HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        if(user == null || user.getUserRole().getRole() != Role.ADMIN){
            ResponseBanDTO ban = new ResponseBanDTO();
            ban.setAuth("no");
            return new ResponseEntity<ResponseBanDTO>(ban, HttpStatus.OK);
        }
        userService.addUserToBanList(userDTO);
        ResponseBanDTO responseBanDTO = new ResponseBanDTO();
        responseBanDTO.setSuccess("yes");
        return new ResponseEntity<ResponseBanDTO>(responseBanDTO,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/ban/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseBanDTO> deleteFromBanList(@PathVariable("id")Long id,
                                                            HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        if(user == null || user.getUserRole().getRole() != Role.ADMIN){
            ResponseBanDTO ban = new ResponseBanDTO();
            ban.setAuth("no");
            return new ResponseEntity<ResponseBanDTO>(ban, HttpStatus.OK);
        }
        userService.deleteUserFromBanList(id);
        ResponseBanDTO responseBanDTO = new ResponseBanDTO();
        responseBanDTO.setSuccess("yes");
        return new ResponseEntity<ResponseBanDTO>(responseBanDTO,
                HttpStatus.OK);
    }
}

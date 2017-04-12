package ua.training.service;

import ua.training.domain.BlackList;
import ua.training.domain.User;
import ua.training.dto.UserDTO;

import java.util.List;

/**
 * Created by Adrien on 23.03.2017.
 */
public interface UserService {
    List<UserDTO> getAllUsersWithoutAdmins();
    void addUserToBanList(UserDTO userDTO);
    void deleteUserFromBanList(Long id);
    BlackList getBanUser(User user);
}

package ua.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.domain.BlackList;
import ua.training.domain.Role;
import ua.training.domain.User;
import ua.training.dto.UserDTO;
import ua.training.exception.BanUserException;
import ua.training.repository.BlackListRepository;
import ua.training.repository.UserRepository;
import ua.training.service.UserService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by Adrien on 28.03.2017.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BlackListRepository blackListRepository;
    private ResourceBundleMessageSource bean;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BlackListRepository blackListRepository){
        this.userRepository = userRepository;
        this.blackListRepository = blackListRepository;
        bean = new ResourceBundleMessageSource();
        bean.setBasename("reg");
    }

    @Override
    public List<UserDTO> getAllUsersWithoutAdmins() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .filter(user -> user.getUserRole().getRole() != Role.ADMIN)
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setName(user.getName());
                    userDTO.setLogin(user.getLogin());
                    userDTO.setId(user.getId());
                    if(user.getBlackList() != null){
                        userDTO.setBanned(true);
                    } else {
                        userDTO.setBanned(false);
                    }
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addUserToBanList(UserDTO userDTO) {
        try {
            blackListRepository.addUserToBlackList(userDTO);
        } catch (Exception e){
            throw new BanUserException(bean.getMessage("reg.addBanUserError", null, Locale.ENGLISH));
        }
    }

    @Override
    public void deleteUserFromBanList(Long id) {
        try{
            blackListRepository.removeUserFromBlackList(id);
        } catch (Exception e){
            throw new BanUserException(bean.getMessage("reg.removeBanUserError", null, Locale.ENGLISH));
        }
    }

    @Override
    @Transactional
    public BlackList getBanUser(User user){
        return blackListRepository.getBanUserById(user.getId());
    }
}

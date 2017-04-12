package ua.training.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.training.domain.BlackList;
import ua.training.domain.User;
import ua.training.dto.UserDTO;
import ua.training.repository.BlackListRepositoryCustom;
import ua.training.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Adrien on 28.03.2017.
 */
@Component
public class BlackListRepositoryImpl implements BlackListRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void addUserToBlackList(UserDTO userDTO) {
        User user = userRepository.getUserByLogin(userDTO.getLogin());
        BlackList blackList = new BlackList();
        blackList.setUser(user);
        entityManager.persist(blackList);
    }

    @Override
    @Transactional
    public void removeUserFromBlackList(Long id) {
        User user = userRepository.findOne(id);
        BlackList blackList = user.getBlackList();
        entityManager.createQuery("DELETE FROM ua.training.domain.BlackList b " +
                "WHERE b.id = :id")
                .setParameter("id", blackList.getId())
                .executeUpdate();
    }


}

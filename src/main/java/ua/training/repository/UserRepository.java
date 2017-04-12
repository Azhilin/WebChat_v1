package ua.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.training.domain.User;

/**
 * Created by Adrien on 28.02.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM ua.training.domain.User user " +
            "WHERE user.login= :login")
    User getUserByLogin(@Param("login")String login);
}

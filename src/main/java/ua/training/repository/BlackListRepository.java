package ua.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.training.domain.BlackList;

/**
 * Created by Adrien on 28.03.2017.
 */
@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Long>,
     BlackListRepositoryCustom{

    @Query("SELECT b FROM ua.training.domain.BlackList b "
    + "WHERE b.user.id = :id")
    BlackList getBanUserById(@Param("id")Long id);
}

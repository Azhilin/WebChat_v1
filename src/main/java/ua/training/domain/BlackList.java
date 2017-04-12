package ua.training.domain;

import javax.persistence.*;

/**
 * Created by Adrien on 28.02.2017.
 */
@Entity(name = "black_list")
public class BlackList {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package ua.training.dto;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Adrien on 23.03.2017.
 */
public class BanDTO extends ResourceSupport {
    private String login;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

package ua.training.domain;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.util.List;

/**
 * Created by Adrien on 28.02.2017.
 */
@Entity(name ="users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name ="name")
    private String name;
    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserRole userRole;
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> receiveMessage;
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sendMessage;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private BlackList blackList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<Message> getReceiveMessage() {
        return receiveMessage;
    }

    public void setReceiveMessage(List<Message> receiveMessage) {
        this.receiveMessage = receiveMessage;
    }

    public List<Message> getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(List<Message> sendMessage) {
        this.sendMessage = sendMessage;
    }

    public BlackList getBlackList() {
        return blackList;
    }

    public void setBlackList(BlackList blackList) {
        this.blackList = blackList;
    }
}

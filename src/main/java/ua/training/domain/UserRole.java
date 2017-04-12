package ua.training.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Adrien on 28.02.2017.
 */
@Entity(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", unique = true)
    private Role role;
    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

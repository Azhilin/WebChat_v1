package ua.training.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Adrien on 28.02.2017.
 */
@Entity(name = "messages")
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "body")
    private String body;
    @Temporal(TemporalType.DATE)
    private Date date = new Date();
    @ManyToOne(fetch = FetchType.EAGER)
    private User sender;
    @ManyToOne(fetch = FetchType.EAGER)
    private User receiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}

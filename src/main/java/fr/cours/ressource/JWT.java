package fr.cours.ressource;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class JWT implements Serializable {

    private static final long serialVersionUID = 5L;
    @Column
    private String token;

    @OneToOne
    @Id
    private User user;


    public JWT() {
    }

    public JWT(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

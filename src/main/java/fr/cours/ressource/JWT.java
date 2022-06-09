package fr.cours.ressource;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class JWT implements Serializable {

    private static final long serialVersionUID = 5L;
    @Column(columnDefinition = "TEXT")
    private String token;

    @OneToOne
    @Id
    private UserMe userMe;


    public JWT() {
    }

    public JWT(UserMe userMe, String token) {
        this.userMe = userMe;
        this.token = token;
    }

    public UserMe getUser() {
        return userMe;
    }

    public void setUser(UserMe userMe) {
        this.userMe = userMe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

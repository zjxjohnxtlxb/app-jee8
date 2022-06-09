package fr.cours.ressource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.cours.utils.Helper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column
    private String name;
    @Column(unique = true, nullable = false)
    @NotNull
    private String email;
    @Transient
    private String password;
    @Column(nullable = false)
    @JsonIgnore
    @NotNull
    private String passwordHash;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
        this.setPasswordHash(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }


    private void setPasswordHash(String password) {
        this.passwordHash = Helper.hashUtil(password);
    }

    public String toString() {
        return "Compte email : " + this.getEmail() +
                "\nUser name : " + this.getName();
    }

}

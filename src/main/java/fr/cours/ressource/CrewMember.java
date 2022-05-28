/*
 * @Date: 2022-05-19 10:26:41
 * @LastEditors: Junxi ZHANG
 * @LastEditTime: 2022-05-19 11:36:44
 * @FilePath: /app-jee8/src/main/java/fr/cours/ressource/CrewMember.java
 */
package fr.cours.ressource;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CrewMember implements Serializable {
    private static final long serialVersionUID = 2L;
    @Column(unique = true)
    private String name;
    @Column
    private String job;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public CrewMember() {

    }

    public CrewMember(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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
}

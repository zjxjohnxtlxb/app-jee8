package fr.cours.ressource;

/*
 * @Date: 2022-05-19 09:21:31
 * @LastEditors: Junxi ZHANG
 * @LastEditTime: 2022-05-19 09:21:31
 * @FilePath: /app-jee8/src/main/java/fr/cours/ressource/TestJsonRessource.java
 */

import java.io.Serializable;

public class TestJsonRessource implements Serializable {
    private static final long serialVersionUID = 4254739092263227552L;
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
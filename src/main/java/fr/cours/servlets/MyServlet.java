/*
 * @Date: 2022-05-17 21:15:54
 * @LastEditors: Junxi ZHANG
 * @LastEditTime: 2022-05-19 09:04:41
 * @FilePath: /app-jee8/src/main/java/fr/cours/servlets/MyServlet.java
 */
package fr.cours.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Random;

@WebServlet("/helloServlet")
public class MyServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int int_random = new Random().nextInt();
        response.getWriter().println(String.format("Hello: %s", int_random));
    }
}
/*
 * @Date: 2022-05-19 08:48:45
 * @LastEditors: Junxi ZHANG
 * @LastEditTime: 2022-05-19 08:52:45
 * @FilePath: /app-jee8/src/main/java/fr/cours/servlets/DemoServlet.java
 */
package fr.cours.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/demo.html")
public class DemoServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
                response.setContentType("text/html");
                var out = response.getWriter();
        
                // send HTML page to client
                out.println("<html>");
                out.println("<head><title>Demo Servlet</title></head>");
                out.println("<body>Page html générée via une servlet</body>");
                out.println("</html>");
    }
}
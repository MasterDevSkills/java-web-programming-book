package com.bazlur.converter.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/home", loadOnStartup = 0)
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(
                "<html>" +
                        "<body>" +
                        "<h1> Hello, world! </h1>" +
                        "</body>" +
                        "</html>"
        );
    }
}

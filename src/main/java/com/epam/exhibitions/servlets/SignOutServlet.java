package com.epam.exhibitions.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "SignOutServlet", value = "/SignOutServlet")
public class SignOutServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(LoginServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("User: "+req.getSession().getAttribute("username")+" signed out!");
        req.getSession().invalidate();
        resp.sendRedirect("index.jsp");
    }
}

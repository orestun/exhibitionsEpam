package com.epam.exhibitions.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ChangeLanguage", value = "/ChangeLanguage")
public class ChangeLanguage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String language = request.getParameter("language");
        request.getSession().setAttribute("language",language);
        response.sendRedirect((String) request.getSession().getAttribute("responsePage"));
    }

}

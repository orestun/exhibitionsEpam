package com.epam.exhibitions.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ViewExhibition", value = "/ViewExhibition")
public class ViewExhibition extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageId = request.getParameter("page");
        request.getSession().setAttribute("page",pageId);
        response.sendRedirect("exhibitions.jsp");
    }

}

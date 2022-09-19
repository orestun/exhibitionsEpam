package com.epam.exhibitions.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GetIdForUpdate", value = "/GetIdForUpdate")
public class GetIdForUpdate extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.getSession().setAttribute("idForUpdate",id);
        response.sendRedirect("updateExhibitions.jsp");
    }
}

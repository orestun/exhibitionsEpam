package com.epam.exhibitions.servlets;

import com.epam.exhibitions.db.entity.ExhibitionsBasket;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "plusTicket", value = "/plusTicket")
public class PlusTicket extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        List<ExhibitionsBasket> usersBasket = (List<ExhibitionsBasket>) request.getSession().getAttribute("listUsersBasket");
        for (ExhibitionsBasket exhibitions:usersBasket){
            if(exhibitions.getExhibitions().getId_exhibition()==Integer.parseInt(id)){
                exhibitions.setNumber(exhibitions.getNumber()+1);
            }
        }
        response.sendRedirect("basket.jsp");
    }

}

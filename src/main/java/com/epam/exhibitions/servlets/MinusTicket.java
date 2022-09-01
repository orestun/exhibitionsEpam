package com.epam.exhibitions.servlets;

import com.epam.exhibitions.servlets.db.entity.ExhibitionsBasket;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "minusTicket", value = "/minusTicket")
public class MinusTicket extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        List<ExhibitionsBasket> usersBasket = (List<ExhibitionsBasket>) request.getSession().getAttribute("listUsersBasket");
        for (ExhibitionsBasket exhibitions:usersBasket){
            if(exhibitions.getExhibitions().getId_exhibition()==Integer.parseInt(id)){
                if(exhibitions.getNumber()>1){
                    exhibitions.setNumber(exhibitions.getNumber()-1);
                }
            }
        }
        response.sendRedirect("basket.jsp");
    }
}

package com.epam.exhibitions.servlets;

import com.epam.exhibitions.db.entity.ExhibitionsBasket;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "toClean", value = "/toClean")
public class ToClean extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        List<ExhibitionsBasket> usersBasket = (List<ExhibitionsBasket>) request.getSession().getAttribute("listUsersBasket");
        List<ExhibitionsBasket> usersBasketNew = new ArrayList<>();
        for (ExhibitionsBasket exhibitions:usersBasket){
            if(exhibitions.getExhibitions().getId_exhibition()!=Integer.parseInt(id)){
                usersBasketNew.add(exhibitions);
            }
        }
        if(usersBasketNew.isEmpty()){
            usersBasketNew=null;
        }
        request.getSession().setAttribute("listUsersBasket",usersBasketNew);
        response.sendRedirect("basket.jsp");
    }
}

package com.epam.exhibitions.servlets;

import com.epam.exhibitions.db.ExhibitionsDAOImpl;
import com.epam.exhibitions.db.entity.ExhibitionsBasket;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "toAddInBasket", value = "/toAddInBasket")
public class ToAddInBasket extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        List<ExhibitionsBasket> usersBasket = (List<ExhibitionsBasket>) request.getSession().getAttribute("listUsersBasket");
        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();

        if(usersBasket==null){
            usersBasket = new ArrayList<>();
            usersBasket.add(new ExhibitionsBasket(exhibitionsDAO.getExhibitionById(Integer.parseInt(id)),1));
            request.getSession().setAttribute("listUsersBasket",usersBasket);
        }else{
            boolean repeat=false;
            for (ExhibitionsBasket exhibitions:usersBasket){
                if(exhibitions.getExhibitions().getId_exhibition()==Integer.parseInt(id)){
                    repeat = true;
                    exhibitions.setNumber(exhibitions.getNumber()+1);
                }
            }
            if(!repeat){
                usersBasket.add(new ExhibitionsBasket(exhibitionsDAO.getExhibitionById(Integer.parseInt(id)),1));
                request.getSession().setAttribute("listUsersBasket",usersBasket);
            }
        }
        if(request.getSession().getAttribute("sorting")!=null){
            request.getSession().setAttribute("sortingReturn","true");
            request.getSession().removeAttribute("sorting");
        }
        response.sendRedirect("exhibitions.jsp");
    }

}

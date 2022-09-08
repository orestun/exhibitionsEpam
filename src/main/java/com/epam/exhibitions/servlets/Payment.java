package com.epam.exhibitions.servlets;

import com.epam.exhibitions.db.TicketsDAOImpl;
import com.epam.exhibitions.db.UserDAOImpl;
import com.epam.exhibitions.db.entity.ExhibitionsBasket;
import com.epam.exhibitions.db.entity.Ticket;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "Payment", value = "/Payment")
public class Payment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        UserDAOImpl userDAO = UserDAOImpl.getInstance();
        BigDecimal theSumInBasket = (BigDecimal) request.getSession().getAttribute("theSumInBasket");
        BigDecimal usersWallet = (BigDecimal) request.getSession().getAttribute("wallet");
        if(usersWallet.compareTo(theSumInBasket) ==-1){
            request.getSession().setAttribute("balanceError","You have not enough money to buy it!");
            response.sendRedirect("basket.jsp");
        }else {
            userDAO.withdrawCashFromWallet(username,theSumInBasket);
            request.getSession().setAttribute("wallet",userDAO.getWallet(username));
            TicketsDAOImpl ticketsDAO = TicketsDAOImpl.getInstance();
            List<ExhibitionsBasket> exhibitionsBasketList = (List<ExhibitionsBasket>) request.getSession().getAttribute("listUsersBasket");
            for(ExhibitionsBasket exhibitionsBasket:exhibitionsBasketList){
                for(int i = 0;i<exhibitionsBasket.getNumber();i++){
                    Ticket ticket = new Ticket(username,exhibitionsBasket.getExhibitions().getId_exhibition());
                    ticketsDAO.toAddTicket(ticket);
                }
            }
            request.getSession().setAttribute("listUsersBasket",null);
            response.sendRedirect("basket.jsp");
        }
    }
}

package com.epam.exhibitions.servlets;

import com.epam.exhibitions.db.UserDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "RefillBalanceServlet", value = "/RefillBalanceServlet")
public class RefillBalanceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BigDecimal wallet = new BigDecimal(request.getParameter("paid"));
        UserDAOImpl userDAO = UserDAOImpl.getInstance();
        userDAO.addCashToWallet((String) request.getSession().getAttribute("username"),wallet);
        request.getSession().setAttribute("wallet", userDAO.getWallet((String) request.getSession().getAttribute("username")));
        response.sendRedirect("index.jsp");
    }
}

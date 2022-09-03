package com.epam.exhibitions.servlets;

import com.epam.exhibitions.servlets.db.DAO.TicketsDAO;
import com.epam.exhibitions.servlets.db.ExhibitionsDAOImpl;
import com.epam.exhibitions.servlets.db.ExhibitonHallsDAOImpl;
import com.epam.exhibitions.servlets.db.TicketsDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DeleteExhibition", value = "/DeleteExhibition")
public class DeleteExhibition extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
        exhibitionsDAO.deleteExhibition(id);
        ExhibitonHallsDAOImpl exhibitionHallsDAO = ExhibitonHallsDAOImpl.getInstance();
        exhibitionHallsDAO.deleteHalls(id);
        TicketsDAOImpl ticketsDAO = TicketsDAOImpl.getInstance();
        ticketsDAO.deleteTickets(id);
        response.sendRedirect("exhibitions.jsp");
    }
}

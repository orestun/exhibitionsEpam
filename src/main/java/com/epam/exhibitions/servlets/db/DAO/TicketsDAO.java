package com.epam.exhibitions.servlets.db.DAO;

import com.epam.exhibitions.servlets.db.entity.Ticket;

import java.util.List;


public interface TicketsDAO {
    boolean toAddTicket(Ticket ticket);

    List<Ticket> toGetTicketsForUser(String username);
    boolean deleteTickets(int id);
    int numberOfVisitors(int id);
}

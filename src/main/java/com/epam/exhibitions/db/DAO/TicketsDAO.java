package com.epam.exhibitions.db.DAO;

import com.epam.exhibitions.db.entity.Ticket;

import java.util.List;


public interface TicketsDAO {
    boolean toAddTicket(Ticket ticket);

    List<Ticket> toGetTicketsForUser(String username);
    boolean deleteTickets(int id);
    int numberOfVisitors(int id);
}

package com.epam.exhibitions.servlets.db;

import com.epam.exhibitions.servlets.db.DAO.TicketsDAO;
import com.epam.exhibitions.servlets.db.entity.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TicketsDAOImpl implements TicketsDAO {

    private static TicketsDAOImpl instance;

    public static TicketsDAOImpl getInstance() {
        if(instance==null){
            instance=new TicketsDAOImpl();
        }
        return instance;
    }

    static ResourceBundle resource = ResourceBundle.getBundle("database");
    private static final String CONNECTION_URL = resource.getString("CONNECTION_URL");
    private static final String USER = resource.getString("USER");
    private static final String PASSWORD = resource.getString("PASSWORD");
    private static Connection con;

    @Override
    public boolean toAddTicket(Ticket ticket) {
        String query = "INSERT INTO tickets (username,id_exhibition) VALUES(?,?)";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,ticket.getUsername());
            preparedStatement.setInt(2,ticket.getIdExhibition());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> toGetTicketsForUser(String username) {
        String query = "SELECT * FROM tickets WHERE username=?";
        List<Ticket> ticketList = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getString(2),rs.getInt(1),rs.getInt(3));
                ticketList.add(ticket);
            }
            return ticketList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        TicketsDAOImpl ticketsDAO = TicketsDAOImpl.getInstance();
        ticketsDAO.toGetTicketsForUser("orestun");
        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
        for (Ticket ticket:ticketsDAO.toGetTicketsForUser("orestun")) {
            System.out.println(ticket.getIdTicket());
        }
    }
}

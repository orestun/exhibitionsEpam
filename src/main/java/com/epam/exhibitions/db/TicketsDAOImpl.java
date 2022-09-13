package com.epam.exhibitions.db;

import com.epam.exhibitions.db.DAO.TicketsDAO;
import com.epam.exhibitions.db.connectionPool.BasicConnectionPool;
import com.epam.exhibitions.db.connectionPool.ConnectionPool;
import com.epam.exhibitions.db.entity.Ticket;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TicketsDAOImpl implements TicketsDAO {

    final static Logger logger = Logger.getLogger(TicketsDAOImpl.class);
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

    private static final ConnectionPool connectionPool;

    static {
        try {
            connectionPool = BasicConnectionPool.create(CONNECTION_URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean toAddTicket(Ticket ticket) {
        String query = "INSERT INTO tickets (username,id_exhibition) VALUES(?,?)";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,ticket.getUsername());
            preparedStatement.setInt(2,ticket.getIdExhibition());
            preparedStatement.executeUpdate();
            logger.info("User: "+ticket.getUsername()+" bought a new ticket");
            connectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> toGetTicketsForUser(String username) {
        String query = "SELECT * FROM tickets WHERE username=?";
        List<Ticket> ticketList = new ArrayList<>();
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();
            connectionPool.releaseConnection(connection);
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getString(2),rs.getInt(1),rs.getInt(3));
                ticketList.add(ticket);
            }
            return ticketList;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteTickets(int id) {
        String query = "DELETE FROM tickets WHERE id_exhibition=?";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            connectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int numberOfVisitors(int id) {
        String query = "SELECT * FROM tickets WHERE id_exhibition=?";
        try{
            int number= 0;
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            connectionPool.releaseConnection(connection);
            while (rs.next()){
                number++;
            }
            return number;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }
}

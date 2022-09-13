package com.epam.exhibitions.db;

import com.epam.exhibitions.db.connectionPool.BasicConnectionPool;
import com.epam.exhibitions.db.connectionPool.ConnectionPool;
import com.epam.exhibitions.db.entity.Exhibitions;
import com.epam.exhibitions.db.DAO.ExhibitionsDAO;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExhibitionsDAOImpl implements ExhibitionsDAO {
    private static ExhibitionsDAOImpl instance;

    final static Logger logger = Logger.getLogger(ExhibitionsDAOImpl.class);

    public ExhibitionsDAOImpl() {
    }

    public static ExhibitionsDAOImpl getInstance() {
        if(instance==null){
            instance=new ExhibitionsDAOImpl();
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
    public boolean addExhibition(Exhibitions exhibitions) {
        String query = "INSERT INTO exhibition (nameUA, nameEN,themeUA, themeEN,date_from, date_to, working_time_from, working_time_to, price) VALUES (?,?,?,?,?,?,?,?,?)";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, exhibitions.getNameUA());
            preparedStatement.setString(2, exhibitions.getNameEN());
            preparedStatement.setString(3, exhibitions.getThemeUA());
            preparedStatement.setString(4, exhibitions.getThemaEN());
            preparedStatement.setDate(5,exhibitions.getDate_from());
            preparedStatement.setDate(6,exhibitions.getDate_to());
            preparedStatement.setTime(7,exhibitions.getWorking_time_from());
            preparedStatement.setTime(8,exhibitions.getWorking_time_to());
            preparedStatement.setBigDecimal(9,exhibitions.getPrice());
            preparedStatement.executeUpdate();
            logger.info("exhibition with id: "+exhibitions.getId_exhibition()+" is successfully add in database");
            connectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteExhibition(int id_exhibition) {
        String query = "DELETE FROM exhibition WHERE id_exhibition=?";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id_exhibition);
            preparedStatement.executeUpdate();
            logger.info("exhibition with id: "+id_exhibition+" is successfully deleted from the database");
            connectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getIdByNames(String nameUA, String nameEN) {
        String query = "SELECT id_exhibition FROM exhibition WHERE nameUA=? AND nameEN=?";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,nameUA);
            preparedStatement.setString(2,nameEN);
            ResultSet rs = preparedStatement.executeQuery();
            connectionPool.releaseConnection(connection);
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public boolean duplicateNames(String nameUA, String nameEN) {
        String query = "SELECT nameUA,nameEN FROM exhibition WHERE nameUA=? AND nameEN=?";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,nameUA);
            preparedStatement.setString(2,nameEN);
            ResultSet rs = preparedStatement.executeQuery();
            connectionPool.releaseConnection(connection);
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addImage(String image, int id_exhibition) {
        String query = "UPDATE exhibition SET image=? WHERE id_exhibition=?";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,image);
            preparedStatement.setInt(2,id_exhibition);
            preparedStatement.executeUpdate();
            connectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Exhibitions> exhibitionsCommonList() {
        String query = "SELECT * FROM exhibition";
        List<Exhibitions> exhibitions = new ArrayList<>();
        try{
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                Exhibitions exhibitions1 = new Exhibitions(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6),rs.getDate(7),rs.getTime(8),rs.getTime(9),rs.getBigDecimal(10));
                exhibitions1.setId_exhibition(rs.getInt(1));
                exhibitions1.setImage(rs.getString(11));
                exhibitions.add(exhibitions1);
            }
            connectionPool.releaseConnection(connection);
            logger.info("List with all exhibitions is returned!");
            return exhibitions;
        } catch (SQLException e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<Exhibitions> exhibitionsSorting(String name,  Date dateFrom,Date dateTo, BigDecimal priceFrom, BigDecimal priceTo) {
        String query = "SELECT * FROM exhibition WHERE price BETWEEN ? AND ? \n" +
                "AND (date_from <= ? AND ? <= date_to)\n" +
                "AND (nameUA like ? OR nameEN like ?)";
        List<Exhibitions> exhibitionsList = new ArrayList<>();
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1,priceFrom);
            preparedStatement.setBigDecimal(2,priceTo);
            preparedStatement.setDate(3,dateFrom);
            preparedStatement.setDate(4,dateTo);
            preparedStatement.setString(5,"%"+name+"%");
            preparedStatement.setString(6,"%"+name+"%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Exhibitions exhibitions = new Exhibitions(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6),rs.getDate(7),rs.getTime(8),rs.getTime(9),rs.getBigDecimal(10));
                exhibitions.setId_exhibition(rs.getInt(1));
                exhibitions.setImage(rs.getString(11));
                connectionPool.releaseConnection(connection);
                exhibitionsList.add(exhibitions);
            }
            logger.info("Sorted list with exhibitions is returned");
            return exhibitionsList;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public Exhibitions getExhibitionById(int id_exhibition){
        String query = "SELECT * FROM exhibition WHERE id_exhibition=?";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id_exhibition);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Exhibitions exhibitions = new Exhibitions(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6),rs.getDate(7),rs.getTime(8),rs.getTime(9),rs.getBigDecimal(10));
                exhibitions.setId_exhibition(rs.getInt(1));
                exhibitions.setImage(rs.getString(11));
                connectionPool.releaseConnection(connection);
                return exhibitions;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public BigDecimal minPrice() {
        String query = "SELECT price FROM exhibition";
        BigDecimal min = new BigDecimal(0);
        try{
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                min = rs.getBigDecimal(1);
            }
            while(rs.next()){
                if(min.compareTo(rs.getBigDecimal(1)) > 0){
                    min = rs.getBigDecimal(1);
                }
            }
            connectionPool.releaseConnection(connection);
            return min;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal maxPrice() {
        String query = "SELECT price FROM exhibition";
        BigDecimal max = new BigDecimal(0);
        try{
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                max = rs.getBigDecimal(1);
            }
            while(rs.next()){
                if(max.compareTo(rs.getBigDecimal(1)) < 0){
                    max = rs.getBigDecimal(1);
                }
            }
            connectionPool.releaseConnection(connection);
            return max;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        connectionPool.getConnection();
        connectionPool.getConnection();
        System.out.println(connectionPool);
    }

}
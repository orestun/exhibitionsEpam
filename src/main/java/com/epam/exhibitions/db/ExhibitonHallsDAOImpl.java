package com.epam.exhibitions.db;

import com.epam.exhibitions.db.DAO.ExhibitonHallsDAO;
import com.epam.exhibitions.db.connectionPool.BasicConnectionPool;
import com.epam.exhibitions.db.connectionPool.ConnectionPool;
import com.epam.exhibitions.db.entity.ExhibitionHalls;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ResourceBundle;
public class ExhibitonHallsDAOImpl implements ExhibitonHallsDAO {

    final static Logger logger = Logger.getLogger(ExhibitonHallsDAOImpl.class);
    private static ExhibitonHallsDAOImpl instance;

    public static ExhibitonHallsDAOImpl getInstance() {
        if(instance==null){
            instance=new ExhibitonHallsDAOImpl();
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
    public boolean addHalls(ExhibitionHalls exhibitionHalls) {
        String query = "INSERT INTO exhibition_halls (id_exhibition,HALL1,HALL2,HALL3,HALL4,HALL5) VALUES (?,?,?,?,?,?)";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,exhibitionHalls.getId_exhibition());
            preparedStatement.setBoolean(2, exhibitionHalls.isHALL1());
            preparedStatement.setBoolean(3, exhibitionHalls.isHALL2());
            preparedStatement.setBoolean(4, exhibitionHalls.isHALL3());
            preparedStatement.setBoolean(5, exhibitionHalls.isHALL4());
            preparedStatement.setBoolean(6, exhibitionHalls.isHALL5());
            preparedStatement.executeUpdate();
            connectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteHalls(int id) {
        String query = "DELETE FROM exhibition_halls WHERE id_exhibition=?";
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
    public String getHalls(int id_exhibition) {
        String query="SELECT * FROM exhibition_halls WHERE id_exhibition=?";
        String halls = "";
        try{
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id_exhibition);
            ResultSet rs = preparedStatement.executeQuery();
            connectionPool.releaseConnection(connection);
            if(rs.next()){
                if(rs.getBoolean(2)){
                    halls+=" №1";
                }
                if (rs.getBoolean(3)) {
                    halls+=" №2";
                }
                if (rs.getBoolean(4)) {
                    halls+=" №3";
                }
                if (rs.getBoolean(5)) {
                    halls+=" №4";
                }
                if (rs.getBoolean(6)) {
                    halls+=" №5";
                }
                return halls+=";";
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        ExhibitonHallsDAOImpl exhibitonHallsDAO = ExhibitonHallsDAOImpl.getInstance();
        System.out.println(exhibitonHallsDAO.getHalls(8));
    }

}

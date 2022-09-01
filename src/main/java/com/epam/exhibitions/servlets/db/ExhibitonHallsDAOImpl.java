package com.epam.exhibitions.servlets.db;

import com.epam.exhibitions.servlets.db.DAO.ExhibitonHallsDAO;
import com.epam.exhibitions.servlets.db.entity.ExhibitionHalls;
import com.epam.exhibitions.servlets.db.entity.Exhibitions;

import java.sql.*;
import java.util.ResourceBundle;

public class ExhibitonHallsDAOImpl implements ExhibitonHallsDAO {

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
    private static Connection con;

    public void getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            System.out.println("well done");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ExhibitonHallsDAOImpl exhibitonHallsDAO = ExhibitonHallsDAOImpl.getInstance();
        exhibitonHallsDAO.getConnection();
        System.out.println(exhibitonHallsDAO.getHalls(8));
    }

    @Override
    public boolean addHalls(ExhibitionHalls exhibitionHalls) {
        String query = "INSERT INTO exhibition_halls (id_exhibition,HALL1,HALL2,HALL3,HALL4,HALL5) VALUES (?,?,?,?,?,?)";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,exhibitionHalls.getId_exhibition());
            preparedStatement.setBoolean(2, exhibitionHalls.isHALL1());
            preparedStatement.setBoolean(3, exhibitionHalls.isHALL2());
            preparedStatement.setBoolean(4, exhibitionHalls.isHALL3());
            preparedStatement.setBoolean(5, exhibitionHalls.isHALL4());
            preparedStatement.setBoolean(6, exhibitionHalls.isHALL5());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteHalls(ExhibitionHalls exhibitionHalls) {
        return false;
    }

    @Override
    public String getHalls(int id_exhibition) {
        String query="SELECT * FROM exhibition_halls WHERE id_exhibition=?";
        String halls = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,id_exhibition);
            ResultSet rs = preparedStatement.executeQuery();
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
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}

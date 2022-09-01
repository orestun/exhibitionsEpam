package com.epam.exhibitions.servlets.db;

import com.epam.exhibitions.servlets.db.DAO.ExhibitionsDAO;
import com.epam.exhibitions.servlets.db.entity.Exhibitions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExhibitionsDAOImpl implements ExhibitionsDAO {
    private static ExhibitionsDAOImpl instance;

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
    private static Connection con;


    @Override
    public boolean addExhibition(Exhibitions exhibitions) {
        String query = "INSERT INTO exhibition (nameUA, nameEN,themeUA, themeEN,date_from, date_to, working_time_from, working_time_to, price) VALUES (?,?,?,?,?,?,?,?,?)";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
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
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean deleteExhibition(int id_exhibition) {
        return false;
    }

    @Override
    public int getIdByNames(String nameUA, String nameEN) {
        String query = "SELECT id_exhibition FROM exhibition WHERE nameUA=? AND nameEN=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,nameUA);
            preparedStatement.setString(2,nameEN);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public boolean dublicateNames(String nameUA, String nameEN) {
        String query = "SELECT nameUA,nameEN FROM exhibition WHERE nameUA=? AND nameEN=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,nameUA);
            preparedStatement.setString(2,nameEN);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean addImage(String image, int id_exhibition) {
        String query = "UPDATE exhibition SET image=? WHERE id_exhibition=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,image);
            preparedStatement.setInt(2,id_exhibition);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Exhibitions> exhibitionsCommonList() {
        String query = "SELECT * FROM exhibition";
        List<Exhibitions> exhibitions = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                Exhibitions exhibitions1 = new Exhibitions(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6),rs.getDate(7),rs.getTime(8),rs.getTime(9),rs.getBigDecimal(10));
                exhibitions1.setId_exhibition(rs.getInt(1));
                exhibitions1.setImage(rs.getString(11));
                exhibitions.add(exhibitions1);
            }
            return exhibitions;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Exhibitions getExhibitionById(int id_exhibition){
        String query = "SELECT * FROM exhibition WHERE id_exhibition=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,id_exhibition);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Exhibitions exhibitions = new Exhibitions(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6),rs.getDate(7),rs.getTime(8),rs.getTime(9),rs.getBigDecimal(10));
                exhibitions.setId_exhibition(rs.getInt(1));
                exhibitions.setImage(rs.getString(11));
                return exhibitions;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
        System.out.println(exhibitionsDAO.getExhibitionById(5));
    }
}

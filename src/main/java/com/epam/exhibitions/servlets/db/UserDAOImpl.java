package com.epam.exhibitions.servlets.db;

import com.epam.exhibitions.servlets.db.DAO.UserDAO;
import com.epam.exhibitions.servlets.db.entity.User;
import com.epam.exhibitions.servlets.enums.UserRoles;
import com.mysql.jdbc.Driver;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ResourceBundle;

public class UserDAOImpl implements UserDAO {

    final static Logger logger = Logger.getLogger(UserDAOImpl.class);
    private static UserDAOImpl instance;
    public static UserDAOImpl getInstance() {
        if(instance==null){
            instance=new UserDAOImpl();
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
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    /*
    * The method take an object User, that have all filled fields
    * and insert this user into table 'User' in db.
    * If user is added successfully we will get TRUE.
    * */
    @Override
    public boolean addNewUser(User user) {
        String query = "INSERT INTO user (id_user_username, first_name, second_name, email, phone_number, country, password, wallet) VALUES(?,?,?,?,?,?,?,?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirth_name());
            preparedStatement.setString(3, user.getSecond_name());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhone_number());
            preparedStatement.setString(6, user.getCountry());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setBigDecimal(8, user.getWallet());
            preparedStatement.executeUpdate();
            logger.info("User: "+ user.getUsername()+" is successfully added in database");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    /*
    * The method checks whether there is a user with such a username in db.
    * If there is such user we will get TRUE,
    * otherwise we will get FALSE.
    * */
    @Override
    public boolean verifyUsername(String username) {
        String query = "SELECT id_user_username FROM user WHERE id_user_username=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    /*
     * The method checks whether there is a user with such email in db.
     * If there is such user we will get TRUE,
     * otherwise we will get FALSE.
     * */
    @Override
    public boolean verifyEmail(String email) {
        String query = "SELECT email FROM user WHERE email=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    /*
     * The method checks whether there is a user with such phone number in db.
     * If there is such user we will get TRUE,
     * otherwise we will get FALSE.
     * */
    @Override
    public boolean verifyPhoneNumber(String phone) {
        String query = "SELECT phone_number FROM user WHERE phone_number=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, phone);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    /*
     * The method take a username,
     * and delete this user from table 'User' in db.
     * If user is deleted successfully we will get TRUE.
     * */
    @Override
    public boolean deleteUser(String username) {
        String query = "delete FROM user WHERE id_user_username = ?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();
            logger.info("User: "+ username+" is successfully deleted from the database");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            try{
                con.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
    }

    /*
    * The method takes a username and gives a value of wallet
    * that belongs to this user.
    * */
    @Override
    public BigDecimal getWallet(String username) {
        String query = "SELECT wallet FROM user WHERE id_user_username=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();
            BigDecimal wallet;
            if(rs.next()){
                wallet = rs.getBigDecimal(1);
                rs.close();
                return wallet;
            }
            return new BigDecimal(-1);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            try{
                con.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
    }

    /*
    * The method takes the value of user`s wallet
    * and add it.
    * */
    @Override
    public boolean addCashToWallet(String username, BigDecimal cash) {
        BigDecimal currentBalance = this.getWallet(username);
        BigDecimal updatedBalance = currentBalance.add(cash);
        String query = "UPDATE user SET wallet=? WHERE id_user_username=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setBigDecimal(1,updatedBalance);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();
            logger.info("User: "+username+" successfully added +"+cash.toString()+" $ to his balance");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            try{
                con.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * The method takes the value of user`s wallet
     * and add it.
     * If user have no enough money, the method will return false.
    * */
    @Override
    public boolean withdrawCashFromWallet(String username, BigDecimal cash) {
        BigDecimal currentBalance = this.getWallet(username);
        BigDecimal updatedBalance = currentBalance.subtract(cash);
        if(updatedBalance.compareTo(new BigDecimal(0))<0){
            return false;
        }
        String query = "UPDATE user SET wallet=? WHERE id_user_username=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setBigDecimal(1,updatedBalance);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();
            logger.info("User: "+username+" successfully withdraw -"+cash.toString()+" $ from his balance");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            try{
                con.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
    }

    /*
    * The method return password by username, in order to log in
    * account.
    * */
    @Override
    public String getPassword(String username) {
        String query = "SELECT password FROM user WHERE id_user_username=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();
            String password = "";
            if(rs.next()){
                password = rs.getString(1);
            }
            return password;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        User user;
        String query = "SELECT * FROM user WHERE id_user_username=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                user = new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5), rs.getString(6),rs.getString(1),rs.getString(7),rs.getBigDecimal(8) );
                user.setUserRoles(UserRoles.valueOf(rs.getString(9)));
                return user;
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public String getRole(String username){
        String query = "SELECT role FROM user WHERE id_user_username=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,username);
            String role;
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                role= rs.getString(1);
                return role;
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            try{
                con.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        User user = new User("Orest","Uzhytchak","o@gmail.com","+380509112262","Ukraine","orestun","12345678",new BigDecimal(100000));
        UserDAOImpl userDAOImpl = com.epam.exhibitions.servlets.db.UserDAOImpl.getInstance();
        //System.out.println(userDAOImpl.addNewUser(user));
        System.out.println(userDAOImpl.getRole("orestun"));
    }

}

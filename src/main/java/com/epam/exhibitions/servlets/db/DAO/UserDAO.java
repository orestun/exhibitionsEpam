package com.epam.exhibitions.servlets.db.DAO;

import com.epam.exhibitions.servlets.db.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface UserDAO {
    boolean addNewUser(User user) throws SQLException;
    boolean verifyUsername(String username);
    boolean verifyEmail(String email);
    boolean verifyPhoneNumber(String phone);
    boolean deleteUser(String username);

    BigDecimal getWallet(String username);
    boolean addCashToWallet(String username, BigDecimal cash);
    boolean withdrawCashFromWallet(String username, BigDecimal cash);
    String getPassword(String username);

    User getUserByUsername(String username);
}

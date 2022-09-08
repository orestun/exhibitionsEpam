package com.epam.exhibitions.db.entity;

import com.epam.exhibitions.enums.UserRoles;

import java.math.BigDecimal;

public class User {
    private String firth_name;
    private String second_name;
    private String email;
    private String phone_number;
    private String country;
    private String username;
    private String password;
    private BigDecimal wallet;
    private UserRoles userRoles;


    public User(String firth_name, String second_name, String email, String phone_number, String country, String username, String password, BigDecimal wallet) {
        this.firth_name = firth_name;
        this.second_name = second_name;
        this.email = email;
        this.phone_number = phone_number;
        this.country = country;
        this.username = username;
        this.password = password;
        this.wallet = wallet;
        this.userRoles = UserRoles.USER;
    }


    public String getFirth_name() {
        return firth_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getCountry() {
        return country;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getWallet() {
        return wallet;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "User{" +
                "firth_name='" + firth_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", country='" + country + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", wallet=" + wallet +
                ", userRoles=" + userRoles +
                '}';
    }
}

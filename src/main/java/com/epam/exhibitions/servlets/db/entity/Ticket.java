package com.epam.exhibitions.servlets.db.entity;

public class Ticket {
    private String username;
    private int idExhibition;
    private int idTicket;


    public Ticket(String username, int idExhibition, int idTicket) {
        this.username = username;
        this.idExhibition = idExhibition;
        this.idTicket = idTicket;
    }

    public Ticket(String username, int idExhibition) {
        this.username = username;
        this.idExhibition = idExhibition;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdExhibition() {
        return idExhibition;
    }

    public void setIdExhibition(int idExhibition) {
        this.idExhibition = idExhibition;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "username='" + username + '\'' +
                ", idExhibition=" + idExhibition +
                ", idTicket=" + idTicket +
                '}';
    }
}

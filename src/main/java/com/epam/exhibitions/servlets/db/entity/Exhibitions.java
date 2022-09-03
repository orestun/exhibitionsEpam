package com.epam.exhibitions.servlets.db.entity;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Date;
import java.util.List;

public class Exhibitions implements Comparable<Exhibitions>{

    private int id_exhibition;
    private String nameUA;
    private String nameEN;
    private String themeUA;
    private String themaEN;
    private List<String> halls;
    private Date date_from;
    private Date date_to;
    private Time working_time_from;
    private Time working_time_to;
    private BigDecimal price;

    private String image;

    public Exhibitions(String nameUA, String nameEN, String themeUA, String themeEN, Date date_from, Date date_to, Time working_time_from, Time working_time_to, BigDecimal price) {
        this.nameUA = nameUA;
        this.nameEN = nameEN;
        this.themeUA = themeUA;
        this.themaEN = themeEN;
        this.date_from = date_from;
        this.date_to = date_to;
        this.working_time_from = working_time_from;
        this.working_time_to = working_time_to;
        this.price = price;
    }


    public int getId_exhibition() {
        return id_exhibition;
    }

    public void setId_exhibition(int id_exhibition) {
        this.id_exhibition = id_exhibition;
    }

    public Date getDate_from() {
        return date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public Time getWorking_time_from() {
        return working_time_from;
    }

    public Time getWorking_time_to() {
        return working_time_to;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getNameUA() {
        return nameUA;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getThemeUA() {
        return themeUA;
    }

    public String getThemaEN() {
        return themaEN;
    }

    public List<String> getHalls() {
        return halls;
    }

    @Override
    public String toString() {
        return "Exhibitions{" +
                "id_exhibition=" + id_exhibition +
                ", nameUA='" + nameUA + '\'' +
                ", nameEN='" + nameEN + '\'' +
                ", themeUA='" + themeUA + '\'' +
                ", themaEN='" + themaEN + '\'' +
                ", date_from=" + date_from +
                ", date_to=" + date_to +
                ", working_time_from=" + working_time_from +
                ", working_time_to=" + working_time_to +
                ", price=" + price +
                ", working_time_to=" + image +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public int compareTo(Exhibitions exhibition) {
        return exhibition.getPrice().compareTo(this.getPrice());
    }
}

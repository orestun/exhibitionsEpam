package com.epam.exhibitions.servlets.db.entity;

public class ExhibitionsBasket {

    private Exhibitions exhibitions;
    private int number;

    public ExhibitionsBasket(Exhibitions exhibitions, int number){
        this.exhibitions=exhibitions;
        this.number=number;
    }


    public Exhibitions getExhibitions() {
        return exhibitions;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

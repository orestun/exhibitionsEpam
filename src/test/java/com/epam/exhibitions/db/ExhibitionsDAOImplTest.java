package com.epam.exhibitions.db;

import com.epam.exhibitions.db.entity.Exhibitions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class ExhibitionsDAOImplTest {


    @Test
    void minPrice() {
        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
        BigDecimal min = exhibitionsDAO.minPrice();
        Exhibitions exhibitions = new Exhibitions("Exh","Exh","theme","theme",new Date(2022,10,3),new Date(2022,10,3),new Time(10,0,0),new Time(20,0,0),min.subtract(new BigDecimal(10)));
        exhibitionsDAO.addExhibition(exhibitions);
        int id = exhibitionsDAO.getIdByNames("Exh","Exh");
        assertNotEquals(min,exhibitionsDAO.minPrice());
        exhibitionsDAO.deleteExhibition(id);
    }

    @Test
    void maxPrice() {
        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
        BigDecimal max = exhibitionsDAO.maxPrice();
        Exhibitions exhibitions = new Exhibitions("Exh","Exh","theme","theme",new Date(2022,10,3),new Date(2022,10,3),new Time(10,0,0),new Time(20,0,0),max.add(new BigDecimal(10)));
        exhibitionsDAO.addExhibition(exhibitions);
        int id = exhibitionsDAO.getIdByNames("Exh","Exh");
        assertNotEquals(max,exhibitionsDAO.minPrice());
        exhibitionsDAO.deleteExhibition(id);
    }
}
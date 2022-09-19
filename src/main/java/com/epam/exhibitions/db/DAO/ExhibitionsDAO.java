package com.epam.exhibitions.db.DAO;

import com.epam.exhibitions.db.entity.Exhibitions;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface ExhibitionsDAO {
    boolean addExhibition(Exhibitions exhibitions);
    boolean deleteExhibition(int id_exhibition);
    int getIdByNames(String nameUA, String nameEN);
    boolean duplicateNames(String nameUA, String nameEN);
    boolean addImage(String image,int id_exhibition);
    List<Exhibitions> exhibitionsCommonList();
    List<Exhibitions> exhibitionsSorting(String name, Date dateFrom,Date dateTo, BigDecimal priceFrom,BigDecimal priceTo);
    BigDecimal minPrice();
    BigDecimal maxPrice();

    boolean updateExhibition(Exhibitions exhibitions, int id);
}

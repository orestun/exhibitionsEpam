package com.epam.exhibitions.db.DAO;

import com.epam.exhibitions.db.entity.ExhibitionHalls;

import java.util.List;

public interface ExhibitonHallsDAO {
    boolean addHalls(ExhibitionHalls exhibitionHalls);
    boolean deleteHalls(int id);

    boolean updateHalls(ExhibitionHalls exhibitionHalls,int id);

    List<Boolean> getListHalls(int id);
    String getHalls(int id_exhibition);
}

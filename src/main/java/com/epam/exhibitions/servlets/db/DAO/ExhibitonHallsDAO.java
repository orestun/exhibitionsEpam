package com.epam.exhibitions.servlets.db.DAO;

import com.epam.exhibitions.servlets.db.entity.ExhibitionHalls;

public interface ExhibitonHallsDAO {
    boolean addHalls(ExhibitionHalls exhibitionHalls);
    boolean deleteHalls(int id);

    String getHalls(int id_exhibition);
}

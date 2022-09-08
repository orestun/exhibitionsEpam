package com.epam.exhibitions.db.DAO;

import com.epam.exhibitions.db.entity.ExhibitionHalls;

public interface ExhibitonHallsDAO {
    boolean addHalls(ExhibitionHalls exhibitionHalls);
    boolean deleteHalls(int id);

    String getHalls(int id_exhibition);
}

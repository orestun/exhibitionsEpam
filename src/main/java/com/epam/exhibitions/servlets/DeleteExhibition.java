package com.epam.exhibitions.servlets;

import com.epam.exhibitions.db.ExhibitionsDAOImpl;
import com.epam.exhibitions.db.TicketsDAOImpl;
import com.epam.exhibitions.db.ExhibitonHallsDAOImpl;
import com.epam.exhibitions.db.entity.Exhibitions;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeleteExhibition", value = "/DeleteExhibition")
public class DeleteExhibition extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
        String imageName = exhibitionsDAO.getExhibitionById(id).getImage();
        exhibitionsDAO.deleteExhibition(id);
        ExhibitonHallsDAOImpl exhibitionHallsDAO = ExhibitonHallsDAOImpl.getInstance();
        exhibitionHallsDAO.deleteHalls(id);
        TicketsDAOImpl ticketsDAO = TicketsDAOImpl.getInstance();
        ticketsDAO.deleteTickets(id);
        List<Exhibitions> exhibitionsList;
        if(request.getSession().getAttribute("sorting")==null){
            exhibitionsList = (List<Exhibitions>) request.getSession().getAttribute("sortingListCommon");
        }else{
            exhibitionsList = (List<Exhibitions>) request.getSession().getAttribute("sortingList");
        }
        List<Exhibitions> updatedList = new ArrayList<>();
        for(Exhibitions exhibition:exhibitionsList){
            if(exhibition.getId_exhibition()!=id){
                updatedList.add(exhibition);
            }
        }
        if(request.getSession().getAttribute("sorting")==null){
            request.getSession().setAttribute("sortingListCommon",updatedList);
        }else{
            request.getSession().setAttribute("sortingList",updatedList);
        }
        Path filePath = Paths.get("C:\\Users\\orest\\OneDrive\\Рабочий стол\\projects\\exhibitions\\src\\main\\webapp\\images\\"+imageName);
        Files.delete(filePath);
        request.getSession().setAttribute("page","1");

        response.sendRedirect("exhibitions.jsp");
    }
}

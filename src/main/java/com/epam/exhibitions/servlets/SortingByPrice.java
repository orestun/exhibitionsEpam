package com.epam.exhibitions.servlets;

import com.epam.exhibitions.db.ExhibitionsDAOImpl;
import com.epam.exhibitions.db.entity.Exhibitions;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "SortingByPrice", value = "/SortingByPrice")
public class SortingByPrice extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sorting = request.getParameter("sorting");
        List<Exhibitions> exhibitionsList;
        if(request.getSession().getAttribute("sorting")==null){
            exhibitionsList = (List<Exhibitions>) request.getSession().getAttribute("sortingListCommon");
        }else{
            exhibitionsList = (List<Exhibitions>) request.getSession().getAttribute("sortingList");
        }

        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
        if(exhibitionsList.isEmpty()){
            exhibitionsList = new ArrayList<>();
        }

        switch (sorting){
            case "common":
                exhibitionsList = exhibitionsDAO.exhibitionsCommonList();
                request.getSession().setAttribute("total",null);
                break;
            case "FromHighToLow":
                Collections.sort(exhibitionsList);
                break;
            case "FromLowToHigh":
                Collections.sort(exhibitionsList);
                Collections.reverse(exhibitionsList);
                break;
        }
        if(request.getSession().getAttribute("sorting")!=null){
            request.getSession().setAttribute("sortingList",exhibitionsList);
        }else {
            request.getSession().setAttribute("sortingListCommon",exhibitionsList);
        }

        response.sendRedirect("exhibitions.jsp");
    }

}

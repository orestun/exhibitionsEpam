package com.epam.exhibitions.servlets;

import com.epam.exhibitions.servlets.db.ExhibitionsDAOImpl;
import com.epam.exhibitions.servlets.db.entity.Exhibitions;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "SortExhibitions", value = "/SortExhibitions")
public class SortExhibitions extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo") ;
        String date = request.getParameter("date");
        request.getSession().setAttribute("nameSearch",name);
        request.getSession().setAttribute("priceToSearch",priceTo);
        request.getSession().setAttribute("priceFromSearch",priceFrom);
        request.getSession().setAttribute("dateSearch",date);
        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
        BigDecimal priceFromNumber;
        BigDecimal priceToNumber;
        Date dateFrom;
        Date dateTo;
        List<Exhibitions> exhibitionsList = new ArrayList<>();
        if(Objects.equals(priceFrom, "")){
            priceFromNumber = exhibitionsDAO.minPrice();
        }else{
            priceFromNumber = new BigDecimal(request.getParameter("priceFrom"));
        }

        if(Objects.equals(priceTo, "")){
            priceToNumber = exhibitionsDAO.maxPrice();
        }else{
            priceToNumber = new BigDecimal(request.getParameter("priceTo"));
        }

        if(Objects.equals(date, "")){
            dateFrom = new Date(5900,1,1);
            dateTo = new Date(-1900,1,1);
            exhibitionsList = exhibitionsDAO.exhibitionsSorting(name,dateFrom,dateTo,priceFromNumber,priceToNumber);
        }else{
            Date date1 = Date.valueOf(date);
            exhibitionsList = exhibitionsDAO.exhibitionsSorting(name,date1,date1,priceFromNumber,priceToNumber);
        }

        request.getSession().setAttribute("sorting","true");
        request.getSession().setAttribute("sortingList",exhibitionsList);
        response.sendRedirect("exhibitions.jsp");
    }
}

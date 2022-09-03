package com.epam.exhibitions.servlets;

import com.epam.exhibitions.servlets.db.ExhibitionsDAOImpl;
import com.epam.exhibitions.servlets.db.ExhibitonHallsDAOImpl;
import com.epam.exhibitions.servlets.db.entity.ExhibitionHalls;
import com.epam.exhibitions.servlets.db.entity.Exhibitions;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@WebServlet(name = "ToAddExhibitionServlet", value = "/ToAddExhibitionServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ToAddExhibitionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String nameUA = request.getParameter("nameUA");
        System.out.println(nameUA);
        String nameEN = request.getParameter("nameEN");
        String themeUA = request.getParameter("themeUA");
        String themeEN = request.getParameter("themeEN");
        Date date_from = Date.valueOf(request.getParameter("date_from"));
        Date date_to = Date.valueOf(request.getParameter("date_to"));
        System.out.println(date_from+" "+date_to);
        Time working_time_from = Time.valueOf(request.getParameter("working_time_from")+":00");
        Time working_time_to = Time.valueOf(request.getParameter("working_time_to")+":00");
        System.out.println(working_time_from+" "+working_time_to);
        BigDecimal price =new BigDecimal(request.getParameter("price"));

        boolean hall1 = request.getParameter("hall1")!=null;
        boolean hall2 = request.getParameter("hall2")!=null;
        boolean hall3 = request.getParameter("hall3")!=null;
        boolean hall4 = request.getParameter("hall4")!=null;
        boolean hall5 = request.getParameter("hall5")!=null;

        LocalDate dateNow = LocalDate.now();
        LocalDate dateFrom = LocalDate.parse(date_from.toString());

        ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
        ExhibitonHallsDAOImpl exhibitonHallsDAO = ExhibitonHallsDAOImpl.getInstance();

        if(exhibitionsDAO.dublicateNames(nameUA,nameEN)){
            session.setAttribute("namesError","There is such name or names!");
        } else if(dateFrom.isBefore(dateNow)){
            session.setAttribute("dateFromError","Date can`t be before the current date!");
        } else if (date_from.after(date_to)) {
            session.setAttribute("dateToError","Date can`t be before start date!");
        }else if(working_time_from.getTime()>working_time_to.getTime()){
            session.setAttribute("TimeToError","Time of start can`t be after finish date!");
        }else if(!hall1&&!hall2&&!hall3&&!hall4&&!hall5){
            session.setAttribute("HallError","You have to chose at least one hall!");
        }else {
            Exhibitions exhibitions = new Exhibitions(nameUA,nameEN,themeUA,themeEN,date_from,date_to,working_time_from,working_time_to,price);
            exhibitionsDAO.addExhibition(exhibitions);
            int idExhibition = exhibitionsDAO.getIdByNames(nameUA,nameEN);
            ExhibitionHalls exhibitionHalls = new ExhibitionHalls(idExhibition,hall1,hall2,hall3,hall4,hall5);


            exhibitonHallsDAO.addHalls(exhibitionHalls);

            Part file = request.getPart("file");
            String fileName = "exhibition_"+String.valueOf(idExhibition)+"."+file.getContentType().split("/")[1];
            file.write("C:\\Users\\orest\\OneDrive\\Рабочий стол\\projects\\exhibitions\\src\\main\\webapp\\images\\"+fileName);

            exhibitionsDAO.addImage(fileName,idExhibition);
        }



        response.sendRedirect("toaddexhibition.jsp");
    }
}

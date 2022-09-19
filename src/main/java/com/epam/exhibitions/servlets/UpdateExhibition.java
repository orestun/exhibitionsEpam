package com.epam.exhibitions.servlets;

import com.epam.exhibitions.db.ExhibitionsDAOImpl;
import com.epam.exhibitions.db.ExhibitonHallsDAOImpl;
import com.epam.exhibitions.db.entity.ExhibitionHalls;
import com.epam.exhibitions.db.entity.Exhibitions;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "UpdateExhibition", value = "/UpdateExhibition")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class UpdateExhibition extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int id = (int) request.getSession().getAttribute("idForUpdate");
        request.getSession().removeAttribute("idForUpdate");
        String nameUA = request.getParameter("nameUA");
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
        Exhibitions exhibitionsOriginal = exhibitionsDAO.getExhibitionById(id);
        ExhibitonHallsDAOImpl exhibitonHallsDAO = ExhibitonHallsDAOImpl.getInstance();
        String imageName =exhibitionsDAO.getExhibitionById(id).getImage();
        if(exhibitionsDAO.duplicateNames(nameUA,nameEN)&& !Objects.equals(exhibitionsOriginal.getNameUA(), nameUA)&&!Objects.equals(exhibitionsOriginal.getNameEN(), nameEN)){
            session.setAttribute("namesError","There is such name or names!");
        } else if(dateFrom.isBefore(dateNow)){
            session.setAttribute("dateFromError","Date can`t be before the current date!");
        } else if (date_from.after(date_to)) {
            session.setAttribute("dateToError","Date can`t be before start date!");
        }else if(working_time_from.getTime()>working_time_to.getTime()){
            session.setAttribute("TimeToError","Time of start can`t be after finish date!");
        }else if(!hall1&&!hall2&&!hall3&&!hall4&&!hall5){
            session.setAttribute("HallError","You have to chose at least one hall!");
        }else{
            Exhibitions exhibitions = new Exhibitions(nameUA,nameEN,themeUA,themeEN,date_from,date_to,working_time_from,working_time_to,price);

            ExhibitionHalls exhibitionHalls = new ExhibitionHalls(id,hall1,hall2,hall3,hall4,hall5);
            exhibitonHallsDAO.updateHalls(exhibitionHalls,id);
            Part file = request.getPart("file");
            if(Objects.equals(file.getContentType().split("/")[0], "image")){
                Path filePath = Paths.get("C:\\Users\\orest\\OneDrive\\Рабочий стол\\projects\\exhibitions\\src\\main\\webapp\\images\\"+imageName);
                Files.delete(filePath);
                String fileName = "exhibition_"+String.valueOf(id)+"."+file.getContentType().split("/")[1];
                file.write("C:\\Users\\orest\\OneDrive\\Рабочий стол\\projects\\exhibitions\\src\\main\\webapp\\images\\"+fileName);
                exhibitions.setImage(fileName);
            }else{
                exhibitions.setImage(exhibitionsOriginal.getImage());
            }
            exhibitionsDAO.updateExhibition(exhibitions,id);
            List<Exhibitions> exhibitionsList;
            if(request.getSession().getAttribute("sorting")==null){
                exhibitionsList = (List<Exhibitions>) request.getSession().getAttribute("sortingListCommon");
            }else{
                exhibitionsList = (List<Exhibitions>) request.getSession().getAttribute("sortingList");
            }
            List<Exhibitions> updatedExhibitionsList = new ArrayList<>();
            if(exhibitionsList!=null){
                for(Exhibitions exhibitions1:exhibitionsList){
                    if(exhibitions1.getId_exhibition()==id){
                        updatedExhibitionsList.add(exhibitions);
                    }else{
                        updatedExhibitionsList.add(exhibitions1);
                    }
                }
                if(request.getSession().getAttribute("sorting")==null){
                    request.getSession().setAttribute("sortingListCommon",updatedExhibitionsList);
                }else{
                    request.getSession().setAttribute("sortingList",updatedExhibitionsList);
                }
            }
        }
        response.sendRedirect("exhibitions.jsp");
    }
}

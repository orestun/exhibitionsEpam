package com.epam.exhibitions.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToAddExhibitionServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    ToAddExhibitionServlet toAddExhibitionServlet = new ToAddExhibitionServlet();
    @Test
    void doPostSuccess1() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("nameUA")).thenReturn("Картини");
        when(request.getParameter("nameEN")).thenReturn("Pictures");
        when(request.getParameter("themeUA")).thenReturn("Тема");
        when(request.getParameter("themeEN")).thenReturn("Theme");
        when(request.getParameter("date_from")).thenReturn(String.valueOf(new Date(2022,10,1)));
        when(request.getParameter("date_to")).thenReturn(String.valueOf(new Date(2022,10,7)));
        when(request.getParameter("working_time_from")).thenReturn("10:00");
        when(request.getParameter("working_time_to")).thenReturn("20:00");
        when(request.getParameter("price")).thenReturn(String.valueOf(new BigDecimal(35)));

        when(request.getParameter("hall1")).thenReturn(String.valueOf(true));
        when(request.getParameter("hall2")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall3")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall4")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall5")).thenReturn(String.valueOf(false));

        when(request.getPart("file")).thenReturn(new PartImpl());
        toAddExhibitionServlet.doPost(request,response);
        verify(response).sendRedirect("toaddexhibition.jsp");

    }
    @Test
    void doPostBadDuplicateNames() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("nameUA")).thenReturn("Конструктор Lego");
        when(request.getParameter("nameEN")).thenReturn("Brics Lego");
        when(request.getParameter("themeUA")).thenReturn("Тема");
        when(request.getParameter("themeEN")).thenReturn("Theme");
        when(request.getParameter("date_from")).thenReturn(String.valueOf(new Date(2022,10,1)));
        when(request.getParameter("date_to")).thenReturn(String.valueOf(new Date(2022,10,7)));
        when(request.getParameter("working_time_from")).thenReturn("10:00");
        when(request.getParameter("working_time_to")).thenReturn("20:00");
        when(request.getParameter("price")).thenReturn(String.valueOf(new BigDecimal(35)));

        when(request.getParameter("hall1")).thenReturn(String.valueOf(true));
        when(request.getParameter("hall2")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall3")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall4")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall5")).thenReturn(String.valueOf(false));

        when(request.getPart("file")).thenReturn(new PartImpl());
        toAddExhibitionServlet.doPost(request,response);
        verify(response).sendRedirect("toaddexhibition.jsp");

    }
    @Test
    void doPostDateIsBefore() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("nameUA")).thenReturn("Конструктор Lego");
        when(request.getParameter("nameEN")).thenReturn("Brics Lego");
        when(request.getParameter("themeUA")).thenReturn("Тема");
        when(request.getParameter("themeEN")).thenReturn("Theme");
        when(request.getParameter("date_from")).thenReturn(String.valueOf(new Date(0,10,1)));
        when(request.getParameter("date_to")).thenReturn(String.valueOf(new Date(2022,10,7)));
        when(request.getParameter("working_time_from")).thenReturn("10:00");
        when(request.getParameter("working_time_to")).thenReturn("20:00");
        when(request.getParameter("price")).thenReturn(String.valueOf(new BigDecimal(35)));

        when(request.getParameter("hall1")).thenReturn(String.valueOf(true));
        when(request.getParameter("hall2")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall3")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall4")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall5")).thenReturn(String.valueOf(false));

        when(request.getPart("file")).thenReturn(new PartImpl());
        toAddExhibitionServlet.doPost(request,response);
        verify(response).sendRedirect("toaddexhibition.jsp");

    }

    @Test
    void doPostDateAfter() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("nameUA")).thenReturn("Конструктор Lego");
        when(request.getParameter("nameEN")).thenReturn("Brics Lego");
        when(request.getParameter("themeUA")).thenReturn("Тема");
        when(request.getParameter("themeEN")).thenReturn("Theme");
        when(request.getParameter("date_from")).thenReturn(String.valueOf(new Date(2022,10,1)));
        when(request.getParameter("date_to")).thenReturn(String.valueOf(new Date(2022,9,7)));
        when(request.getParameter("working_time_from")).thenReturn("10:00");
        when(request.getParameter("working_time_to")).thenReturn("20:00");
        when(request.getParameter("price")).thenReturn(String.valueOf(new BigDecimal(35)));

        when(request.getParameter("hall1")).thenReturn(String.valueOf(true));
        when(request.getParameter("hall2")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall3")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall4")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall5")).thenReturn(String.valueOf(false));

        when(request.getPart("file")).thenReturn(new PartImpl());
        toAddExhibitionServlet.doPost(request,response);
        verify(response).sendRedirect("toaddexhibition.jsp");

    }

    @Test
    void doPostTimeError() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("nameUA")).thenReturn("Конструктор Lego");
        when(request.getParameter("nameEN")).thenReturn("Brics Lego");
        when(request.getParameter("themeUA")).thenReturn("Тема");
        when(request.getParameter("themeEN")).thenReturn("Theme");
        when(request.getParameter("date_from")).thenReturn(String.valueOf(new Date(2022,10,1)));
        when(request.getParameter("date_to")).thenReturn(String.valueOf(new Date(2022,9,7)));
        when(request.getParameter("working_time_from")).thenReturn("20:00");
        when(request.getParameter("working_time_to")).thenReturn("10:00");
        when(request.getParameter("price")).thenReturn(String.valueOf(new BigDecimal(35)));

        when(request.getParameter("hall1")).thenReturn(String.valueOf(true));
        when(request.getParameter("hall2")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall3")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall4")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall5")).thenReturn(String.valueOf(false));

        when(request.getPart("file")).thenReturn(new PartImpl());
        toAddExhibitionServlet.doPost(request,response);
        verify(response).sendRedirect("toaddexhibition.jsp");

    }

    @Test
    void doPostHallsError() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("nameUA")).thenReturn("Конструктор Lego");
        when(request.getParameter("nameEN")).thenReturn("Brics Lego");
        when(request.getParameter("themeUA")).thenReturn("Тема");
        when(request.getParameter("themeEN")).thenReturn("Theme");
        when(request.getParameter("date_from")).thenReturn(String.valueOf(new Date(2022,10,1)));
        when(request.getParameter("date_to")).thenReturn(String.valueOf(new Date(2022,9,7)));
        when(request.getParameter("working_time_from")).thenReturn("20:00");
        when(request.getParameter("working_time_to")).thenReturn("10:00");
        when(request.getParameter("price")).thenReturn(String.valueOf(new BigDecimal(35)));

        when(request.getParameter("hall1")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall2")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall3")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall4")).thenReturn(String.valueOf(false));
        when(request.getParameter("hall5")).thenReturn(String.valueOf(false));

        when(request.getPart("file")).thenReturn(new PartImpl());
        toAddExhibitionServlet.doPost(request,response);
        verify(response).sendRedirect("toaddexhibition.jsp");

    }
}
package com.epam.exhibitions.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlusTicketTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    PlusTicket plusTicket = new PlusTicket();
    @Test
    void doGetAccess1() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("id")).thenReturn(String.valueOf(10));
        session.setAttribute("listUsersBasket",new ArrayList<>());
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("listUsersBasket")).thenReturn(new ArrayList<>());
        plusTicket.doGet(request,response);
        verify(response).sendRedirect("basket.jsp");
    }

    @Test
    void doGetAccess2() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        session.setAttribute("listUsersBasket",new ArrayList<>());
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("listUsersBasket")).thenReturn(new ArrayList<>());
        plusTicket.doGet(request,response);
        verify(response).sendRedirect("basket.jsp");
    }

    @Test
    void doGetAccess3() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("id")).thenReturn(String.valueOf(100));
        session.setAttribute("listUsersBasket",new ArrayList<>());
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("listUsersBasket")).thenReturn(new ArrayList<>());
        plusTicket.doGet(request,response);
        verify(response).sendRedirect("basket.jsp");
    }
}
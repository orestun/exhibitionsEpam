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

class ToAddInBasketTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    ToAddInBasket toAddInBasket = new ToAddInBasket();
    @Test
    void doGet() throws IOException, ServletException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(10));
        when(request.getSession().getAttribute("listUsersBasket")).thenReturn(new ArrayList<>());
        toAddInBasket.doGet(request,response);
        verify(response).sendRedirect("exhibitions.jsp");
    }
}
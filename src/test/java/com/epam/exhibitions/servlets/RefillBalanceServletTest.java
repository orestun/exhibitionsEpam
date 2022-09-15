package com.epam.exhibitions.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefillBalanceServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RefillBalanceServlet refillBalanceServlet = new RefillBalanceServlet();
    @Test
    void doPost() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("paid")).thenReturn(String.valueOf(new BigDecimal(120)));
        refillBalanceServlet.doPost(request,response);
        verify(response).sendRedirect("index.jsp");
    }
}
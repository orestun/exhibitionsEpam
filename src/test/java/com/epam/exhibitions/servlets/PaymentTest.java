package com.epam.exhibitions.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    Payment payment = new Payment();
    @Test
    void doPost() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("username")).thenReturn("orestun");
        when(request.getSession().getAttribute("theSumInBasket")).thenReturn(new BigDecimal(100));
        when(request.getSession().getAttribute("wallet")).thenReturn(new BigDecimal(110));
        when(request.getSession().getAttribute("listUsersBasket")).thenReturn(new ArrayList<>());
        payment.doPost(request,response);
        verify(response).sendRedirect("basket.jsp");
    }
}
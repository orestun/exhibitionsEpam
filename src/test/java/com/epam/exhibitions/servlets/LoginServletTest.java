package com.epam.exhibitions.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    LoginServlet loginServlet = new LoginServlet();

    @Test
    void doPostBadUsername() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("username")).thenReturn("orestun897");
        when(request.getParameter("password")).thenReturn("12345678");

        loginServlet.doPost(request,response);
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPostBadPassword() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("username")).thenReturn("orestun");
        when(request.getParameter("password")).thenReturn("12345643");

        loginServlet.doPost(request,response);
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPostSuccess1() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("username")).thenReturn("orestun");
        when(request.getParameter("password")).thenReturn("12345678");

        loginServlet.doPost(request,response);
        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void doPostSuccess2() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        when(request.getParameter("username")).thenReturn("svitusik");
        when(request.getParameter("password")).thenReturn("vaskul2004");

        loginServlet.doPost(request,response);
        verify(response).sendRedirect("index.jsp");
    }
}
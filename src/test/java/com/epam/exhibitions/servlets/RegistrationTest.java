package com.epam.exhibitions.servlets;

import com.epam.exhibitions.db.UserDAOImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    Registration registration = new Registration();


    @Test
    void doPostEmailRepeat() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("registration.jsp")).thenReturn(dispatcher);

        when(request.getParameter("name")).thenReturn("Orest");
        when(request.getParameter("secondName")).thenReturn("Uzhytchak");
        when(request.getParameter("email")).thenReturn("orest.uzhytchak0001@gmail.com");
        when(request.getParameter("phone")).thenReturn("+380509112262");
        when(request.getParameter("country")).thenReturn("Україна");
        when(request.getParameter("username")).thenReturn("orestun3");
        when(request.getParameter("password")).thenReturn("12345678");
        registration.doPost(request,response);
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPostPhoneRepeat() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("registration.jsp")).thenReturn(dispatcher);

        when(request.getParameter("name")).thenReturn("Orest");
        when(request.getParameter("secondName")).thenReturn("Uzhytchak");
        when(request.getParameter("email")).thenReturn("orest.uzhytchak0002@gmail.com");
        when(request.getParameter("phone")).thenReturn("+380509112262");
        when(request.getParameter("country")).thenReturn("Україна");
        when(request.getParameter("username")).thenReturn("orestun3");
        when(request.getParameter("password")).thenReturn("12345678");
        registration.doPost(request,response);
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPostUsernameRepeat() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("registration.jsp")).thenReturn(dispatcher);

        when(request.getParameter("name")).thenReturn("Orest");
        when(request.getParameter("secondName")).thenReturn("Uzhytchak");
        when(request.getParameter("email")).thenReturn("orest.uzhytchak0001@gmail.com");
        when(request.getParameter("phone")).thenReturn("+380999112262");
        when(request.getParameter("country")).thenReturn("Україна");
        when(request.getParameter("username")).thenReturn("orestun");
        when(request.getParameter("password")).thenReturn("12345678");
        registration.doPost(request,response);
        verify(dispatcher).forward(request,response);
    }
    @Test
    void doPostSuccess1() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("registration.jsp")).thenReturn(dispatcher);

        when(request.getParameter("name")).thenReturn("Orest");
        when(request.getParameter("secondName")).thenReturn("Uzhytchak");
        when(request.getParameter("email")).thenReturn("orest.uzh32chak0001@gmail.com");
        when(request.getParameter("phone")).thenReturn("+38099911322");
        when(request.getParameter("country")).thenReturn("Україна");
        when(request.getParameter("username")).thenReturn("orestu4465u");
        when(request.getParameter("password")).thenReturn("12345678");
        registration.doPost(request,response);
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.deleteUser("orestu4465u");
        verify(response).sendRedirect("index.jsp");
    }
    @Test
    void doPostSuccess2() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("registration.jsp")).thenReturn(dispatcher);

        when(request.getParameter("name")).thenReturn("Orest");
        when(request.getParameter("secondName")).thenReturn("Uzhytchak");
        when(request.getParameter("email")).thenReturn("orest.uzh32chak0001@gmail.com");
        when(request.getParameter("phone")).thenReturn("+38099911322");
        when(request.getParameter("country")).thenReturn("Україна");
        when(request.getParameter("username")).thenReturn("orestun32");
        when(request.getParameter("password")).thenReturn("12345678");
        registration.doPost(request,response);
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.deleteUser("orestun32");
        verify(response).sendRedirect("index.jsp");
    }
}
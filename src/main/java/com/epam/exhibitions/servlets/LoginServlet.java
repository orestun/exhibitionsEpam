package com.epam.exhibitions.servlets;

import com.epam.exhibitions.servlets.db.UserDAOImpl;
import com.epam.exhibitions.servlets.db.entity.User;
import com.mysql.cj.Session;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("usernameError"," ");
        session.setAttribute("incorrectPassword", " ");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAOImpl userDAO = UserDAOImpl.getInstance();

        if(!userDAO.verifyUsername(username)){
            session.setAttribute("usernameError","There is not such user!");
            RequestDispatcher dd = request.getRequestDispatcher("login.jsp");
            dd.forward(request, response);
        }else if(!Objects.equals(password, userDAO.getPassword(username))){
            session.setAttribute("incorrectPassword","It is incorrect password!");
            RequestDispatcher dd = request.getRequestDispatcher("login.jsp");
            dd.forward(request, response);
        }else {
            session.removeAttribute("incorrectPassword");
            session.removeAttribute("usernameError");

            UserDAOImpl userDAO1 = UserDAOImpl.getInstance();
            User user = userDAO1.getUserByUsername(username);
            session.setAttribute("firthName",user.getFirth_name());
            session.setAttribute("secondName",user.getSecond_name());
            session.setAttribute("username",user.getUsername());
            session.setAttribute("email",user.getEmail());
            session.setAttribute("phone",user.getPhone_number());
            session.setAttribute("country",user.getCountry());
            session.setAttribute("wallet",user.getWallet());
            String role = userDAO1.getRole(username);
            session.setAttribute("role",role);
            System.out.println(session.getId());
            response.sendRedirect("index.jsp");
        }

    }
}

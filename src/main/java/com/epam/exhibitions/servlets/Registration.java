package com.epam.exhibitions.servlets;

import com.epam.exhibitions.servlets.db.ExhibitonHallsDAOImpl;
import com.epam.exhibitions.servlets.db.UserDAOImpl;
import com.epam.exhibitions.servlets.db.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "Registration", value = "/Registration")
public class Registration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        HttpSession session = request.getSession();
        session.setAttribute("emailRepeat"," ");
        session.setAttribute("phoneRepeat", " ");

        System.out.println(session.getAttribute("role"));

        String name = request.getParameter("name");
        String secondName = request.getParameter("secondName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String country = request.getParameter("country");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAOImpl userDAO = UserDAOImpl.getInstance();
        userDAO.getConnection();

        if(userDAO.verifyEmail(email)){
            session.setAttribute("emailRepeat","There is user with such email!");
            RequestDispatcher dd = request.getRequestDispatcher("registration.jsp");
            dd.forward(request, response);
        } else if(userDAO.verifyPhoneNumber(phone)){
            session.setAttribute("phoneRepeat", "There is user with such phone number!");
            RequestDispatcher dd = request.getRequestDispatcher("registration.jsp");
            dd.forward(request, response);
        } else if (userDAO.verifyUsername(username)) {
            session.setAttribute("usernameRepeat", "There is user with such username!");
            RequestDispatcher dd = request.getRequestDispatcher("registration.jsp");
            dd.forward(request, response);
        }else{
            User user = new User(name,secondName,email,phone,country,username,password,new BigDecimal(0));
            userDAO.addNewUser(user);
            session.removeAttribute("emailRepeat");//Removing attributes from session            .removeAttribute("phoneRepeat");        //that a not required already
            session.removeAttribute("usernameRepeat");
            session.removeAttribute("phoneRepeat");

            session.setAttribute("firthName",user.getFirth_name());
            session.setAttribute("secondName",user.getSecond_name());
            session.setAttribute("username",user.getUsername());
            session.setAttribute("email",user.getEmail());
            session.setAttribute("phone",user.getPhone_number());
            session.setAttribute("country",user.getCountry());
            session.setAttribute("wallet",user.getWallet());
            String role = userDAO.getRole(username);
            session.setAttribute("role",role);
            response.sendRedirect("index.jsp");
        }
    }
}

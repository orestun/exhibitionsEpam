package com.epam.exhibitions.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "AdminAccessFilter")
public class AdminAccessFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String role = (String) httpServletRequest.getSession().getAttribute("role");
            if (role==null||role.equals("USER")) {
                httpServletResponse.sendRedirect("error403.jsp");
            }
            chain.doFilter(request,response);
    }
}

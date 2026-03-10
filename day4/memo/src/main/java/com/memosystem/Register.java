package com.memosystem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class Register extends HttpServlet {
    private Dao dao = new Dao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            String u = request.getParameter("username");
            String p = request.getParameter("password");
            
            if (dao.register(u, p)) {
                response.sendRedirect("login.jsp?msg=success");
            } else {
                response.sendRedirect("register.jsp?error=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=500");
        }
    }
}
package com.memosystem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login") 
public class Login extends HttpServlet {
    private Dao dao = new Dao(); 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            String u = request.getParameter("username");
            String p = request.getParameter("password");
            
            User user = dao.login(u, p);
            
            if (user != null) {
                // 登录成功，把用户对象存进 Session
                request.getSession().setAttribute("user", user);
                response.sendRedirect("index.jsp");
            } else {
                // 账号密码不对
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=500");
        }
    }
}
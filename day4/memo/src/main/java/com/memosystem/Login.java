package com.memosystem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login") 
public class Login extends HttpServlet {
    private Dao dao = new Dao(); 

    // 这里只能抛出这两个具体的异常，绝对不能写 Exception！
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            String u = request.getParameter("username");
            String p = request.getParameter("password");
            
            // 业务逻辑包在 try 里，这里的 Exception 会被下面的 catch 抓住
            User user = dao.login(u, p);
            
            if (user != null) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (Exception e) {
            // 所有的异常都在这里消化掉，不会传给上层
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=500");
        }
    }
}
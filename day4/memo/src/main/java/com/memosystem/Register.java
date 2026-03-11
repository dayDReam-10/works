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
            
            // 调用 Dao 里的 register 方法插入数据
            if (dao.register(u, p)) {
                // 注册成功，跳回登录页并给个成功提示
                response.sendRedirect("login.jsp?msg=success");
            } else {
                // 注册失败（通常是用户名已存在）
                response.sendRedirect("register.jsp?error=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=500");
        }
    }
}
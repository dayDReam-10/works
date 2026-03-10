package com.memosystem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/deleteMemo")
public class DeleteMemo extends HttpServlet {
    private Dao dao = new Dao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // 从 URL 参数拿 id
            int id = Integer.parseInt(request.getParameter("id"));
            
            dao.deleteMemo(id);
            
            // 跳回首页
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=del_fail");
        }
    }
}
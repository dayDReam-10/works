package com.memosystem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/addMemo")
public class addMemo extends HttpServlet {
    private Dao dao = new Dao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // 从 Session 里拿到当前登录的用户对象
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user != null) {
                // 拿到网页表单传过来的数据
                String title = request.getParameter("title");
                String content = request.getParameter("content");

                // 4. 调用 addMemo
                dao.addMemo(user.getId(), title, content);

                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=1");
        }
    }
}
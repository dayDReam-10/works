package com.memosystem;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/uploadAvatar")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private Dao dao = new Dao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            Part part = request.getPart("avatarFile");
            String fileName = UUID.randomUUID().toString() + "_" + part.getSubmittedFileName();
            
            String savePath = getServletContext().getRealPath("/upload");
            File fileDir = new File(savePath);
            if (!fileDir.exists()) fileDir.mkdirs();
            
            part.write(savePath + File.separator + fileName);
            
            dao.updateAvatar(user.getId(), fileName);
            user.setAvatar(fileName);
            
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=upload_fail");
        }
    }
}
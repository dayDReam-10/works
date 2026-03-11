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
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            Part part = request.getPart("avatarFile");
            String header = part.getHeader("content-disposition");
            String oldFileName = "";
            for (String temp : header.split(";")) {
                if (temp.trim().startsWith("filename")) {
                    oldFileName = temp.substring(temp.indexOf('=') + 1).trim().replace("\"", "");
                }
            }

            if (oldFileName != null && !oldFileName.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + oldFileName;
                String savePath = getServletContext().getRealPath("/upload");
                File fileDir = new File(savePath);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                
                part.write(savePath + File.separator + fileName);
                
                dao.updateAvatar(user.getId(), fileName);
                user.setAvatar(fileName);
            }
            
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=upload_fail");
        }
    }
}
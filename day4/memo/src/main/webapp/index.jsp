<%@ page import="com.memosystem.User" %>
<%@ page import="com.memosystem.Dao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Memo System</title>
</head>
<body>
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>

    <div>
        <img src="upload/<%= user.getAvatar() %>" width="60" height="60" onerror="this.src='default.png'">
        <h3>User: <%= user.getUsername() %></h3>
        
        <form action="uploadAvatar" method="post" enctype="multipart/form-data">
            <input type="file" name="avatarFile">
            <input type="submit" value="Upload">
        </form>
    </div>

    <hr>

    <form action="addMemo" method="post">
        Title: <input type="text" name="title"><br>
        Content: <br>
        <textarea name="content" rows="3" cols="30"></textarea><br>
        <input type="submit" value="Save Memo">
    </form>

    <hr>

    <div id="memo-list">
        <%
            Dao dao = new Dao();
            String html = dao.get(user.getId());
            out.print(html != null ? html : "No memos found.");
        %>
    </div>
</body>
</html>

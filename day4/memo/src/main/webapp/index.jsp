<%@ page import="com.memosystem.User" %>
<%@ page import="com.memosystem.Dao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Memo System</title>
    <script>
        function check() {
            var t = document.getElementById("title").value;
            if(!t || t.trim() === "") {
                alert("标题不能为空！");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) { response.sendRedirect("login.jsp"); return; }
    %>
    <img src="upload/<%= user.getAvatar() %>" width="60" height="60" onerror="this.src='default.png'">
    <h3>User: <%= user.getUsername() %></h3>
    
    <form action="uploadAvatar" method="post" enctype="multipart/form-data">
        <input type="file" name="avatarFile">
        <input type="submit" value="Upload">
    </form>
    <hr>
    <form action="addMemo" method="post" onsubmit="return check()">
        Title: <input type="text" name="title" id="title"><br>
        Content: <br>
        <textarea name="content" rows="3" cols="30"></textarea><br>
        <input type="submit" value="Save Memo">
    </form>
    <hr>
    <div id="memo-list">
        <% out.print(new Dao().get(user.getId())); %>
    </div>
</body>
</html>
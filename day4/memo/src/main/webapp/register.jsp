<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h2>User Register</h2>
    <form action="register" method="post">
        Username: <input type="text" name="username" required><br>
        Password: <input type="password" name="password" required><br>
        <input type="submit" value="Register Now">
    </form>
    
    <p>已有账号？<a href="login.jsp">返回登录</a></p>

    <% if("1".equals(request.getParameter("error"))) { %>
        <p style="color:red">Registration failed! Username might exist.</p>
    <% } %>
    
    <% if("500".equals(request.getParameter("error"))) { %>
        <p style="color:red">Server error, check your database.</p>
    <% } %>
</body>
</html>
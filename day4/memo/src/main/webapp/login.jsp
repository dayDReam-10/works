<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>User Login</h2>
    <form action="login" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit" value="Login">
    </form>

    <% if("1".equals(request.getParameter("error"))) { %>
        <p style="color:red">Invalid username or password.</p>
    <% } %>
    
    <% if("500".equals(request.getParameter("error"))) { %>
        <p style="color:red">Server error, check database connection.</p>
    <% } %>
</body>
</html>
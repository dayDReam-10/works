<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <h3>修改备忘录</h3>
    <form action="updateMemo" method="post">
        <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
        标题: <input type="text" name="title" value="<%= request.getParameter("t") %>"><br>
        内容: <textarea name="content" rows="5" cols="30"><%= request.getParameter("c") %></textarea><br>
        <input type="submit" value="保存修改">
        <a href="index.jsp">取消</a>
    </form>
</body>
</html>
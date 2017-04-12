<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
${requestScope.error}
<form action="${s:mvcUrl('loginLink').build()}" method="post">
    Login:
    <input type="text" name="login" value=""/>
    <br>
    Password:
    <input type="password" name="password" value=""/>
    <br>
    <input type="submit" value="send"/>
</form>
</body>
</html>

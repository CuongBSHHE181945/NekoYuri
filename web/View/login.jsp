<%-- 
    Document   : login
    Created on : Jul 2, 2024, 2:39:49 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body onload="showAlert('<%= (request.getAttribute("errorMessage") != null) ? request.getAttribute("errorMessage") : "" %>')">       
        <form method="post" action="Login">
            Username: <input type="text" name ="username"/><br/>
            Password: <input type="password" name ="password"/><br/>
            <input type="submit" value="Login"/>
        </form>
        <button onclick="location.href='Register'">Create account</button>
    </body>
    
    <script type="text/javascript">
            function showAlert(message) {
                if (message !== "") {
                    alert(message);
                }
            }
    </script>
</html>

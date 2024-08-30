<%-- 
    Document   : register
    Created on : Jul 14, 2024, 12:32:10 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <body onload="showAlert('<%= (request.getAttribute("errorMessage") != null) ? request.getAttribute("errorMessage") : "" %>')">       
        <form method="post" action="Register">
            Username: <input type="text" name ="username"/><br/>
            Password: <input type="password" name ="password"/><br/>
            <input type="submit" value="Register"/>
        </form>
    </body>
    
    <script type="text/javascript">
            function showAlert(message) {
                if (message !== "") {
                    alert(message);
                }
            }
    </script>
    </body>
</html>

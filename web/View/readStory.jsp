<%-- 
    Document   : readStory
    Created on : Jul 11, 2024, 2:45:02 PM
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
        <h1>${content.getStoryName()}</h1>
        <p>${content.getContent()}</p>
        <button onclick="location.href='Main'">Main page</button>
    </body>
</html>

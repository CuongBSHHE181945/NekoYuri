<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./styleCSS.css">
    <title>Read Story</title>
</head>
<body>
    <h1>${content.getStoryName()}</h1>
    <p>${content.getContent()}</p>
    <button onclick="location.href='Main'">Main page</button>
    <form action="Logout" method="post">
        <button type="submit" class="logout-button">Logout</button>
    </form>
</body>
</html>

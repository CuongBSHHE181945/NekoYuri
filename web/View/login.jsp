<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./styleCSS.css">
        <title>Login</title>
    </head>
    <body>
        <div class="form-container">
            <form method="post" action="Login">
                <h2>Login</h2>
                <input type="text" name="username" placeholder="Username"><br/>
                <input type="password" name="password" placeholder="Password"><br/>
                <input type="submit" value="Login">
            </form>
            <button onclick="location.href='Register'">Create account</button>
        </div>
    </body>
</html>
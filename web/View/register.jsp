<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./styleCSS.css">
    <title>Register</title>
</head>
<body onload="showAlert('<%= (request.getAttribute("errorMessage") != null) ? request.getAttribute("errorMessage") : "" %>')">
    <form method="post" action="Register">
        Username: <input type="text" name="username"/><br/>
        Password: <input type="password" name="password"/><br/>
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
</html>

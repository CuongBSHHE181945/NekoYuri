<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./styleCSS.css">
        <title>JSP Page</title>
    </head>
    <body onload="showAlert('${updateAccountMsg}'); showAlert('${deleteAccountMsg}')">
        <button onclick="location.href='Main'">Main page</button>
        <table>
            <tr>
                <td>Username</td>
                <td>Role</td>
                <td>Delete</td>
            </tr>
            <c:forEach var="account" items="${accountList}">
                <c:if test="${account.getRoleId() != 1}">
                    <tr>
                        <td>${account.getUsername()}</td>
                        <td>
                            <form action="UpdateAccount" method="post">
                                <input type="hidden" name="updateAccountId" value="${account.getAccountId()}">
                                <select name="updateRoleId" onchange="this.form.submit()">
                                    <c:forEach var="role" items="${roleList}">
                                        <c:if test="${role.getRoleId() >= 2}">
                                            <option value="${role.getRoleId()}" <c:if test="${account.getRoleId() == role.getRoleId()}">selected</c:if>>
                                                ${role.getRoleName()}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </form>
                        </td>
                        <td>
                            <form action="DeleteAccount" method="post">
                                <input type="hidden" name="deleteAccountId" value="${account.getAccountId()}">
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </body>
</html>

<script>
        function showAlert(message) {
            if (message) {
                alert(message);
            }
        }
</script>
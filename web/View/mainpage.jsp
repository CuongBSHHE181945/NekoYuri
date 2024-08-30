<%-- 
    Document   : mainpage
    Created on : Jul 2, 2024, 3:41:12 PM
    Author     : Admin
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./styleCSS.css">
        <title>JSP Page</title>
    </head>
    <body onload="showAlert('${sessionScope.registerMsg}')">
        <div> 
            <c:if test = "${sessionScope.roleId <= 2}">
                <button onclick="location.href='StoryManagement'">Story Management</button>
            </c:if>
            <c:if test = "${sessionScope.roleId == 1}">
                <button onclick="location.href='AccountManagement'">Account Management</button>
            </c:if>
            <table>
                <tr>
                    <td>Name</td>
                    <td>Author</td>
                    <td>Uploader</td>
                    <td>Description</td>
                    <td>Tag</td>
                </tr>
            <c:forEach var="story" items="${storyList}">
                <tr>
                    <td>
                        <a href="ReadStory?readStoryId=${story.getStoryId()}">
                                    ${story.getStoryName()}
                        </a>
                    </td>
                    <td>${story.getAuthorName()}</td>
                    <td>${story.getAccountName()}</td>
                    <td>${story.getDescription()}</td>
                    <td>
                    <c:forEach var="tag" items="${story.getTag()}">
                        ${tag.getTagName()}
                    </c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </table>
        
        <button onclick="location.href='Search'">Search</button>
        <form action="Logout" method="post">
            <input type="submit" value="Logout">
        </form>

        </div>
<!--        <button onclick="location.href='SessionDetail'">Log</button>-->
        <c:if test="${not empty sessionScope.registerMsg}">
            <c:remove var="registerMsg" scope="session"></c:remove>
        </c:if>
    </body>
</html>

<script type="text/javascript">
            function showAlert(message) {
                if (message !== "") {
                    alert(message);
                }
            }
</script>

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
    <body>
        <div> 
            <form action="Search" method="post">
                <input type="hidden" name="source" value="userpage">
                <input type="text" name="keyword" placeholder="Search stories...">
                <c:forEach var="tag" items="${tagList}">
                    <input type = "checkbox" name="tagCheck" value="${tag.getTagId()}"/>${tag.getTagName()}
                </c:forEach>
                
                <button type="submit">Search</button>
            </form>
            
            <c:if test="${resultList ne null}">
                <table>
                    <tr>
                        <td>Name</td>
                        <td>Author</td>
                        <td>Uploader</td>
                        <td>Description</td>
                        <td>Tag</td>
                    </tr>
                <c:forEach var="story" items="${resultList}">
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
            </c:if>
        <button onclick="location.href='Main'">Main page</button>
        <form action="Logout" method="post">
            <input type="submit" value="Logout">
        </form>
        </div>
<!--        <a href = 'SessionDetail'>View log-->
        
    </body>
</html>

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
    <body onload="showAlert('${deleteMsg}')">
        <form action="Search" method="post">
            <input type="text" name="keyword" placeholder="Search stories...">
            <input type="hidden" name="source" value="managementpage">
            <c:forEach var="tag" items="${tagList}">
                <input type="checkbox" name="tagCheck" value="${tag.getTagId()}"/>${tag.getTagName()}
            </c:forEach>
                
            <button type="submit">Search</button>
        </form>
            
        <c:choose>
            <c:when test="${resultList ne null}">
                <table>
                    <tr>
                        <td>Name</td>
                        <td>Author</td>
                        <td>Uploader</td>
                        <td>Description</td>
                        <td>Tag</td>
                        <td>Update</td>
                        <td>Delete</td>
                    </tr>
                    <c:forEach var="story" items="${resultList}">
                        <tr>
                            <td>${story.getStoryName()}</td>
                            <td>${story.getAuthorName()}</td>
                            <td>${story.getAccountName()}</td>
                            <td>${story.getDescription()}</td>
                            <td>
                                <c:forEach var="tag" items="${story.getTag()}">
                                    ${tag.getTagName()}
                                </c:forEach>
                            </td>
                            <td>
                                <a href="Update?updateStoryId=${story.getStoryId()}">
                                    <button>Update</button>
                                </a>
                            </td>
                            <td>
                                <form action="Delete" method="post">
                                    <input type="hidden" name="deleteStoryId" value="${story.getStoryId()}">
                                    <button type="submit">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <td>Name</td>
                        <td>Author</td>
                        <td>Uploader</td>
                        <td>Description</td>
                        <td>Tag</td>
                        <td>Update</td>
                        <td>Delete</td>
                    </tr>
                    <c:forEach var="story" items="${storyList}">
                        <tr>
                            <td>${story.getStoryName()}</td>
                            <td>${story.getAuthorName()}</td>
                            <td>${story.getAccountName()}</td>
                            <td>${story.getDescription()}</td>
                            <td>
                                <c:forEach var="tag" items="${story.getTag()}">
                                    ${tag.getTagName()}
                                </c:forEach>
                            </td>
                            <td>
                                <a href="Update?updateStoryId=${story.getStoryId()}">
                                    <button>Update</button>
                                </a>
                            </td>
                            <td>
                                <form action="Delete" method="post">
                                    <input type="hidden" name="deleteStoryId" value="${story.getStoryId()}">
                                    <button type="submit">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
        
        <button onclick="location.href='NewStory'">Create</button>
        <button onclick="location.href='Main'">Main page</button>
        <form action="Logout" method="post">
            <input type="submit" value="Logout">
        </form>
<!--        <button onclick="location.href='SessionDetail'">Log</button>-->
        
    </body>
</html>

<script>
        function showAlert(message) {
            if (message) {
                alert(message);
            }
        }
</script>

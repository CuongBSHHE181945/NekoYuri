<%-- 
    Document   : update
    Created on : Jul 10, 2024, 2:17:24 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./styleCSS.css">
    <title>JSP Page</title>
</head>
    <body onload="showAlert('${updateMsg}')">
        <form action="Update" method="post">
            <input type="hidden" name="updateId" value="${updateStory.getStoryId()}">
            Name: <input type="text" name="nameUpdate" value="${updateStory.getStoryName()}"><br>
            Author: <input type="text" name="authorUpdate" value="${updateStory.getAuthorName()}"><br>
            Uploader: <input type="text" name="uploaderUpdate" value="${updateStory.getAccountName()}" readonly><br>
            Description: <input type="text" name="descriptionUpdate" value="${updateStory.getDescription()}"><br>
            Tags:
            <c:forEach var="tag" items="${tagList}">
                <c:set var="checked" value="" />
                <c:forEach var="selectedTag" items="${updateStory.getTag()}">
                    <c:if test="${tag.getTagId() eq selectedTag.getTagId()}">
                        <c:set var="checked" value="checked" />
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="tagUpdate" value="${tag.getTagId()}" ${checked}>${tag.getTagName()}
            </c:forEach><br>
            Content<br>
                <textarea rows="30" cols="300" name="contentUpdate">${content.getContent()}</textarea><br>
            <button type="submit">Update</button>


        </form>
        <button onclick="location.href='StoryManagement'">Back</button>
    </body>
</html>

<script>
        function showAlert(message) {
            if (message) {
                alert(message);
            }
        }
</script>

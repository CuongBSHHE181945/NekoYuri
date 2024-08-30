<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Story</title>
    </head>
    <body onload="initializePage()">
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());
        %>
        <form action="NewStory" method="post">
            Name: <input type="text" name="newName" value="${storyName != null ? storyName : ''}"><br>
            Author: <input type="text" name="newAuthor" value="${author != null ? author : ''}"><br>
            Description: <input type="text" name="newDescription" value="${description != null ? description : ''}"><br>
            Tags:
            <c:forEach var="tag" items="${tagList}">
                <c:set var="checked" value="" />
                <c:forEach var="selectedTag" items="${tags}">
                    <c:if test="${tag.getTagId() eq selectedTag}">
                        <c:set var="checked" value="checked" />
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="newTag" value="${tag.getTagId()}" ${checked}>${tag.getTagName()}
            </c:forEach><br>
            Content<br>
            <textarea rows="30" cols="100" name="content">${content != null ? content : ''}</textarea><br>
            Date: 
            <input type="date" id="newDate" name="newDate" value="${currentDate}" disabled>
            <button type="submit">Create</button>
        </form>
        <c:if test="${not empty errorMsg}">
            <p style="color:red;">${errorMsg}</p>
        </c:if>
        <button onclick="location.href='StoryManagement'">Back</button>
    </body>
</html>

<script>
    function showAlert(message) {
        if (message && message !== "false") {
            alert(message);
        }
    }

    function initializePage() {
        showAlert('${createMsg}');
        
        var dateInput = document.getElementById('newDate');
        if (!dateInput.value) {
            var today = new Date().toISOString().split('T')[0];
            dateInput.value = today;
        }
    }
</script>

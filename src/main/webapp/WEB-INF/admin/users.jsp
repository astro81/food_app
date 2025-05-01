<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>
</head>
<body>
<h1>User Management</h1>
<c:if test="${not empty NOTIFICATION}">
    <div class="notification">${NOTIFICATION}</div>
</c:if>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Actions</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.userId}</td>
            <td>${user.userName}</td>
            <td>${user.userMail}</td>
            <td>${user.userRole}</td>
            <td>
                <c:if test="${user.userRole != 'admin'}">
                    <form action="${pageContext.request.contextPath}/admin/users" method="post">
                        <input type="hidden" name="userId" value="${user.userId}">
                        <c:choose>
                            <c:when test="${user.userRole == 'vendor'}">
                                <button type="submit" name="action" value="demote">Demote to Customer</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" name="action" value="promote">Promote to Vendor</button>
                            </c:otherwise>
                        </c:choose>
                        <button type="submit" name="action" value="delete">Delete</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
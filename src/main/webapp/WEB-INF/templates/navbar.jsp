<%-- WEB-INF/templates/navbar.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav style="background-color: #2c3e50; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 2px 5px rgba(0,0,0,0.1); position: sticky; top: 0; z-index: 1000;">
    <div style="display: flex; align-items: center; gap: 30px;">
        <a href="${pageContext.request.contextPath}/" style="color: white; text-decoration: none; font-weight: bold; font-size: 1.2rem;">Home</a>
        <a href="${pageContext.request.contextPath}/menu" style="color: white; text-decoration: none; font-size: 1.1rem;">Menu</a>
    </div>

    <div>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/user/profile"
                   style="color: white; text-decoration: none; display: flex; align-items: center; gap: 8px; background: rgba(255,255,255,0.1); padding: 8px 15px; border-radius: 20px; transition: all 0.3s ease;">
                    <span style="font-weight: 500;">${sessionScope.user.userName}</span>
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/user/login"
                   style="color: white; text-decoration: none; background: #3498db; padding: 8px 20px; border-radius: 4px; transition: all 0.3s ease;">
                    Login
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="user-management-section">
    <h2>User Management</h2>

    <!-- Role Filter Section -->
    <div class="filter-section">
        <form id="roleFilterForm" method="get" action="${pageContext.request.contextPath}/admin/dashboard">
            <input type="hidden" name="tab" value="user-management">
            <label for="roleFilter">Filter by Role:</label>
            <select id="roleFilter" name="role" onchange="this.form.submit()">
                <option value="all" ${param.role == 'all' || empty param.role ? 'selected' : ''}>All Users</option>
                <option value="admin" ${param.role == 'admin' ? 'selected' : ''}>Admins</option>
                <option value="vendor" ${param.role == 'vendor' ? 'selected' : ''}>Vendors</option>
                <option value="customer" ${param.role == 'customer' ? 'selected' : ''}>Customers</option>
            </select>
        </form>
    </div>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <c:if test="${param.role == 'all' || empty param.role || user.userRole == param.role}">
                    <tr>
                        <td>${user.userName}</td>
                        <td>${user.userMail}</td>
                        <td>${user.userRole}</td>
                        <td>
                            <c:if test="${user.userRole != 'admin'}">
                                <form action="${pageContext.request.contextPath}/admin/dashboard" method="post">
                                    <input type="hidden" name="userId" value="${user.userId}">
                                    <input type="hidden" name="tab" value="user-management">
                                    <!-- Preserve the current role filter when submitting the form -->
                                    <input type="hidden" name="role" value="${param.role}">
                                    <c:choose>
                                        <c:when test="${user.userRole == 'vendor'}">
                                            <button type="submit" name="action" value="demote" class="action-btn demote">Demote to Customer</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" name="action" value="promote" class="action-btn promote">Promote to Vendor</button>
                                        </c:otherwise>
                                    </c:choose>
                                    <button type="submit" name="action" value="delete" class="action-btn delete">Delete</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<style>
    .user-management-section {
        margin: 20px 0;
        padding: 20px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .filter-section {
        margin-bottom: 20px;
    }

    #roleFilter {
        padding: 8px 12px;
        border-radius: 4px;
        border: 1px solid #ddd;
        font-size: 14px;
    }

    .table-container {
        overflow-x: auto;
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        padding: 12px 15px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    th {
        background-color: #f5f5f5;
        font-weight: 600;
    }

    .action-btn {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        margin-right: 5px;
        transition: background-color 0.3s;
    }

    .promote {
        background-color: #4CAF50;
        color: white;
    }

    .promote:hover {
        background-color: #45a049;
    }

    .demote {
        background-color: #ff9800;
        color: white;
    }

    .demote:hover {
        background-color: #e68a00;
    }

    .delete {
        background-color: #f44336;
        color: white;
    }

    .delete:hover {
        background-color: #d32f2f;
    }
</style>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="stats-section">
    <h2>User Statistics</h2>
    <div class="stats-container">
        <div class="stat-card">
            <h3>Total Users</h3>
            <div class="value">${userCount}</div>
        </div>
        <div class="stat-card">
            <h3>Vendors</h3>
            <div class="value">${vendorCount}</div>
        </div>
        <div class="stat-card">
            <h3>Menu Items</h3>
            <div class="value">${menuItemCount}</div>
        </div>
        <div class="stat-card">
            <h3>Total Orders</h3>
            <div class="value">${orderCount}</div>
        </div>
    </div>
</div>
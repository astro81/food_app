<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Admin Dashboard</title>
  <style>
    .dashboard-menu {
      display: flex;
      gap: 20px;
      margin: 20px 0;
    }
    .dashboard-menu a {
      padding: 10px 15px;
      background: #007bff;
      color: white;
      text-decoration: none;
      border-radius: 5px;
    }
  </style>
</head>
<body>
<h1>Admin Dashboard</h1>

<div class="dashboard-menu">
  <a href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
  <a href="${pageContext.request.contextPath}/menu">View Menu</a>
  <a href="${pageContext.request.contextPath}/user/profile">My Profile</a>
</div>

<div class="admin-stats">
  <!-- You can add admin statistics here later -->
</div>
</body>
</html>
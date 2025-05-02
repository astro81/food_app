<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Admin Dashboard</title>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <!-- Google Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@500;600;700&family=Inter:wght@300;400;500&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <style>
    /* Dashboard Layout */
    .dashboard-wrapper {
      width: 100%;
      padding: 2rem;
      box-sizing: border-box;
    }

    .dashboard-header {
      margin-bottom: 1.5rem;
    }

    .dashboard-header h1 {
      font-family: 'Montserrat', serif;
      font-weight: 600;
      margin-bottom: 0.5rem;
    }

    .dashboard-header p {
      color: #555;
      font-family: 'Inter', sans-serif;
      font-weight: 300;
      font-size: 1rem;
    }

    /* Toggle Menu Styles */
    .toggle-menu {
      display: flex;
      margin: 2rem 0;
      background: #f9f9f9;
      border-radius: 8px;
      padding: 0.5rem;
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
    }

    .toggle-btn {
      flex: 1;
      border: none;
      background: none;
      padding: 0.9rem 2rem;
      font-family: 'Inter', sans-serif;
      font-weight: 500;
      font-size: 15px;
      color: #555;
      cursor: pointer;
      border-radius: 6px;
      transition: all 0.3s ease;
      position: relative;
      overflow: hidden;
      z-index: 1;
    }

    .toggle-btn span {
      position: relative;
      z-index: 10;
      transition: color 0.3s;
    }

    .toggle-btn.active {
      color: var(--background, #fff);
    }

    .toggle-btn::before {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: var(--primary, #66ff66);
      opacity: 0;
      transition: opacity 0.3s ease;
      z-index: -1;
    }

    .toggle-btn.active::before {
      opacity: 1;
    }

    .toggle-btn:hover:not(.active)::before {
      opacity: 0.1;
    }

    /* Section Containers */
    .section-content {
      display: none;
      opacity: 0;
      transform: translateY(10px);
      transition: opacity 0.4s ease, transform 0.4s ease;
    }

    .section-content.active {
      display: block;
      opacity: 1;
      transform: translateY(0);
    }

    /* Stats Section Styles */
    .stats-section h2 {
      font-family: 'Montserrat', sans-serif;
      font-weight: 600;
      margin-bottom: 1.5rem;
      color: #333;
    }

    .stats-container {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
      margin-bottom: 20px;
    }

    .stat-card {
      flex: 1;
      min-width: 200px;
      padding: 24px;
      background: white;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.05);
      text-align: center;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .stat-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 6px 16px rgba(0,0,0,0.08);
    }

    .stat-card h3 {
      margin-top: 0;
      color: #555;
      font-family: 'Inter', sans-serif;
      font-weight: 500;
      font-size: 1rem;
    }

    .stat-card .value {
      font-size: 2.2em;
      font-weight: 700;
      color: #333;
      font-family: 'Montserrat', sans-serif;
      margin-top: 0.5rem;
    }

    /* User Management Section Styles */
    .user-management-section h2 {
      font-family: 'Montserrat', sans-serif;
      font-weight: 600;
      margin-bottom: 1.5rem;
      color: #333;
    }

    .table-container {
      background-color: white;
      border-radius: 12px;
      overflow: hidden;
      box-shadow: 0 4px 12px rgba(0,0,0,0.05);
    }

    .user-management-section table {
      width: 100%;
      border-collapse: collapse;
    }

    .user-management-section th,
    .user-management-section td {
      padding: 14px 16px;
      text-align: left;
      font-family: 'Inter', sans-serif;
    }

    .user-management-section th {
      background-color: #f9f9f9;
      color: #555;
      font-weight: 500;
      font-size: 0.9rem;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .user-management-section tr {
      border-bottom: 1px solid #f0f0f0;
    }

    .user-management-section tr:last-child {
      border-bottom: none;
    }

    .user-management-section td {
      color: #333;
      font-size: 0.95rem;
    }

    /* Action Buttons */
    .action-btn {
      padding: 8px 12px;
      border: none;
      border-radius: 6px;
      font-family: 'Inter', sans-serif;
      font-weight: 500;
      font-size: 0.85rem;
      cursor: pointer;
      margin-right: 8px;
      transition: all 0.3s ease;
    }

    .action-btn.promote {
      background-color: #e3f5e3;
      color: #2e7d32;
    }

    .action-btn.promote:hover {
      background-color: #2e7d32;
      color: white;
    }

    .action-btn.demote {
      background-color: #fff3e0;
      color: #e65100;
    }

    .action-btn.demote:hover {
      background-color: #e65100;
      color: white;
    }

    .action-btn.delete {
      background-color: #ffebee;
      color: #c62828;
    }

    .action-btn.delete:hover {
      background-color: #c62828;
      color: white;
    }

    /* Notification Styles */
    .notification {
      padding: 14px 16px;
      margin-bottom: 1.5rem;
      border-radius: 8px;
      background-color: #f8d7da;
      color: #721c24;
      border-left: 4px solid #f5c6cb;
      font-family: 'Inter', sans-serif;
    }

    /* Responsive Design */
    @media (max-width: 990px) {
      .dashboard-wrapper {
        padding: 1.5rem;
      }

      .stats-container {
        flex-direction: column;
      }

      .stat-card {
        min-width: 100%;
      }
    }

    @media (max-width: 768px) {
      .toggle-menu {
        flex-direction: column;
        gap: 10px;
      }

      .toggle-btn {
        width: 100%;
      }

      .user-management-section {
        overflow-x: auto;
      }

      .action-btn {
        padding: 6px 10px;
        font-size: 0.75rem;
      }
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>

<div class="dashboard-wrapper">
  <div class="dashboard-header">
    <h1>Admin Dashboard</h1>
    <p>Manage users and view system statistics</p>
  </div>

  <%@ include file="/WEB-INF/components/notification.jsp" %>

  <div class="toggle-menu">
    <button class="toggle-btn ${param.tab != 'user-management' ? 'active' : ''}" data-target="stats-section"><span>User Statistics</span></button>
    <button class="toggle-btn ${param.tab == 'user-management' ? 'active' : ''}" data-target="user-management-section"><span>User Management</span></button>
  </div>

  <div id="stats-section" class="section-content ${param.tab != 'user-management' ? 'active' : ''}">
    <%@ include file="/WEB-INF/admin/components/user_stats.jsp" %>
  </div>

  <div id="user-management-section" class="section-content ${param.tab == 'user-management' ? 'active' : ''}">
    <%@ include file="/WEB-INF/admin/components/user_management.jsp" %>
  </div>
</div>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    const toggleButtons = document.querySelectorAll('.toggle-btn');

    toggleButtons.forEach(button => {
      button.addEventListener('click', function() {
        // Remove active class from all buttons
        toggleButtons.forEach(btn => btn.classList.remove('active'));

        // Add active class to clicked button
        this.classList.add('active');

        // Hide all sections
        const sections = document.querySelectorAll('.section-content');
        sections.forEach(section => section.classList.remove('active'));

        // Show the target section with animation
        const targetId = this.getAttribute('data-target');
        const targetSection = document.getElementById(targetId);

        // Small delay for smoother transition
        setTimeout(() => {
          targetSection.classList.add('active');
        }, 50);

        // Get the current query parameters (preserving other parameters like 'role')
        const urlParams = new URLSearchParams(window.location.search);

        // Update the tab parameter
        const tabName = targetId === 'user-management-section' ? 'user-management' : 'stats';
        urlParams.set('tab', tabName);

        // Create the new URL with updated parameters
        const newUrl = window.location.pathname + '?' + urlParams.toString();

        // Update browser history without reloading the page
        history.pushState(null, '', newUrl);
      });
    });
  });
</script>
</body>
</html>
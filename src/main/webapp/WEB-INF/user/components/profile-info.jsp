<%-- /WEB-INF/components/profile-info.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .profile-section {
        margin-bottom: 2rem;
        animation-delay: 0.2s;
    }

    .section-title {
        font-size: 1.333rem;
        color: var(--text);
        margin-bottom: 1rem;
        display: flex;
        align-items: center;
        gap: 0.5rem;
    }

    .section-title::before {
        content: "";
        display: inline-block;
        width: 20px;
        height: 20px;
        background-color: var(--primary);
        mask-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2'%3E%3C/path%3E%3Ccircle cx='12' cy='7' r='4'%3E%3C/circle%3E%3C/svg%3E");
        mask-size: contain;
        mask-repeat: no-repeat;
    }

    .user-info {
        background: white;
        padding: 1.5rem;
        border-radius: 12px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        transition: all 0.3s ease;
    }

    .user-info:hover {
        box-shadow: 0 4px 12px rgba(0,0,0,0.12);
    }

    .info-item {
        display: flex;
        margin-bottom: 1rem;
        padding-bottom: 0.75rem;
        border-bottom: 1px solid #f0f0f0;
    }

    .info-item:last-child {
        margin-bottom: 0;
        padding-bottom: 0;
        border-bottom: none;
    }

    .info-label {
        flex: 0 0 100px;
        font-weight: 500;
        color: var(--text);
    }

    .info-value {
        flex: 1;
        color: #555;
    }

    .action-buttons {
        display: flex;
        gap: 1rem;
        margin-top: 1.5rem;
    }

    .edit-form {
        display: none;
        margin-top: 1.5rem;
        padding: 1.5rem;
        background-color: #f9f9f9;
        border-radius: 12px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        animation: fadeIn 0.3s;
    }

    .edit-form form {
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }

    .form-actions {
        display: flex;
        justify-content: space-between;
        margin-top: 1rem;
    }

    .form-actions .left {
        flex: 1;
    }

    .form-actions .right {
        display: flex;
        gap: 0.75rem;
    }

    .delete-account {
        margin-top: 2rem;
        padding-top: 1.5rem;
        border-top: 1px solid #e0e0e0;
    }

    .delete-account p {
        color: #666;
        margin-bottom: 1rem;
        font-size: 0.9rem;
    }

    @media (max-width: 768px) {
        .action-buttons {
            flex-direction: column;
            gap: 0.5rem;
        }

        .action-buttons .btn {
            width: 100%;
        }

        .form-actions {
            flex-direction: column;
            gap: 1rem;
        }

        .form-actions .right {
            flex-direction: column;
        }
    }
</style>

<div class="profile-section fade-in">
    <h3 class="section-title">Personal Information</h3>

    <div class="user-info">
        <div class="info-item">
            <div class="info-label">Name:</div>
            <div class="info-value" id="display-name"><%= user.getUserName() %></div>
        </div>
        <div class="info-item">
            <div class="info-label">Email:</div>
            <div class="info-value"><%= user.getUserMail() %></div>
        </div>
        <div class="info-item">
            <div class="info-label">Phone:</div>
            <div class="info-value" id="display-phone"><%= user.getUserPhone() %></div>
        </div>
        <div class="info-item">
            <div class="info-label">Address:</div>
            <div class="info-value" id="display-address"><%= user.getUserAddress() %></div>
        </div>
    </div>

    <div class="action-buttons">
        <button id="edit-btn" class="btn btn-primary">Edit Profile</button>
        <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-danger">Logout</a>
    </div>

    <div id="edit-form" class="edit-form">
        <form action="${pageContext.request.contextPath}/user/profile" method="post">
            <input type="hidden" name="action" value="update">

            <div class="form-group">
                <label for="user_name">Name:</label>
                <input type="text" id="user_name" name="user_name" value="<%= user.getUserName() %>" required>
            </div>

            <div class="form-group">
                <label for="user_phone">Phone:</label>
                <input type="text" id="user_phone" name="user_phone" value="<%= user.getUserPhone() %>">
            </div>

            <div class="form-group">
                <label for="user_address">Address:</label>
                <textarea id="user_address" name="user_address" rows="3"><%= user.getUserAddress() %></textarea>
            </div>

            <div class="form-group">
                <label for="user_passwd">New Password (leave blank to keep current):</label>
                <input type="password" id="user_passwd" name="user_passwd">
            </div>

            <div class="form-actions">
                <div class="left">
                    <button type="button" id="cancel-btn" class="btn btn-danger">Cancel</button>
                </div>
                <div class="right">
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </div>
        </form>

        <div class="delete-account">
            <p>Deleting your account will permanently remove all your data and cannot be undone.</p>
            <form action="${pageContext.request.contextPath}/user/profile" method="post"
                  onsubmit="return confirm('Are you sure you want to delete your account? This cannot be undone.');">
                <input type="hidden" name="action" value="delete">
                <button type="submit" class="btn btn-danger">Delete Account</button>
            </form>
        </div>
    </div>
</div>

<script>
    document.getElementById('edit-btn').addEventListener('click', function() {
        document.getElementById('edit-form').style.display = 'block';
        this.style.display = 'none';
    });

    document.getElementById('cancel-btn').addEventListener('click', function() {
        document.getElementById('edit-form').style.display = 'none';
        document.getElementById('edit-btn').style.display = 'inline-block';
    });
</script>
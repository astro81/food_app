<%-- /WEB-INF/components/profile-picture.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .profile-picture-section {
        margin-bottom: 2rem;
        padding: 1.5rem;
        border-radius: 12px;
        background-color: rgba(255, 255, 255, 0.7);
        box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        text-align: center;
        position: relative;
        overflow: hidden;
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 1rem;
        transition: all 0.3s ease;
        animation-delay: 0.1s;
    }

    .profile-picture-section:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0,0,0,0.1);
    }

    .profile-picture {
        width: 150px;
        height: 150px;
        border-radius: 50%;
        object-fit: cover;
        border: 4px solid var(--primary);
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        transition: transform 0.3s ease;
    }

    .profile-picture:hover {
        transform: scale(1.05);
    }

    .change-picture-btn {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background-color: var(--secondary);
        color: white;
        border: none;
        cursor: pointer;
        font-size: 1.2rem;
        transition: all 0.2s ease;
        position: relative;
    }

    .change-picture-btn:hover {
        background-color: var(--primary);
        transform: translateY(-2px) scale(1.1);
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    /* Tooltip styles */
    .change-picture-btn::after {
        content: "Change Profile Picture";
        position: absolute;
        bottom: 100%;
        left: 50%;
        transform: translateX(-50%);
        background-color: #333;
        color: #fff;
        padding: 0.5rem 1rem;
        border-radius: 6px;
        font-size: 0.9rem;
        white-space: nowrap;
        opacity: 0;
        visibility: hidden;
        transition: all 0.2s ease;
        pointer-events: none;
    }

    .change-picture-btn:hover::after {
        opacity: 1;
        visibility: visible;
        bottom: calc(100% + 10px);
    }

    /* Icon styles */
    .camera-icon {
        display: inline-block;
        width: 20px;
        height: 20px;
        background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='white' viewBox='0 0 24 24'%3E%3Cpath d='M9 2L7.17 4H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2h-3.17L15 2H9zm3 15c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5z'/%3E%3Ccircle cx='12' cy='12' r='3'/%3E%3C/svg%3E");
        background-repeat: no-repeat;
        background-position: center;
    }
</style>

<div class="profile-picture-section fade-in">
    <img src="${pageContext.request.contextPath}/user/profile-picture"
         alt="Profile Picture" class="profile-picture">

    <button id="change-picture-btn" class="change-picture-btn" aria-label="Change Profile Picture">
        <span class="camera-icon"></span>
    </button>
</div>

<!-- The Modal -->
<div id="pictureModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 class="modal-title">Update Profile Picture</h3>
            <span class="close">&times;</span>
        </div>

        <form id="pictureForm" action="${pageContext.request.contextPath}/user/update-profile-picture"
              method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="update">

            <div class="form-group">
                <label for="profilePicture">Select an image:</label>
                <input type="file" id="profilePicture" name="profilePicture" accept="image/*" required>
            </div>

            <div class="preview-container">
                <img id="imagePreview" src="#" alt="Preview">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="cancelBtn">Cancel</button>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </div>
        </form>
    </div>
</div>

<script>
    // Modal functionality
    const modal = document.getElementById("pictureModal");
    const btn = document.getElementById("change-picture-btn");
    const span = document.getElementsByClassName("close")[0];
    const cancelBtn = document.getElementById("cancelBtn");

    // Open modal when button is clicked
    btn.onclick = function() {
        modal.style.display = "block";
    }

    // Close modal when X is clicked
    span.onclick = function() {
        modal.style.display = "none";
        resetForm();
    }

    // Close modal when cancel button is clicked
    cancelBtn.onclick = function() {
        modal.style.display = "none";
        resetForm();
    }

    // Close modal when clicking outside of it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
            resetForm();
        }
    }

    // Image preview functionality
    document.getElementById('profilePicture').addEventListener('change', function(e) {
        const preview = document.getElementById('imagePreview');
        const file = e.target.files[0];
        const reader = new FileReader();

        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
        }

        if (file) {
            reader.readAsDataURL(file);
        }
    });

    // Reset form when modal is closed
    function resetForm() {
        document.getElementById('pictureForm').reset();
        document.getElementById('imagePreview').style.display = 'none';
        document.getElementById('imagePreview').src = '#';
    }
</script>
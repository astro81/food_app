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
        padding: 0.6rem 1.2rem;
        border-radius: 8px;
        background-color: var(--secondary);
        color: white;
        border: none;
        cursor: pointer;
        font-weight: 500;
        transition: all 0.2s ease;
    }

    .change-picture-btn:hover {
        background-color: var(--primary);
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }
</style>

<div class="profile-picture-section fade-in">
    <img src="${pageContext.request.contextPath}/user/profile-picture"
         alt="Profile Picture" class="profile-picture">

    <button id="change-picture-btn" class="change-picture-btn">Change Profile Picture</button>
</div>

<!-- The Modal -->
<div id="pictureModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 class="modal-title">Update Profile Picture</h3>
            <span class="close">&times;</span>
        </div>

        <form id="pictureForm" action="${pageContext.request.contextPath}/user/profile"
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
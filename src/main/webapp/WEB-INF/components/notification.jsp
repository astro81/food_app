<%-- /WEB-INF/components/notifications.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  /* Notification styles */
  .notification-container {
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 1000;
    max-width: 350px;
    width: 100%;
  }

  .notification-popup {
    position: relative;
    padding: 15px;
    margin-bottom: 15px;
    border-radius: 8px;
    color: var(--background, #fff);
    background: var(--text);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    animation: slideIn 0.5s forwards;
    overflow: hidden;
  }

  .notification-popup.success {
    border-left: 5px solid var(--secondary, #2E7D32);
  }

  .notification-popup.error {
    border-left: 5px solid var(--accent, #C62828);
  }

  .notification-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
    z-index: 1;
  }

  .notification-message {
    flex: 1;
    padding-right: 20px;
    font-size: 14px;
    line-height: 1.4;
  }

  .notification-close {
    background: none;
    border: none;
    color: var(--background, #fff);
    font-size: 20px;
    cursor: pointer;
    padding: 0;
    line-height: 1;
    opacity: 0.8;
    transition: opacity 0.2s;
  }

  .notification-close:hover {
    opacity: 1;
  }

  .notification-progress {
    position: absolute;
    bottom: 0;
    left: 0;
    height: 3px;
    width: 100%;
    background-color: rgba(255, 255, 255, 0.3);
  }

  .notification-popup.success .notification-progress {
    background-color: var(--primary, #2E7D32) !important;
  }

  .notification-popup.error .notification-progress {
    background-color: var(--accent, #C62828) !important;
  }

  /* Animations */
  @keyframes slideIn {
    from {
      transform: translateX(100%);
      opacity: 0;
    }
    to {
      transform: translateX(0);
      opacity: 1;
    }
  }

  @keyframes slideOut {
    from {
      transform: translateX(0);
      opacity: 1;
    }
    to {
      transform: translateX(100%);
      opacity: 0;
    }
  }

  @keyframes progress {
    from {
      width: 100%;
    }
    to {
      width: 0%;
    }
  }
</style>

<c:if test="${not empty NOTIFICATION}">

  <div class="notification-container">
    <div class="notification-popup ${NOTIFICATION_TYPE eq 'SUCCESS' ? 'success' : 'error'}">
      <div class="notification-content">
        <span class="notification-message">${NOTIFICATION}</span>
        <button class="notification-close">&times;</button>
      </div>
      <div class="notification-progress"></div>
    </div>
  </div>

  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const notification = document.querySelector('.notification-popup');
      if (notification) {
        const closeBtn = notification.querySelector('.notification-close');
        const progressBar = notification.querySelector('.notification-progress');

        // Auto-close after 5 seconds
        let timer = setTimeout(() => {
          notification.style.animation = 'slideOut 0.5s forwards';
          setTimeout(() => notification.parentElement.remove(), 500);
        }, 5000);

        // Close button click handler
        closeBtn.addEventListener('click', () => {
          clearTimeout(timer);
          notification.style.animation = 'slideOut 0.5s forwards';
          setTimeout(() => notification.parentElement.remove(), 500);
        });

        // Progress bar animation
        progressBar.style.animation = 'progress 5s linear forwards';
      }
    });
  </script>
</c:if>
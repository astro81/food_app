<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
  .notification-container {
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 9999;
    max-width: 350px;
    width: 100%;
  }

  .notification-popup {
    background-color: #222;
    color: white;
    padding: 16px 20px;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    margin-bottom: 10px;
    position: relative;
    overflow: hidden;
    animation: slideIn 0.4s ease forwards;
    opacity: 0;
    transform: translateX(100%);
  }

  .notification-popup.hide {
    animation: slideOut 0.4s ease forwards;
  }

  .notification-content {
    display: flex;
    align-items: center;
  }

  .notification-icon {
    margin-right: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 24px;
    height: 24px;
    border-radius: 50%;
    background-color: var(--accent, #e51f1f);
    flex-shrink: 0;
  }

  .notification-icon::before {
    font-weight: bold;
    content: "!";
    font-size: 16px;
  }

  .notification-message {
    flex-grow: 1;
    font-size: 14px;
  }

  .notification-close {
    position: absolute;
    top: 12px;
    right: 12px;
    background: none;
    border: none;
    color: rgba(255, 255, 255, 0.6);
    cursor: pointer;
    font-size: 18px;
    line-height: 1;
    padding: 0;
    width: 20px;
    height: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .notification-close:hover {
    color: white;
  }

  .notification-timer {
    position: absolute;
    bottom: 0;
    left: 0;
    height: 3px;
    background-color: var(--accent, #e51f1f);
    width: 100%;
    transform-origin: left;
    animation: timer 5s linear forwards;
  }

  @keyframes slideIn {
    to {
      opacity: 1;
      transform: translateX(0);
    }
  }

  @keyframes slideOut {
    to {
      opacity: 0;
      transform: translateX(100%);
    }
  }

  @keyframes timer {
    to {
      transform: scaleX(0);
    }
  }
</style>

<div class="notification-container" id="notificationContainer">
  <!-- Notifications will be added here dynamically -->
</div>

<script>
  // Function to create and show notification
  function showNotification(message, type = 'error', duration = 5000) {
    const container = document.getElementById('notificationContainer');

    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification-popup ${type}`;

    // Create notification content
    const content = document.createElement('div');
    content.className = 'notification-content';

    // Create icon
    const icon = document.createElement('div');
    icon.className = 'notification-icon';

    // Create message
    const messageEl = document.createElement('div');
    messageEl.className = 'notification-message';
    messageEl.textContent = message;

    // Create close button
    const closeBtn = document.createElement('button');
    closeBtn.className = 'notification-close';
    closeBtn.innerHTML = '×';
    closeBtn.addEventListener('click', () => {
      notification.classList.add('hide');
      setTimeout(() => {
        container.removeChild(notification);
      }, 400);
    });

    // Create timer
    const timer = document.createElement('div');
    timer.className = 'notification-timer';

    // Assemble notification
    content.appendChild(icon);
    content.appendChild(messageEl);
    notification.appendChild(content);
    notification.appendChild(closeBtn);
    notification.appendChild(timer);

    // Add to container
    container.appendChild(notification);

    // Auto remove after duration
    setTimeout(() => {
      if (notification.parentNode === container) {
        notification.classList.add('hide');
        setTimeout(() => {
          if (notification.parentNode === container) {
            container.removeChild(notification);
          }
        }, 400);
      }
    }, duration);
  }

  // Check if there's a notification message from the server
  document.addEventListener('DOMContentLoaded', function() {
    <c:if test="${not empty NOTIFICATION}">
    showNotification("${NOTIFICATION}", "error");
    </c:if>
  });
</script>
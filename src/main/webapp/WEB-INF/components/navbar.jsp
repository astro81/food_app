<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    header {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        position: sticky;
        top: 0;
        left: 0;
        margin: 0;
        z-index: 1000;
        background: transparent !important;
        mix-blend-mode: difference;
    }

    .navbar{
        width: 100dvw;
        padding: 1rem 2rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
        background: transparent !important;
        mix-blend-mode: difference;
    }

    .nav-logo {
        display: flex;
        justify-content: center;
        align-items: center;
        mix-blend-mode: difference;
    }

    .nav-logo .nav-title {
        color: var(--text);
        text-decoration: none;
        font-weight: 500;
        font-size: 1.5rem;
        transition: color 0.3s ease;
    }

    .nav-logo .nav-title:hover {
        color: var(--primary);
    }

    .nav-links{
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 2rem;
    }

    .nav-link {
        color: var(--text);
        text-decoration: none !important;
        font-size: 1rem;
        font-weight: 300 !important;
        position: relative;
        padding: 0.5rem 0;
        transition: opacity 0.3s ease;
        mix-blend-mode: difference;
    }

    .nav-link::after {
        content: '';
        width: 0;
        height: 1px;
        position: absolute;
        bottom: 8px;
        left: 0;
        background-color: var(--primary);
        transition: width 0.3s ease;
    }

    .nav-link:hover::after {
        width: 100%;
    }

    .nav-links:hover .nav-link:not(:hover) {
        opacity: 0.5;
    }
</style>

<header>
    <nav class="navbar">
        <div class="nav-logo">
            <a href="${pageContext.request.contextPath}/" class="nav-title">Food App</a>
        </div>

        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/" class="nav-link">Home</a>
            <a href="${pageContext.request.contextPath}/menu" class="nav-link">Menu</a>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <!-- Show user profile link for all logged in users -->
                    <a href="${pageContext.request.contextPath}/user/profile" class="nav-link">
                            ${sessionScope.user.userName}
                    </a>
                    <!-- Additional admin dashboard link for admin users -->
                    <c:if test="${sessionScope.user.userRole eq 'admin'}">
                        <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link">
                            Admin Dashboard
                        </a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <!-- Only show login button when no user is logged in -->
                    <a href="${pageContext.request.contextPath}/user/login" class="nav-link">
                        Login
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
</header>
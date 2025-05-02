<%-- /WEB-INF/components/order-history.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .order-history-section {
        margin-top: 3rem;
        animation-delay: 0.3s;
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
        mask-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M22 12h-6l-2 3h-4l-2-3H2'%3E%3C/path%3E%3Cpath d='M5.45 5.11L2 12v6a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2v-6l-3.45-6.89A2 2 0 0 0 16.76 4H7.24a2 2 0 0 0-1.79 1.11z'%3E%3C/path%3E%3C/svg%3E");
        mask-size: contain;
        mask-repeat: no-repeat;
    }

    .no-orders {
        background: white;
        padding: 2rem;
        border-radius: 12px;
        text-align: center;
        color: #777;
        box-shadow: 0 2px 8px rgba(0,0,0,0.08);
    }

    .no-orders p {
        margin-bottom: 1rem;
    }

    .order-table-container {
        background: white;
        border-radius: 12px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        overflow: hidden;
    }

    .order-table {
        width: 100%;
        border-collapse: collapse;
    }

    .order-table th, .order-table td {
        padding: 1rem;
        text-align: left;
        border-bottom: 1px solid #f0f0f0;
    }

    .order-table th {
        background-color: #f9f9f9;
        font-weight: 600;
        color: var(--text);
    }

    .order-row {
        transition: background-color 0.2s ease;
    }

    .order-row:hover {
        background-color: #f8f8f8;
    }

    .order-row:last-child td {
        border-bottom: none;
    }

    .order-id {
        font-weight: 500;
        color: var(--secondary);
    }

    .order-date {
        color: #777;
        font-size: 0.95rem;
    }

    .order-total {
        font-weight: 500;
        color: var(--text);
    }

    .order-status {
        display: inline-block;
        padding: 0.35rem 0.75rem;
        border-radius: 20px;
        font-size: 0.85rem;
        font-weight: 500;
    }

    .status-completed {
        background-color: #e6f7e6;
        color: #2e7d32;
    }

    .status-processing {
        background-color: #fff8e1;
        color: #f57c00;
    }

    .status-cancelled {
        background-color: #ffebee;
        color: #c62828;
    }

    .order-actions {
        display: flex;
        gap: 0.5rem;
    }

    .order-actions .btn {
        padding: 0.5rem 0.75rem;
        font-size: 0.85rem;
    }

    .order-actions form {
        display: inline;
    }

    @media (max-width: 768px) {
        .order-table-container {
            overflow-x: auto;
        }

        .order-table {
            min-width: 600px;
        }

        .order-actions {
            flex-direction: column;
            gap: 0.5rem;
        }

        .order-actions .btn {
            width: 100%;
            margin-right: 0;
            margin-bottom: 0.5rem;
        }
    }
</style>

<div class="order-history-section fade-in">
    <h3 class="section-title">Order History</h3>

    <c:choose>
        <c:when test="${empty orderHistory}">
            <div class="no-orders">
                <p>You don't have any completed orders yet.</p>
                <a href="${pageContext.request.contextPath}/menu" class="btn btn-primary">Browse Menu</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="order-table-container">
                <table class="order-table">
                    <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Date</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orderHistory}">
                        <tr class="order-row">
                            <td class="order-id">#${order.orderId}</td>
                            <td class="order-date">
                                <fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy HH:mm" />
                            </td>
                            <td class="order-total">
                                $<fmt:formatNumber value="${order.total}" pattern="#,##0.00" />
                            </td>
                            <td>
                                    <span class="order-status status-${order.status.toLowerCase()}">
                                            ${order.status}
                                    </span>
                            </td>
                            <td>
                                <div class="order-actions">
                                    <a href="${pageContext.request.contextPath}/order/details?orderId=${order.orderId}"
                                       class="btn btn-primary">View Details</a>
                                    <form action="${pageContext.request.contextPath}/user/profile" method="post"
                                          onsubmit="return confirm('Are you sure you want to delete this order? This cannot be undone.');">
                                        <input type="hidden" name="action" value="deleteOrder">
                                        <input type="hidden" name="orderId" value="${order.orderId}">
                                        <button type="submit" class="btn btn-danger">Delete</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>
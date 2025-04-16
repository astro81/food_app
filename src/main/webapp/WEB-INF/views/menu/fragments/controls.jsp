<c:if test="${sessionScope.user.userRole == 'admin'}">
    <div class="admin-controls">
        <a href="${pageContext.request.contextPath}/menu/edit?food_id=${item.foodId}" class="button">Edit</a>
        <form action="${pageContext.request.contextPath}/menu/delete" method="post" style="display:inline;">
            <input type="hidden" name="food_id" value="${item.foodId}">
            <button type="submit" class="button delete" onclick="return confirm('Are you sure you want to delete this item?')">Delete</button>
        </form>
    </div>
</c:if>
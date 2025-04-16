<div class="filter-form">
    <form action="${pageContext.request.contextPath}/menu/filter" method="get">
        <label for="filterCategory">Filter by Category:</label>
        <select name="filterCategory" id="filterCategory">
            <option value="">All Categories</option>
            <option value="meals" ${filterCategory == 'meals' ? 'selected' : ''}>Meals</option>
            <option value="snacks" ${filterCategory == 'snacks' ? 'selected' : ''}>Snacks</option>
            <option value="sweets" ${filterCategory == 'sweets' ? 'selected' : ''}>Sweets</option>
            <option value="drinks" ${filterCategory == 'drinks' ? 'selected' : ''}>Drinks</option>
        </select>

        <label for="filterAvailability">Filter by Availability:</label>
        <select name="filterAvailability" id="filterAvailability">
            <option value="">All Items</option>
            <option value="available" ${filterAvailability == 'available' ? 'selected' : ''}>Available</option>
            <option value="out_of_order" ${filterAvailability == 'out_of_order' ? 'selected' : ''}>Out of Order</option>
        </select>

        <button type="submit" class="button">Apply Filter</button>
    </form>
</div>
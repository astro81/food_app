<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Add Menu Item</title>
  <style>
    .success { color: green; }
    .error { color: red; }
    form {
      max-width: 500px;
      margin: 20px auto;
      padding: 20px;
      border: 1px solid #ddd;
      border-radius: 5px;
    }
    label {
      display: block;
      margin-bottom: 10px;
    }
    input, select {
      width: 100%;
      padding: 8px;
      margin-bottom: 15px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    button {
      background-color: #4CAF50;
      color: white;
      padding: 10px 15px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>
<h1>Add New Menu Item</h1>

<!-- Display success/error messages -->
<c:if test="${not empty successMessage}">
  <p class="success">${successMessage}</p>
  <c:remove var="successMessage" scope="session"/>
</c:if>
<c:if test="${not empty errorMessage}">
  <p class="error">${errorMessage}</p>
  <c:remove var="errorMessage" scope="session"/>
</c:if>

<form action="${pageContext.request.contextPath}/menu" method="post">
  <input type="hidden" name="action" value="create">
  <label>Name: <input type="text" name="food_name" required></label>
  <label>Description: <input type="text" name="food_description"></label>
  <label>Price: <input type="number" step="0.01" name="food_price" required></label>
  <label>Category:
    <select name="food_category" required>
      <option value="meals">Meals</option>
      <option value="snacks">Snacks</option>
      <option value="sweets">Sweets</option>
      <option value="drinks">Drinks</option>
    </select>
  </label>
  <label>Availability:
    <select name="food_availability" required>
      <option value="available">Available</option>
      <option value="out_of_order">Out of Order</option>
    </select>
  </label>
  <button type="submit">Add Item</button>
  <a href="${pageContext.request.contextPath}/menu" style="margin-left: 10px;">Cancel</a>
</form>
</body>
</html>
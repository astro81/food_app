CREATE TABLE MenuItem(
    food_id INT PRIMARY KEY AUTO_INCREMENT,
    food_name VARCHAR(50) NOT NULL,
    food_description TEXT,
    food_price DECIMAL(10, 2),
    food_category ENUM('meals', 'snacks', 'sweets', 'drinks'),
    food_availability ENUM('available', 'out_of_order'),
    food_image VARCHAR(255),
    vendor_id INT REFERENCES users(user_id)
);
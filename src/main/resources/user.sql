CREATE TABLE users(
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) UNIQUE  NOT NULL,
    user_mail VARCHAR(50) UNIQUE NOT NULL,
    user_passwd VARCHAR(255) NOT NULL,
    user_phone VARCHAR(10) UNIQUE NOT NULL,
    user_address VARCHAR(100) NOT NULL,
    user_role ENUM('admin', 'customer', 'vendor') DEFAULT 'customer' NOT NULL,
    profile_picture LONGBLOB DEFAULT NULL
);

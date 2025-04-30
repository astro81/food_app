<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@700&family=Inter:wght@400&display=swap" rel="stylesheet">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

    <title>Register | Food App</title>
    <style>
        .wrapper {
            width: 100dvw;
            height: 100dvh;
            overflow: hidden;
            display: flex;
            position: absolute;
            top: 0;
            left: 0;
        }

        .register-container {
            width: 50%;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 0 12vw;
            overflow-y: auto;
        }

        .image-container {
            width: 50%;
            height: 100%;
            background-color: var(--background, #f0f0f0);
            overflow: hidden;
            border-top-left-radius: 40px;
            border-bottom-left-radius: 40px;
            position: relative;
        }

        .image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            object-position: center;
        }

        .register-header {
            margin-bottom: 1.5rem;
        }

        .register-header h2 {
            font-family: 'Montserrat', serif;
            font-weight: 600;
            margin-bottom: 0.5rem;
        }

        .register-header p {
            color: var(--text, #000);
            font-family: 'Inter', sans-serif;
            font-weight: 300;
            font-size: 1rem;
        }

        .input-box {
            margin-bottom: 0.8rem;
        }

        .input-box label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
        }

        .input-field {
            width: 100%;
            padding: 14px 16px;
            font-size: 1rem;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            background-color: var(--background, #fff);
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        .input-field:focus {
            outline: none;
            border-color: var(--secondary);
            box-shadow: 0 0 0 3px rgba(85, 124, 85, 0.15);
        }

        textarea.input-field {
            resize: vertical;
            min-height: 100px;
        }

        .input-submit {
            margin-top: 1rem;
            margin-bottom: 1.5rem;
        }

        /* Animated button style */
        .submit-btn {
            outline: none;
            cursor: pointer;
            border: none;
            width: 100%;
            padding: 0.9rem 2rem;
            margin: 0;
            font-family: inherit;
            position: relative;
            display: inline-block;
            letter-spacing: 0.05rem;
            font-weight: 700;
            font-size: 17px;
            border-radius: 8px;
            overflow: hidden;
            background: var(--primary, #66ff66);
            color: var(--background, ghostwhite);
        }

        .submit-btn span {
            position: relative;
            z-index: 10;
            transition: color 0.4s;
        }

        .submit-btn:hover span {
            color: black;
        }

        .submit-btn::before {
            content: "";
            position: absolute;
            top: 0;
            left: -10%;
            width: 120%;
            height: 100%;
            background: #000;
            z-index: 0;
            transform: skew(30deg);
            transition: transform 0.4s cubic-bezier(0.3, 1, 0.8, 1);
        }

        .submit-btn:hover::before {
            transform: translate3d(100%, 0, 0);
        }

        .divider {
            display: flex;
            align-items: center;
            margin: 1.5rem 0;
            color: #777;
        }

        .divider::before,
        .divider::after {
            content: "";
            flex: 1;
            border-bottom: 1px solid #e0e0e0;
        }

        .divider span {
            padding: 0 1rem;
            font-size: 0.9rem;
        }

        .login-link {
            text-align: center;
        }

        .login-link p {
            color: #555;
            margin: 0;
        }

        .login-link a {
            color: var(--secondary);
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
            margin-left: 5px;
        }

        .login-link a:hover {
            color: var(--primary);
            text-decoration: underline;
        }

        /* Form validation styles */
        .error-message {
            color: #e74c3c;
            font-size: 0.8rem;
            margin-top: 0.3rem;
            display: block;
            height: 1rem;
            transition: all 0.3s ease;
        }

        .input-field.error {
            border-color: #e74c3c;
            box-shadow: 0 0 0 2px rgba(231, 76, 60, 0.15);
        }

        .input-field.success {
            border-color: #2ecc71;
            box-shadow: 0 0 0 2px rgba(46, 204, 113, 0.15);
        }

        /* Notification styles */
        .notification {
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
            font-weight: 500;
            text-align: center;
        }

        .notification.success {
            background-color: rgba(46, 204, 113, 0.15);
            color: #2ecc71;
            border: 1px solid #2ecc71;
        }

        .notification.error {
            background-color: rgba(231, 76, 60, 0.15);
            color: #e74c3c;
            border: 1px solid #e74c3c;
        }

        /* Responsive design */
        @media (max-width: 990px) {
            .register-container {
                padding: 0 4rem;
            }
        }

        @media (max-width: 768px) {
            .wrapper {
                flex-direction: column;
            }

            .register-container {
                width: 100%;
                padding: 2rem;
                order: 2;
                height: auto;
                min-height: 70vh;
            }

            .image-container {
                width: 100%;
                height: 30%;
                order: 1;
                border-radius: 0 0 40px 40px;
            }
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>

<div class="wrapper">
    <div class="register-container">
        <!-- Import notification component -->
        <%@ include file="/WEB-INF/components/notification.jsp" %>

        <div class="register-header">
            <h2>Create account</h2>
            <p>Join us today and enjoy delicious food delivered to your doorstep</p>
        </div>

        <form action="${pageContext.request.contextPath}/user/register" method="post" id="registerForm">
            <div class="input-box">
                <label for="userName">Full Name</label>
                <input
                        type="text"
                        class="input-field"
                        id="userName"
                        name="user_name"
                        placeholder="Jon Doe"
                        required
                >
                <small class="error-message" id="name-error"></small>
            </div>

            <div class="input-box">
                <label for="userMail">Email Address</label>
                <input
                        type="email"
                        class="input-field"
                        id="userMail"
                        name="user_mail"
                        placeholder="jon.doe@email.com"
                        required
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                >
                <small class="error-message" id="email-error"></small>
            </div>

            <div class="input-box">
                <label for="userPasswd">Password</label>
                <input
                        type="password"
                        class="input-field"
                        id="userPasswd"
                        name="user_passwd"
                        placeholder="••••••••"
                        required
                        minlength="3"
                >
                <small class="error-message" id="password-error"></small>
            </div>

            <div class="input-box">
                <label for="userPhone">Phone Number</label>
                <input
                        type="text"
                        class="input-field"
                        id="userPhone"
                        name="user_phone"
                        placeholder="+977 9812131415"
                        required
                >
                <small class="error-message" id="phone-error"></small>
            </div>

            <div class="input-box">
                <label for="userAddress">Delivery Address</label>
                <textarea
                        class="input-field"
                        id="userAddress"
                        name="user_address"
                        placeholder="123 Main St, Apt 4B, City, State, ZIP"
                        required
                ></textarea>
                <small class="error-message" id="address-error"></small>
            </div>

            <div class="input-submit">
                <button type="submit" class="submit-btn" id="submit"><span>Create Account</span></button>
            </div>
        </form>

        <script>
            document.addEventListener('DOMContentLoaded', function() {
                const form = document.getElementById('registerForm');
                const nameInput = document.getElementById('userName');
                const emailInput = document.getElementById('userMail');
                const passwordInput = document.getElementById('userPasswd');
                const phoneInput = document.getElementById('userPhone');
                const addressInput = document.getElementById('userAddress');

                const nameError = document.getElementById('name-error');
                const emailError = document.getElementById('email-error');
                const passwordError = document.getElementById('password-error');
                const phoneError = document.getElementById('phone-error');
                const addressError = document.getElementById('address-error');

                // Email validation regex
                const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                // Phone validation regex (basic format)
                const phoneRegex = /^[+]?[(]?[0-9]{1,4}[)]?[-\s.]?[0-9]{1,4}[-\s.]?[0-9]{1,9}$/;

                // Function to validate name
                function validateName() {
                    const name = nameInput.value.trim();
                    if (name === '') {
                        nameError.textContent = 'Full name is required';
                        nameInput.classList.add('error');
                        nameInput.classList.remove('success');
                        return false;
                    } else if (name.length < 2) {
                        nameError.textContent = 'Name must be at least 2 characters';
                        nameInput.classList.add('error');
                        nameInput.classList.remove('success');
                        return false;
                    } else {
                        nameError.textContent = '';
                        nameInput.classList.remove('error');
                        nameInput.classList.add('success');
                        return true;
                    }
                }

                // Function to validate email
                function validateEmail() {
                    const email = emailInput.value.trim();
                    if (email === '') {
                        emailError.textContent = 'Email is required';
                        emailInput.classList.add('error');
                        emailInput.classList.remove('success');
                        return false;
                    } else if (!emailRegex.test(email)) {
                        emailError.textContent = 'Please enter a valid email address';
                        emailInput.classList.add('error');
                        emailInput.classList.remove('success');
                        return false;
                    } else {
                        emailError.textContent = '';
                        emailInput.classList.remove('error');
                        emailInput.classList.add('success');
                        return true;
                    }
                }

                // Function to validate password
                function validatePassword() {
                    const password = passwordInput.value.trim();
                    if (password === '') {
                        passwordError.textContent = 'Password is required';
                        passwordInput.classList.add('error');
                        passwordInput.classList.remove('success');
                        return false;
                    } else if (password.length < 3) {
                        passwordError.textContent = 'Password must be at least 3 characters';
                        passwordInput.classList.add('error');
                        passwordInput.classList.remove('success');
                        return false;
                    } else {
                        passwordError.textContent = '';
                        passwordInput.classList.remove('error');
                        passwordInput.classList.add('success');
                        return true;
                    }
                }

                // Function to validate phone
                function validatePhone() {
                    const phone = phoneInput.value.trim();
                    if (phone === '') {
                        phoneError.textContent = 'Phone number is required';
                        phoneInput.classList.add('error');
                        phoneInput.classList.remove('success');
                        return false;
                    } else if (!phoneRegex.test(phone)) {
                        phoneError.textContent = 'Please enter a valid phone number';
                        phoneInput.classList.add('error');
                        phoneInput.classList.remove('success');
                        return false;
                    } else {
                        phoneError.textContent = '';
                        phoneInput.classList.remove('error');
                        phoneInput.classList.add('success');
                        return true;
                    }
                }

                // Function to validate address
                function validateAddress() {
                    const address = addressInput.value.trim();
                    if (address === '') {
                        addressError.textContent = 'Address is required';
                        addressInput.classList.add('error');
                        addressInput.classList.remove('success');
                        return false;
                    } else if (address.length < 10) {
                        addressError.textContent = 'Please enter a complete address';
                        addressInput.classList.add('error');
                        addressInput.classList.remove('success');
                        return false;
                    } else {
                        addressError.textContent = '';
                        addressInput.classList.remove('error');
                        addressInput.classList.add('success');
                        return true;
                    }
                }

                // Real-time validation for all fields
                nameInput.addEventListener('input', validateName);
                nameInput.addEventListener('blur', validateName);

                emailInput.addEventListener('input', validateEmail);
                emailInput.addEventListener('blur', validateEmail);

                passwordInput.addEventListener('input', validatePassword);
                passwordInput.addEventListener('blur', validatePassword);

                phoneInput.addEventListener('input', validatePhone);
                phoneInput.addEventListener('blur', validatePhone);

                addressInput.addEventListener('input', validateAddress);
                addressInput.addEventListener('blur', validateAddress);

                // Form submission
                form.addEventListener('submit', function(event) {
                    // Validate all fields
                    const isNameValid = validateName();
                    const isEmailValid = validateEmail();
                    const isPasswordValid = validatePassword();
                    const isPhoneValid = validatePhone();
                    const isAddressValid = validateAddress();

                    // Prevent form submission if validation fails
                    if (!isNameValid || !isEmailValid || !isPasswordValid || !isPhoneValid || !isAddressValid) {
                        event.preventDefault();
                    }
                });
            });
        </script>

        <div class="divider">
            <span>Or</span>
        </div>

        <div class="login-link">
            <p>Already have an account?<a href="${pageContext.request.contextPath}/user/login">Login</a></p>
        </div>
    </div>

    <div class="image-container">
        <img src="${pageContext.request.contextPath}/images/food-delivery.jpg" alt="Food delivery image" onerror="this.src='https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?q=80&w=1470&auto=format&fit=crop'">
    </div>
</div>
</body>
</html>
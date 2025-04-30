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

    <title>Login | Food App</title>
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

        .login-container {
            width: 50%;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 0 12vw;
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

        .login-header {
            margin-bottom: 1.5rem;
        }

        .login-header h2 {
            font-family: 'Montserrat', serif;
            font-weight: 600;
            margin-bottom: 0.5rem;
        }

        .login-header p {
            color: var(--text, #000);
            font-family: 'Inter', sans-serif;
            font-weight: 300;
            font-size: 1rem;
        }

        .input-box {
            margin-bottom: 1.5rem;
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
        }

        .input-field:focus {
            outline: none;
            border-color: var(--secondary);
            box-shadow: 0 0 0 3px rgba(85, 124, 85, 0.15);
        }

        .forgot-password {
            text-align: right;
            margin-top: -1rem;
            margin-bottom: 1rem;
        }

        .forgot-password a {
            color: var(--secondary);
            text-decoration: none;
            font-size: 0.85rem;
            transition: color 0.3s ease;
        }

        .forgot-password a:hover {
            color: var(--primary);
            text-decoration: underline;
        }

        .remember-me {
            display: flex;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        .remember-me input[type="checkbox"] {
            margin-right: 8px;
            accent-color: var(--secondary);
        }

        .remember-me label {
            font-size: 0.9rem;
            color: #555;
        }

        .input-submit {
            margin-bottom: 1.5rem;
        }

        /* New animated button style */
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

        .sign-up-link {
            text-align: center;
        }

        .sign-up-link p {
            color: #555;
            margin: 0;
        }

        .sign-up-link a {
            color: var(--secondary);
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
            margin-left: 5px;
        }

        .sign-up-link a:hover {
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

        /* Responsive design */
        @media (max-width: 990px) {
            .login-container {
                padding: 0 4rem;
            }
        }

        @media (max-width: 768px) {
            .wrapper {
                flex-direction: column;
            }

            .login-container {
                width: 100%;
                padding: 2rem;
                order: 2;
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
    <div class="login-container">
        <!-- Import notification component -->
        <%@ include file="/WEB-INF/components/notification.jsp" %>

        <div class="login-header">
            <h2>Welcome back!</h2>
            <p>Enter your credentials to access your account</p>
        </div>

        <form action="${pageContext.request.contextPath}/user/login" method="post" id="loginForm">
            <div class="input-box">
                <label for="email">Email address</label>
                <input
                        type="email"
                        class="input-field"
                        id="email"
                        name="user_mail"
                        placeholder="jon.doe@email.com"
                        required
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                >
                <small class="error-message" id="email-error"></small>
            </div>

            <div class="input-box">
                <label for="password">Password</label>
                <input
                        type="password"
                        class="input-field"
                        id="password"
                        name="user_passwd"
                        placeholder="••••••••"
                        required
                        minlength="3"
                >
                <small class="error-message" id="password-error"></small>
            </div>

            <div class="forgot-password">
                <a href="${pageContext.request.contextPath}/user/forgot-password">Forgot password?</a>
            </div>

            <div class="remember-me">
                <input type="checkbox" id="remember" name="remember" value="true">
                <label for="remember">Remember for 30 days</label>
            </div>

            <div class="input-submit">
                <button type="submit" class="submit-btn" id="submit"><span>Login</span></button>
            </div>
        </form>

        <script>
            document.addEventListener('DOMContentLoaded', function() {
                const form = document.getElementById('loginForm');
                const emailInput = document.getElementById('email');
                const passwordInput = document.getElementById('password');
                const emailError = document.getElementById('email-error');
                const passwordError = document.getElementById('password-error');

                // Email validation regex
                const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

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

                // Real-time validation for email
                emailInput.addEventListener('input', validateEmail);
                emailInput.addEventListener('blur', validateEmail);

                // Real-time validation for password
                passwordInput.addEventListener('input', validatePassword);
                passwordInput.addEventListener('blur', validatePassword);

                // Form submission
                form.addEventListener('submit', function(event) {
                    // Validate all fields
                    const isEmailValid = validateEmail();
                    const isPasswordValid = validatePassword();

                    // Prevent form submission if validation fails
                    if (!isEmailValid || !isPasswordValid) {
                        event.preventDefault();
                    }
                });
            });
        </script>

        <div class="divider">
            <span>Or</span>
        </div>

        <div class="sign-up-link">
            <p>Don't have an account?<a href="${pageContext.request.contextPath}/user/register">Sign Up</a></p>
        </div>
    </div>

    <div class="image-container">
        <img src="${pageContext.request.contextPath}/images/monstera-leaf.jpg" alt="Monstera plant leaves" onerror="this.src='https://images.unsplash.com/photo-1604762524889-3e2fcc145683?q=80&w=1471&auto=format&fit=crop'">
    </div>
</div>
</body>
</html>
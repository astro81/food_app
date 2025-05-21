<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OrderNow - Enhanced Footer</title>
    <style>
        :root {
            --text: #222;
            --primary: #e9e8e0;
            --accent: #d64123;
            --light-accent: #f8f4ea;
        }

        body {
            margin: 0;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
            line-height: 1.6;
        }

        /* Enhanced Footer */
        .footer {
            padding: 4rem 2rem 2rem;
            background-color: var(--primary);
            border-top: 1px solid rgba(0, 0, 0, 0.1);
            position: relative;
            overflow: hidden;
        }

        /* Subtle background pattern */
        .footer::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-image: radial-gradient(circle at 10% 20%, rgba(0,0,0,0.02) 0%, transparent 8%),
            radial-gradient(circle at 80% 60%, rgba(0,0,0,0.03) 0%, transparent 6%);
            background-size: 60px 60px;
            opacity: 0.6;
            z-index: 0;
        }

        .footer-top {
            display: flex;
            justify-content: space-between;
            margin-bottom: 3rem;
            position: relative;
            z-index: 1;
        }

        .footer-left,
        .footer-center,
        .footer-right {
            display: flex;
            flex-direction: column;
        }

        .footer-links {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .footer-link {
            text-decoration: none;
            color: var(--text);
            font-size: 0.95rem;
            display: flex;
            align-items: center;
            transition: transform 0.3s ease, color 0.3s ease;
            width: fit-content;
        }

        .footer-link:hover {
            color: var(--accent);
            transform: translateX(5px);
        }

        .footer-link svg {
            margin-right: 8px;
            opacity: 0.7;
            transition: opacity 0.3s ease;
        }

        .footer-link:hover svg {
            opacity: 1;
        }

        .footer-logo {
            font-size: 2rem;
            margin: 0;
            position: relative;
            font-weight: 700;
            letter-spacing: 1px;
            transition: transform 0.3s ease;
        }

        .footer-logo:hover {
            transform: scale(1.05);
        }

        .footer-logo::after {
            content: "";
            position: absolute;
            bottom: -6px;
            left: 0;
            width: 60%;
            height: 3px;
            background-color: var(--accent);
            transform: scaleX(0);
            transform-origin: left;
            transition: transform 0.4s ease;
        }

        .footer-logo:hover::after {
            transform: scaleX(1);
        }

        .footer-center {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .footer-tagline {
            margin-top: 8px;
            font-size: 0.85rem;
            color: #666;
            font-style: italic;
        }

        .newsletter {
            margin-bottom: 0.8rem;
            font-size: 1rem;
            font-weight: 500;
        }

        .newsletter-form {
            display: flex;
            position: relative;
            max-width: 300px;
        }

        .newsletter-input {
            padding: 0.7rem 0.5rem 0.7rem 2.5rem;
            border: 1px solid rgba(0,0,0,0.2);
            border-radius: 6px 0 0 6px;
            background: rgba(255,255,255,0.6);
            width: 100%;
            transition: all 0.3s ease;
            font-size: 0.9rem;
        }

        .newsletter-input:focus {
            outline: none;
            background: rgba(255,255,255,0.9);
            border-color: var(--accent);
            box-shadow: 0 0 0 3px rgba(214, 65, 35, 0.1);
        }

        .newsletter-email-icon {
            position: absolute;
            left: 10px;
            top: 50%;
            transform: translateY(-50%);
            color: #666;
            z-index: 1;
        }

        .newsletter-button {
            padding: 0.7rem 1rem;
            background: var(--text);
            color: white;
            border: none;
            border-radius: 0 6px 6px 0;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .newsletter-button:hover {
            background: var(--accent);
            transform: translateX(3px);
        }

        .newsletter-button svg {
            transition: transform 0.3s ease;
        }

        .newsletter-button:hover svg {
            transform: translateX(4px);
        }

        .footer-bottom {
            display: flex;
            justify-content: center;
            align-items: center;
            padding-top: 2rem;
            border-top: 1px solid rgba(0, 0, 0, 0.1);
            position: relative;
            z-index: 1;
        }

        .social-links {
            display: flex;
            gap: 1.5rem;
            justify-content: center;
            flex-wrap: wrap;
        }

        .social-link {
            text-decoration: none;
            color: var(--text);
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            gap: 6px;
            transition: all 0.3s ease;
            padding: 8px 12px;
            border-radius: 6px;
        }

        .social-link:hover {
            background-color: rgba(255,255,255,0.6);
            transform: translateY(-3px);
            color: var(--accent);
        }

        .social-icon {
            transition: transform 0.4s ease;
        }

        .social-link:hover .social-icon {
            transform: scale(1.2);
        }

        .copyright {
            margin-top: 1.5rem;
            text-align: center;
            font-size: 0.8rem;
            color: #666;
        }

        @keyframes pulse {
            0% { opacity: 0.7; }
            50% { opacity: 1; }
            100% { opacity: 0.7; }
        }

        @media (max-width: 768px) {
            .footer-top {
                flex-direction: column;
                gap: 2rem;
                align-items: center;
                text-align: center;
            }

            .footer-links {
                align-items: center;
            }

            .footer-link:hover {
                transform: translateY(-3px);
            }

            .social-links {
                margin-top: 1rem;
            }
        }
    </style>
</head>
<body>
<!-- Enhanced Footer -->
<footer class="footer">
    <div class="footer-top">
        <div class="footer-left">
            <nav class="footer-links">
                <a href="${pageContext.request.contextPath}/menu" class="footer-link">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M3 3h18v18H3z"/>
                        <path d="M3 9h18"/>
                        <path d="M3 15h18"/>
                        <path d="M9 21V9"/>
                        <path d="M15 21V9"/>
                    </svg>
                    Menu
                </a>
                <a href="#contact" class="footer-link">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
                    </svg>
                    Contact
                </a>
            </nav>
        </div>

        <div class="footer-center">
            <h2 class="footer-logo">OrderNow</h2>
            <p class="footer-tagline">Delicious food, delivered</p>
        </div>

        <div class="footer-right">
            <p class="newsletter">Sign up to our newsletter</p>
            <div class="newsletter-form">
          <span class="newsletter-email-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
              <polyline points="22,6 12,13 2,6"/>
            </svg>
          </span>
                <input type="email" placeholder="Your email" class="newsletter-input">
                <button class="newsletter-button">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="5" y1="12" x2="19" y2="12"/>
                        <polyline points="12 5 19 12 12 19"/>
                    </svg>
                </button>
            </div>
        </div>
    </div>

    <div class="footer-bottom">
        <div>
            <div class="social-links">
                <a href="#" class="social-link">
            <span class="social-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"/>
              </svg>
            </span>
                    Facebook
                </a>
                <a href="#" class="social-link">
            <span class="social-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <rect x="2" y="2" width="20" height="20" rx="5" ry="5"/>
                <path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"/>
                <line x1="17.5" y1="6.5" x2="17.5" y2="6.5"/>
              </svg>
            </span>
                    Instagram
                </a>
                <a href="#" class="social-link">
            <span class="social-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M22 4s-.7 2.1-2 3.4c1.6 10-9.4 17.3-18 11.6 2.2.1 4.4-.6 6-2C3 15.5.5 9.6 3 5c2.2 2.6 5.6 4.1 9 4-.9-4.2 4-6.6 7-3.8 1.1 0 3-1.2 3-1.2z"/>
              </svg>
            </span>
                    Twitter
                </a>
                <a href="#" class="social-link">
            <span class="social-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M22.54 6.42a2.78 2.78 0 0 0-1.94-2C18.88 4 12 4 12 4s-6.88 0-8.6.46a2.78 2.78 0 0 0-1.94 2A29 29 0 0 0 1 11.75a29 29 0 0 0 .46 5.33A2.78 2.78 0 0 0 3.4 19c1.72.46 8.6.46 8.6.46s6.88 0 8.6-.46a2.78 2.78 0 0 0 1.94-2 29 29 0 0 0 .46-5.25 29 29 0 0 0-.46-5.33z"/>
                <polygon points="9.75 15.02 15.5 11.75 9.75 8.48 9.75 15.02"/>
              </svg>
            </span>
                    YouTube
                </a>
            </div>
            <p class="copyright">© 2025 OrderNow. All rights reserved.</p>
        </div>
    </div>
</footer>

</body>
</html>
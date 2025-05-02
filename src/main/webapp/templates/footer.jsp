<!-- Footer -->
<footer class="footer">
    <div class="footer-top">
        <div class="footer-left">
            <nav class="footer-links">
                <a href="${pageContext.request.contextPath}/menu" class="footer-link">Menu</a>
                <a href="${pageContext.request.contextPath}/team" class="footer-link">Team</a>
                <a href="${pageContext.request.contextPath}/events" class="footer-link">Events</a>
                <a href="${pageContext.request.contextPath}/contact" class="footer-link">Contact</a>
            </nav>
        </div>

        <div class="footer-center">
            <h2 class="footer-logo">Cibo gustoso</h2>
        </div>

        <div class="footer-right">
            <p class="newsletter">Sign up to our newsletter</p>
            <div class="newsletter-form">
                <input type="email" placeholder="Your email" class="newsletter-input">
                <button class="newsletter-button">→</button>
            </div>
        </div>
    </div>

    <div class="footer-bottom">
        <div class="social-links">
            <a href="#" class="social-link">Facebook</a>
            <a href="#" class="social-link">Instagram</a>
            <a href="#" class="social-link">Twitter</a>
            <a href="#" class="social-link">YouTube</a>
        </div>
    </div>

    <style>
        /* Footer */
        .footer {
            padding: 4rem 2rem 2rem;
            background-color: #e9e8e0;
            border-top: 1px solid rgba(0, 0, 0, 0.1);
        }

        .footer-top {
            display: flex;
            justify-content: space-between;
            margin-bottom: 3rem;
        }

        .footer-links {
            display: flex;
            gap: 1.5rem;
        }

        .footer-link {
            text-decoration: none;
            color: var(--text);
            font-size: 0.9rem;
        }

        .footer-logo {
            font-size: 1.5rem;
        }

        .newsletter {
            margin-bottom: 0.5rem;
            font-size: 0.9rem;
        }

        .newsletter-form {
            display: flex;
        }

        .newsletter-input {
            padding: 0.5rem;
            border: 1px solid var(--text);
            border-right: none;
            background: none;
        }

        .newsletter-button {
            padding: 0.5rem 1rem;
            background: var(--text);
            color: white;
            border: none;
            cursor: pointer;
        }

        .footer-bottom {
            display: flex;
            justify-content: center;
            padding-top: 2rem;
            border-top: 1px solid rgba(0, 0, 0, 0.1);
        }

        .social-links {
            display: flex;
            gap: 1.5rem;
        }

        @media (max-width: 768px) {
            .footer-top {
                flex-direction: column;
                gap: 2rem;
                align-items: center;
                text-align: center;
            }
        }
    </style>
</footer>
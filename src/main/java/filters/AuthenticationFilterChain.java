package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Composite filter that chains together multiple security-related filters.
 * Applies authentication, authorization, and session timeout checks in sequence.
 * Processes all requests (/*) through the filter chain.
 */
@WebFilter("/*")
public class AuthenticationFilterChain implements Filter {

    // List of filters to be executed in sequence
    private final List<Filter> filters = new ArrayList<>();

    /**
     * Initializes the filter chain by adding all security filters.
     *
     * @param filterConfig The filter configuration object
     * @throws ServletException If filter initialization fails
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize and add all security filters to the chain
        filters.add(new AuthenticationFilter());  // Checks login status
        filters.add(new AuthorizationFilter());   // Checks user permissions
        filters.add(new SessionTimeoutFilter());  // Checks session validity
    }

    /**
     * Processes each request through the filter chain.
     * Creates and executes a custom filter chain with all registered filters.
     *
     * @param request  The ServletRequest object
     * @param response The ServletResponse object
     * @param chain    The original FilterChain
     * @throws IOException      If an I/O error occurs
     * @throws ServletException If a servlet-specific error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Create and execute custom filter chain
        FilterChain customChain = new CustomFilterChain(chain, filters);
        customChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup resources if needed
        filters.clear();
    }

    /**
     * Custom implementation of FilterChain that processes filters sequentially.
     * Maintains state of current filter position in the chain.
     */
    private static class CustomFilterChain implements FilterChain {
        private final FilterChain originalChain;  // The original servlet filter chain
        private final List<Filter> filters;      // List of filters to execute
        private int currentPosition = 0;         // Current position in filter chain

        /**
         * Constructs a new CustomFilterChain.
         *
         * @param originalChain The original servlet filter chain
         * @param filters       The list of filters to execute
         */
        public CustomFilterChain(FilterChain originalChain, List<Filter> filters) {
            this.originalChain = originalChain;
            this.filters = filters;
        }

        /**
         * Executes the next filter in the chain or proceeds to original chain.
         *
         * @param request  The ServletRequest object
         * @param response The ServletResponse object
         * @throws IOException      If an I/O error occurs
         * @throws ServletException If a servlet-specific error occurs
         */
        @Override
        public void doFilter(ServletRequest request, ServletResponse response)
                throws IOException, ServletException {
            if (currentPosition < filters.size()) {
                // Get next filter and increment position
                Filter nextFilter = filters.get(currentPosition++);
                // Execute filter with this chain as callback
                nextFilter.doFilter(request, response, this);
            } else {
                // All filters processed - proceed to original chain
                originalChain.doFilter(request, response);
            }
        }
    }
}
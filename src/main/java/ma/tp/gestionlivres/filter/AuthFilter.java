package ma.tp.gestionlivres.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ma.tp.gestionlivres.model.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/livres", "/auteurs", "/add-livre", "/edit-livre", "/delete-livre", 
                          "/add-auteur", "/edit-auteur", "/delete-auteur"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        // If user is not authenticated, redirect to login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Check role-based access
        String path = request.getRequestURI();
        String role = user.getRole();

        // Visitor can only access livres for viewing (GET requests)
        if ("Visiteur".equalsIgnoreCase(role)) {
            // Only allow GET requests to livres, block POST, DELETE, etc.
            String method = request.getMethod();
            if (!method.equals("GET") || path.contains("auteur") || path.contains("add") || 
                path.contains("edit") || path.contains("delete")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
        }

        // Continue with the request
        filterChain.doFilter(request, response);
    }
}


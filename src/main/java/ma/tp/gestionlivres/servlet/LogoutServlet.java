package ma.tp.gestionlivres.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        // Invalidate the session
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Redirect to login page
        resp.sendRedirect("login.jsp");
    }
}

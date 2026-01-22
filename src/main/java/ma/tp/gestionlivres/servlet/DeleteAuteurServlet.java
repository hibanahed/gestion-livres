package ma.tp.gestionlivres.servlet;

import ma.tp.gestionlivres.dao.AuteurDAO;
import ma.tp.gestionlivres.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/delete-auteur")
public class DeleteAuteurServlet extends HttpServlet {

    private final AuteurDAO auteurDAO = new AuteurDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // Get user from session
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        // Check if user is Admin
        if (!isAdmin(user)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        int matricule = Integer.parseInt(req.getParameter("matricule"));
        auteurDAO.supprimerAuteur(matricule);
        resp.sendRedirect("auteurs");
    }

    private boolean isAdmin(User user) {
        return user != null && "Admin".equalsIgnoreCase(user.getRole());
    }
}

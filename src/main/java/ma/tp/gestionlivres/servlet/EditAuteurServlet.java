package ma.tp.gestionlivres.servlet;

import ma.tp.gestionlivres.dao.AuteurDAO;
import ma.tp.gestionlivres.model.Auteur;
import ma.tp.gestionlivres.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/edit-auteur")
public class EditAuteurServlet extends HttpServlet {

    private final AuteurDAO auteurDAO = new AuteurDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Get user from session
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        // Check if user is Admin
        if (!isAdmin(user)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        int matricule = Integer.parseInt(req.getParameter("matricule"));
        Auteur auteur = auteurDAO.findByMatricule(matricule);
        req.setAttribute("auteur", auteur);
        req.getRequestDispatcher("edit-auteur.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        // Get user from session
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        // Check if user is Admin
        if (!isAdmin(user)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        Auteur a = new Auteur();
        a.setMatricule(Integer.parseInt(req.getParameter("matricule")));
        a.setNom(req.getParameter("nom"));
        a.setPrenom(req.getParameter("prenom"));
        a.setGenre(req.getParameter("genre"));

        auteurDAO.modifierAuteur(a);
        resp.sendRedirect("auteurs");
    }

    private boolean isAdmin(User user) {
        return user != null && "Admin".equalsIgnoreCase(user.getRole());
    }
}
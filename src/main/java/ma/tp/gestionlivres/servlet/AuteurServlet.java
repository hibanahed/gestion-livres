package ma.tp.gestionlivres.servlet;

import ma.tp.gestionlivres.dao.AuteurDAO;
import ma.tp.gestionlivres.model.Auteur;
import ma.tp.gestionlivres.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/auteurs")
public class AuteurServlet extends HttpServlet {

    private final AuteurDAO auteurDAO = new AuteurDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Get user from session
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        req.setAttribute("user", user);

        String action = req.getParameter("action");

        if ("add".equals(action)) {
            // Check if user is Admin
            if (!isAdmin(user)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
            req.getRequestDispatcher("add-auteur.jsp").forward(req, resp);
            return;
        }

        if ("edit".equals(action)) {
            // Check if user is Admin
            if (!isAdmin(user)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
            int matricule = Integer.parseInt(req.getParameter("matricule"));
            Auteur auteur = auteurDAO.findByMatricule(matricule);
            req.setAttribute("auteur", auteur);
            req.getRequestDispatcher("edit-auteur.jsp").forward(req, resp);
            return;
        }

        // default → list
        req.setAttribute("auteurs", auteurDAO.findAll());
        req.getRequestDispatcher("auteurs.jsp").forward(req, resp);
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

        String action = req.getParameter("action");
        if (action != null && action.equals("add")) {
            try {
                String matriculeStr = req.getParameter("matricule");
                
                if (matriculeStr == null || matriculeStr.trim().isEmpty()) {
                    req.setAttribute("error", "Matricule is required");
                    req.getRequestDispatcher("add-auteur.jsp").forward(req, resp);
                    return;
                }
                
                int matricule = Integer.parseInt(matriculeStr);
                String nom = req.getParameter("nom");
                String prenom = req.getParameter("prenom");
                String genre = req.getParameter("genre");
                
                if (nom == null || nom.trim().isEmpty()) {
                    req.setAttribute("error", "Name is required");
                    req.getRequestDispatcher("add-auteur.jsp").forward(req, resp);
                    return;
                }
                
                Auteur a = new Auteur();
                a.setMatricule(matricule);
                a.setNom(nom);
                a.setPrenom(prenom);
                a.setGenre(genre);

                auteurDAO.ajouterAuteur(a);
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Matricule must be a numeric value");
                req.getRequestDispatcher("add-auteur.jsp").forward(req, resp);
                return;
            }
        }
        resp.sendRedirect("auteurs");
    }

    private boolean isAdmin(User user) {
        return user != null && "Admin".equalsIgnoreCase(user.getRole());
    }
}

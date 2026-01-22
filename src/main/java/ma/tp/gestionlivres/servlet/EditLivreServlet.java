package ma.tp.gestionlivres.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.tp.gestionlivres.dao.LivreDAO;
import ma.tp.gestionlivres.model.Livre;
import ma.tp.gestionlivres.model.User;
import ma.tp.gestionlivres.model.Auteur;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

@WebServlet("/edit-livre")
public class EditLivreServlet extends HttpServlet {

    private final LivreDAO livreDAO = new LivreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // Check if user is Admin
        if (!isAdmin(user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        int isbn = Integer.parseInt(request.getParameter("isbn"));
        Livre livre = livreDAO.findByIsbn(isbn);

        List<Auteur> auteurs = livreDAO.findAllAuteurs(); // we'll create this method

        request.setAttribute("livre", livre);
        request.setAttribute("auteurs", auteurs);
        request.getRequestDispatcher("/edit-livre.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // Check if user is Admin
        if (!isAdmin(user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        // Get form data
        int isbn = Integer.parseInt(request.getParameter("isbn"));
        String titre = request.getParameter("titre");
        String description = request.getParameter("description");
        String editeur = request.getParameter("editeur");
        java.sql.Date dateEdition = java.sql.Date.valueOf(request.getParameter("dateEdition"));

        // Get selected author matricule from dropdown
        int matricule = Integer.parseInt(request.getParameter("matricule"));
        Auteur auteur = new Auteur();
        auteur.setMatricule(matricule);

        // Update book object
        Livre livre = new Livre();
        livre.setIsbn(isbn);
        livre.setTitre(titre);
        livre.setDescription(description);
        livre.setEditeur(editeur);
        livre.setDateEdition(dateEdition);
        livre.setAuteur(auteur); // ← assign selected author here

        // Call DAO to update
        livreDAO.modifierLivre(livre);

        // Redirect to list
        response.sendRedirect("livres");
    }

    private boolean isAdmin(User user) {
        return user != null && "Admin".equalsIgnoreCase(user.getRole());
    }

}

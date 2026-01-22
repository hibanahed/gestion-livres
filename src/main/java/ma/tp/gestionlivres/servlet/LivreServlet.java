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

import java.io.IOException;
import java.util.List;

@WebServlet("/livres")
public class LivreServlet extends HttpServlet {

    private final LivreDAO livreDAO = new LivreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Check if user is Admin
            if (!isAdmin(user)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
            // Fetch all authors
            List<Auteur> auteurs = livreDAO.findAllAuteurs();
            System.out.println("DEBUG: Number of auteurs fetched: " + auteurs.size());
            request.setAttribute("auteurs", auteurs); // MUST be exactly "auteurs"

            request.getRequestDispatcher("/add-livre.jsp").forward(request, response);
            return;
        }

        // Check for search parameters
        String searchType = request.getParameter("searchType");
        String searchValue = request.getParameter("searchValue");
        List<Livre> livres = null;

        if ("auteur".equals(searchType) && searchValue != null && !searchValue.isEmpty()) {
            try {
                int matricule = Integer.parseInt(searchValue);
                livres = livreDAO.searchByAuteur(matricule);
            } catch (NumberFormatException e) {
                livres = livreDAO.findAll();
            }
        } else if ("titre".equals(searchType) && searchValue != null && !searchValue.isEmpty()) {
            livres = livreDAO.searchByTitre(searchValue);
        } else if ("date".equals(searchType) && searchValue != null && !searchValue.isEmpty()) {
            String dateDebut = request.getParameter("dateDebut");
            String dateFin = request.getParameter("dateFin");
            if (dateDebut != null && dateFin != null && !dateDebut.isEmpty() && !dateFin.isEmpty()) {
                livres = livreDAO.searchByDateEdition(dateDebut, dateFin);
            } else {
                livres = livreDAO.findAll();
            }
        } else {
            // default: list all books
            livres = livreDAO.findAll();
        }

        request.setAttribute("livres", livres);
        request.getRequestDispatcher("/livres.jsp").forward(request, response);
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

        String action = request.getParameter("action");
        if (action != null && action.equals("add")) {
            try {
                String isbnStr = request.getParameter("isbn");
                String matriculeStr = request.getParameter("matricule");
                String dateStr = request.getParameter("dateEdition");
                
                if (isbnStr == null || isbnStr.trim().isEmpty()) {
                    request.setAttribute("error", "ISBN is required");
                    request.getRequestDispatcher("add-livre.jsp").forward(request, response);
                    return;
                }
                if (matriculeStr == null || matriculeStr.trim().isEmpty()) {
                    request.setAttribute("error", "Author is required");
                    request.getRequestDispatcher("add-livre.jsp").forward(request, response);
                    return;
                }
                
                int isbn = Integer.parseInt(isbnStr);
                String titre = request.getParameter("titre");
                String description = request.getParameter("description");
                String editeur = request.getParameter("editeur");
                int matricule = Integer.parseInt(matriculeStr);

                java.sql.Date dateEdition = null;
                if (dateStr != null && !dateStr.trim().isEmpty()) {
                    dateEdition = java.sql.Date.valueOf(dateStr);
                }

                Auteur auteur = new Auteur();
                auteur.setMatricule(matricule);

                Livre livre = new Livre();
                livre.setIsbn(isbn);
                livre.setTitre(titre);
                livre.setDescription(description);
                livre.setDateEdition(dateEdition);
                livre.setEditeur(editeur);
                livre.setAuteur(auteur);

                livreDAO.ajouterLivre(livre);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ISBN and Author must be numeric values");
                request.getRequestDispatcher("add-livre.jsp").forward(request, response);
                return;
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Invalid date format. Use YYYY-MM-DD");
                request.getRequestDispatcher("add-livre.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("livres");
    }

    private boolean isAdmin(User user) {
        return user != null && "Admin".equalsIgnoreCase(user.getRole());
    }
}


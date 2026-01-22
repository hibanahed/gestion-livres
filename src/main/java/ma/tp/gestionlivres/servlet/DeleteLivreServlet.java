package ma.tp.gestionlivres.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.tp.gestionlivres.dao.LivreDAO;
import ma.tp.gestionlivres.model.User;
import java.io.IOException;

@WebServlet("/delete-livre")
public class DeleteLivreServlet extends HttpServlet {

    private final LivreDAO livreDAO = new LivreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Get user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // Check if user is Admin
        if (!isAdmin(user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        int isbn = Integer.parseInt(request.getParameter("isbn"));
        livreDAO.supprimerLivre(isbn);

        response.sendRedirect("livres");
    }

    private boolean isAdmin(User user) {
        return user != null && "Admin".equalsIgnoreCase(user.getRole());
    }
}

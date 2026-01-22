package ma.tp.gestionlivres.servlet;

import ma.tp.gestionlivres.dao.UserDAO;
import ma.tp.gestionlivres.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userDAO.login(email, password);

        if (user != null) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("livres");
        } else {
            req.setAttribute("error", "Invalid credentials");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}

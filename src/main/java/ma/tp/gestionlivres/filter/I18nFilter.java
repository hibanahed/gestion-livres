package ma.tp.gestionlivres.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebFilter(urlPatterns = {"/*"})
public class I18nFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);

        // Check if language is specified in request
        String lang = request.getParameter("lang");
        if (lang != null && (lang.equals("en") || lang.equals("fr"))) {
            session.setAttribute("lang", lang);
        }

        // Get language from session or use default (French)
        String language = (String) session.getAttribute("lang");
        if (language == null) {
            language = "fr";
            session.setAttribute("lang", language);
        }

        // Create Locale based on language
        Locale locale = new Locale(language);
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        // Set messages in request attribute
        request.setAttribute("messages", messages);

        filterChain.doFilter(request, response);
    }
}

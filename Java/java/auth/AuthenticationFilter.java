package auth;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

/**
 * Project: Airport
 * File: AuthenticationFilter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public final class AuthenticationFilter implements Filter {

    @SuppressWarnings("unused")
    private FilterConfig filterConfig = null;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        AuthenticationBean auth = (AuthenticationBean) session.getAttribute("authenticationBean");

        if (auth != null && auth.isAuthorized()) {
            URL url = new URL(((HttpServletRequest) request).getRequestURL().toString());
            if (Authorization.check(auth, url.getPath().toString())) {
                chain.doFilter(request, response);
            } else {


                this.Redirect(request, response);
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Access denied</title></head><body>");
                out.println("<h1>Access denied</h1>");
                out.println("Access denied. <a href=\"../\">Try again</a>.");
                out.println("</body></html>");
            }

        } else {
            this.Redirect(request, response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Bad login</title></head><body>");
            out.println("<h1>Access denied</h1>");
            out.println("Access denied. <a href=\"../\">Try again</a>.");
            out.println("</body></html>");
        }
    }

    private void Redirect(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = ((HttpServletRequest) request);
        HttpServletResponse resp = ((HttpServletResponse) response);
        boolean isAjax = (req.getHeader("X-Requested-With") != null);
        if (isAjax) {
            resp.sendError(403);
        } else {
            String path = req.getContextPath() + "/?bad-login";
            resp.sendRedirect(path);
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}

package atm;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String user = req.getParameter("user");
        String pass = req.getParameter("pass");

        if (user == null || pass == null) {
            req.setAttribute("error", "Usuario y contraseña requeridos");
            RequestDispatcher rd = req.getRequestDispatcher("register.jsp");
            rd.forward(req, resp);
            return;
        }

        user = user.trim();
        pass = pass.trim();

        if (user.isEmpty() || pass.isEmpty()) {
            req.setAttribute("error", "Usuario y contraseña requeridos");
            RequestDispatcher rd = req.getRequestDispatcher("register.jsp");
            rd.forward(req, resp);
            return;
        }

        // username uniqueness (case-insensitive)
        if (UserStore.find(user) != null) {
            req.setAttribute("error", "El nombre de usuario ya existe");
            RequestDispatcher rd = req.getRequestDispatcher("register.jsp");
            rd.forward(req, resp);
            return;
        }

        double initial = ThreadLocalRandom.current().nextDouble(10.0, 1000.0);
        User u = new User(user, pass, Math.round(initial * 100.0) / 100.0);
        UserStore.add(u);

        HttpSession session = req.getSession(true);
        session.setAttribute("user", u);

        resp.sendRedirect(req.getContextPath() + "/atm");
    }
}
package atm;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String user = req.getParameter("user");
        String pass = req.getParameter("pass");

        if (user == null || pass == null) {
            req.setAttribute("error", "Credenciales inválidas");
            RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req, resp);
            return;
        }

        user = user.trim();
        pass = pass.trim();

        User u = UserStore.find(user);
        if (u == null || !u.getPassword().equals(pass)) {
            req.setAttribute("error", "Usuario o contraseña incorrectos");
            RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("user", u);
        // redirect to menu
        resp.sendRedirect("atm");
    }
}
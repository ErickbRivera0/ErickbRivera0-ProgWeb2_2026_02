package atm;

import java.io.IOException;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

public class ClientsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            req.setAttribute("error", "Necesitas iniciar sesión");
            RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req, resp);
            return;
        }

        Map<String, User> all = UserStore.all();
        req.setAttribute("clients", all.values());
        RequestDispatcher rd = req.getRequestDispatcher("clients.jsp");
        rd.forward(req, resp);
    }
}

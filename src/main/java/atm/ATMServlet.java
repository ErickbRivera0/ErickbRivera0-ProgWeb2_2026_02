package atm;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class ATMServlet extends HttpServlet {

 

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            req.setAttribute("error", "Necesitas iniciar sesión");
            RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req, resp);
            return;
        }

        User user = (User) session.getAttribute("user");

        String accion = req.getParameter("accion");
        double monto = 0;

        if (req.getParameter("monto") != null && !req.getParameter("monto").isEmpty()) {
            try {
                monto = Double.parseDouble(req.getParameter("monto"));
            } catch (NumberFormatException e) {
                req.setAttribute("saldo", user.getSaldo());
                req.setAttribute("message", "Monto inválido");
                RequestDispatcher rd = req.getRequestDispatcher("menu.jsp");
                rd.forward(req, resp);
                return;
            }
        }

        if (monto <= 0) {
            req.setAttribute("saldo", user.getSaldo());
            req.setAttribute("message", "El monto debe ser mayor que cero");
            RequestDispatcher rd = req.getRequestDispatcher("menu.jsp");
            rd.forward(req, resp);
            return;
        }

        if ("depositar".equals(accion)) {
            user.setSaldo(user.getSaldo() + monto);
            req.setAttribute("message", "Depósito realizado");
        }

        if ("retirar".equals(accion)) {
            if (monto <= user.getSaldo()) {
                user.setSaldo(user.getSaldo() - monto);
                req.setAttribute("message", "Retiro realizado");
            } else {
                req.setAttribute("message", "Fondos insuficientes");
            }
        }

        double saldo = user.getSaldo();
        req.setAttribute("saldo", saldo);
        if (saldo <= 0) {
            req.setAttribute("warning", "Saldo en cero o negativo");
        } else if (saldo < 50) {
            req.setAttribute("warning", "Saldo bajo: considera realizar un depósito");
        }

        RequestDispatcher rd = req.getRequestDispatcher("menu.jsp");
        rd.forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            req.setAttribute("error", "Necesitas iniciar sesión");
            RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req, resp);
            return;
        }

        User user = (User) session.getAttribute("user");
        double saldo = user.getSaldo();
        req.setAttribute("saldo", saldo);
        if (saldo <= 0) {
            req.setAttribute("warning", "Saldo en cero o negativo");
        } else if (saldo < 50) {
            req.setAttribute("warning", "Saldo bajo: considera realizar un depósito");
        }
        RequestDispatcher rd = req.getRequestDispatcher("menu.jsp");
        rd.forward(req, resp);
    }
}
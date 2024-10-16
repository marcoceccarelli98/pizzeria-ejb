package com.pizzeria;

import java.io.IOException;

import com.pizzeria.Pizza.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ejb.EJB;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private DAO dao;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (dao.checkUser(username, password)) {
            HttpSession session = request.getSession();
            Utente utente = dao.getUtenteByUsername(username);
            session.setAttribute("loggedUser", utente);
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            // Aggiungi un messaggio di errore come attributo della richiesta
            request.setAttribute("errorMessage", "Username o password non validi");
            // Reindirizza l'utente alla pagina di login
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Gestisce le richieste GET reindirizzando alla pagina di login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
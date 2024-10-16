package com.pizzeria;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.pizzeria.Pizza.Impasto;
import com.pizzeria.Pizza.Ingrediente;
import com.pizzeria.Pizza.Utente;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @EJB
    private DAO dao;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utente loggedUser = (Utente) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Pizza> pizze = dao.getAllPizza();
        List<Impasto> impasti = dao.getAllImpasti();
        List<Ingrediente> ingredienti = dao.getAllIngredienti();

        request.setAttribute("pizze", pizze);
        request.setAttribute("impasti", impasti);
        request.setAttribute("ingredienti", ingredienti);

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utente loggedUser = (Utente) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // NOME
        String nome = request.getParameter("nome");

        // IMPASTO
        int idImpasto = Integer.parseInt(request.getParameter("impasto"));
        Impasto impasto = dao.getImpastoById(idImpasto);

        // INGREDIENTI
        String[] idIngredienti = request.getParameterValues("ingredienti");
        List<Ingrediente> ingredienti = new ArrayList<>();
        for (String idIngrediente : idIngredienti) {
            ingredienti.add(dao.getIngredienteById(Integer.parseInt(idIngrediente)));
        }

        Pizza nuovaPizza = new Pizza(nome, impasto, loggedUser, ingredienti);
        dao.addPizza(nuovaPizza);

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
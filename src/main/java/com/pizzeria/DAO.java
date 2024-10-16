package com.pizzeria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;

import java.util.List;

import com.pizzeria.Pizza.Impasto;
import com.pizzeria.Pizza.Ingrediente;
import com.pizzeria.Pizza.Utente;

@Stateless
public class DAO {
    @PersistenceContext
    private EntityManager em;

    // --- PIZZA ---

    public Pizza getPizzaById(int pizzaId) {
        return em.find(Pizza.class, pizzaId);
    }

    public List<Pizza> getAllPizza() {
        em.flush();
        return em.createNamedQuery("Pizza.getAll", Pizza.class).getResultList();
    }

    public void addPizza(Pizza pizza) {
        em.persist(pizza);
        em.flush();
    }

    public void deletePizza(int pizzaId) {
        em.remove(getPizzaById(pizzaId));
        em.flush();
    }

    public void updatePizza(Pizza pizza) {
        em.merge(pizza);
        em.flush();
    }

    // --- UTENTE ---

    public boolean checkUser(String username, String password) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM Pizza$Utente u WHERE u.username = :username AND u.password = :password",
                Long.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getSingleResult() > 0;
    }

    public Utente getUtenteById(int utenteId) {
        return em.find(Utente.class, utenteId);
    }

    public Utente getUtenteByUsername(String username) {
        TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Pizza$Utente u WHERE u.username = :username", Utente.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public List<Pizza> getPizzaForUser(int userId) {
        TypedQuery<Pizza> query = em.createQuery(
                "SELECT p FROM Pizza p WHERE p.utente.id = :userId", Pizza.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // --- IMPASTO ---

    public Impasto getImpastoById(int impastoId) {
        return em.find(Impasto.class, impastoId);
    }

    public List<Impasto> getAllImpasti() {
        return em.createQuery("SELECT i FROM Pizza$Impasto i", Pizza.Impasto.class).getResultList();
    }

    // --- INGREDIENTE ---

    public Ingrediente getIngredienteById(int ingredienteId) {
        return em.find(Ingrediente.class, ingredienteId);
    }

    public List<Ingrediente> getAllIngredienti() {
        return em.createQuery("SELECT i FROM Pizza$Ingrediente i", Pizza.Ingrediente.class).getResultList();
    }

}
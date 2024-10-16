package com.pizzeria;

import java.io.Serializable;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Stateless
@Entity
@NamedQueries({
        @NamedQuery(name = "Pizza.getAll", query = "SELECT DISTINCT p FROM Pizza p LEFT JOIN FETCH p.ingredienti ORDER BY p.id") })
@Table(name = "pizza")
public class Pizza implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_impasto")
    private Impasto impasto;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToMany
    @JoinTable(name = "pizza_ingrediente", joinColumns = @JoinColumn(name = "id_pizza"), inverseJoinColumns = @JoinColumn(name = "id_ingrediente"))
    private List<Ingrediente> ingredienti;

    // Costruttori
    public Pizza() {
    }

    public Pizza(String nome, Impasto impasto, Utente utente, List<Ingrediente> ingredienti) {
        this.nome = nome;
        this.impasto = impasto;
        this.utente = utente;
        this.ingredienti = ingredienti;
    }

    // Getter e Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Impasto getImpasto() {
        return impasto;
    }

    public void setImpasto(Impasto impasto) {
        this.impasto = impasto;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", impasto=" + impasto +
                ", utente=" + utente +
                ", ingredienti=" + ingredienti +
                '}';
    }

    // ----- Classi interne -----

    @Entity
    @Table(name = "impasto")
    public static class Impasto implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String nome;

        public Integer getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }

    @Entity
    @Table(name = "utenti")
    public static class Utente implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String username;
        private String password;

        public Integer getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Utente [id=" + id + ", username=" + username + ", password=" + password + "]";
        }
    }

    @Entity
    @Table(name = "ingrediente")
    public static class Ingrediente implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String nome;

        public Integer getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        @Override
        public String toString() {
            return "Ingrediente [id=" + id + ", nome=" + nome + "]";
        }
    }
}

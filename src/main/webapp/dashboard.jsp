<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard Pizzeria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .hidden {
            display: none;
        }
        .form-background {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <h1>Dashboard Pizzeria</h1>

        <button id="toggleForm" class="btn btn-primary mb-3">Mostra/Nascondi Creazione Pizza</button>

        <div id="createPizzaForm" class="hidden">
            <h2>Crea una nuova pizza</h2>
            <form action="${pageContext.request.contextPath}/dashboard" method="post">
                <div class="form-background">
                    <div class="row">
                        <div class="col-md-6">
                            <h3>Impasto</h3>
                            <c:forEach var="impasto" items="${impasti}">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="impasto" value="${impasto.id}" id="impasto${impasto.id}" required>
                                    <label class="form-check-label" for="impasto${impasto.id}">
                                        ${impasto.nome}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="col-md-6">
                            <h3>Ingredienti</h3>
                            <c:forEach var="ingrediente" items="${ingredienti}">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="ingredienti" value="${ingrediente.id}" id="ingrediente${ingrediente.id}">
                                    <label class="form-check-label" for="ingrediente${ingrediente.id}">
                                        ${ingrediente.nome}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="row mt-3">
                        <div class="col-12">
                            <div class="mb-3">
                                <label for="nome" class="form-label">Nome Pizza:</label>
                                <input type="text" class="form-control" id="nome" name="nome" required>
                            </div>
                        </div>
                    </div>
                </div>

                <button type="submit" class="btn btn-success">Crea Pizza</button>
            </form>
        </div>

        <h2 class="mt-4">Elenco Pizze</h2>
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Impasto</th>
                        <th>Ingredienti</th>
                        <th>Utente</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pizza" items="${pizze}">
                        <tr>
                            <td>${pizza.id}</td>
                            <td>${pizza.nome}</td>
                            <td>${pizza.impasto.nome}</td>
                            <td>
                                <c:forEach var="ingrediente" items="${pizza.ingredienti}" varStatus="status">
                                    ${ingrediente.nome}<c:if test="${!status.last}">, </c:if>
                                </c:forEach>
                            </td>
                            <td>${pizza.utente.username}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('toggleForm').addEventListener('click', function() {
            var form = document.getElementById('createPizzaForm');
            form.classList.toggle('hidden');
        });
    </script>
</body>
</html>
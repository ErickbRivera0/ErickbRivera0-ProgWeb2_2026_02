<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ATM Login</title>
<link rel="stylesheet" href="css/styles.css">
</head>
<body>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Redirect legacy requests for index.jsp to the JSF index.xhtml
    response.sendRedirect("index.xhtml");
%>

<div class="page">
  <div class="card login-card">
    <h1>Cajero Automático</h1>
    <p class="lead">Por favor inicia sesión para continuar</p>

    <% String error = (String) request.getAttribute("error"); %>
    <% if(error != null) { %>
      <div class="alert" style="background:#ffe6e6;color:#7a1b1b;padding:10px;border-radius:6px;margin-bottom:12px;"> <%= error %> </div>
    <% } %>

    <form action="login" method="post" class="login-form">
      <input class="input" type="text" name="user" placeholder="Usuario" required />
      <input class="input" type="password" name="pass" placeholder="Contraseña" required />
      <div style="display:flex;gap:8px;justify-content:center;margin-top:8px;">
        <button class="btn primary" type="submit">Iniciar sesión</button>
        <a class="btn link" href="register.jsp">Registro (demo)</a>
      </div>
    </form>
  </div>
  <footer class="footer">© 2026 ATM Demo</footer>
</div>

</body>
</html>
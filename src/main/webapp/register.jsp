<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro de Usuario</title>
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="page">
  <div class="card login-card">
    <h1>Registro</h1>
    <p class="lead">Crea una cuenta para usar el cajero (demo)</p>

    <% String error = (String) request.getAttribute("error"); %>
    <% if(error != null) { %>
      <div class="alert" style="background:#ffe6e6;color:#7a1b1b;padding:10px;border-radius:6px;margin-bottom:12px;"> <%= error %> </div>
    <% } %>

    <form action="register" method="post">
      <input class="input" type="text" name="user" placeholder="Usuario" required />
      <input class="input" type="password" name="pass" placeholder="Contraseña" required />
      <div style="display:flex;gap:8px;justify-content:center;margin-top:8px;">
        <button class="btn primary" type="submit">Crear cuenta</button>
        <a class="btn link" href="index.jsp">Volver</a>
      </div>
    </form>
  </div>
  <footer class="footer">© 2026 ATM Demo</footer>
</div>
</body>
</html>
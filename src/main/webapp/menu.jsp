<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ATM</title>
<link rel="stylesheet" href="css/styles.css">
</head>
<body>

<div class="page">
  <div class="card">
    <h1>Menu del Cajero</h1>
    <% atm.User user = (atm.User) session.getAttribute("user"); %>
    <% if(user != null){ %>
      <p style="color:var(--muted);margin-top:6px">Conectado como <strong><%= user.getUsername() %></strong></p>
    <% } %>

    <% String msg = (String) request.getAttribute("message"); %>
    <% if(msg != null){ %>
      <div class="alert" style="background:#e6f4ea;color:#134e2b;padding:10px;border-radius:6px;margin-bottom:12px;"> <%= msg %> </div>
    <% } %>

    <% String warn = (String) request.getAttribute("warning"); %>
    <% if(warn != null){ %>
      <div class="alert" style="background:#fff7ed;color:#92400e;padding:10px;border-radius:6px;margin-bottom:12px;"> <%= warn %> </div>
    <% } %>

    <p class="saldo">Saldo actual: <span class="amount">$<%= String.format("%.2f", request.getAttribute("saldo") == null ? 0.0 : (Double)request.getAttribute("saldo")) %></span></p>

    <div class="grid">
      <div class="panel">
        <h3>Depositar</h3>
        <form action="atm" method="post">
          <input type="hidden" name="accion" value="depositar"/>
          <input class="input" type="number" step="0.01" name="monto" placeholder="Monto a depositar" required />
          <button class="btn success" type="submit">Depositar</button>
        </form>
      </div>

      <div class="panel">
        <h3>Retirar</h3>
        <form action="atm" method="post">
          <input type="hidden" name="accion" value="retirar"/>
          <input class="input" type="number" step="0.01" name="monto" placeholder="Monto a retirar" required />
          <button class="btn danger" type="submit">Retirar</button>
        </form>
      </div>
    </div>

    <div class="actions">
      <a class="btn link" href="clients">Clientes</a>
      <a class="btn link" href="logout">Cerrar sesión</a>
    </div>
  </div>
  <footer class="footer">© 2026 ATM Demo</footer>
</div>

</body>
</html>
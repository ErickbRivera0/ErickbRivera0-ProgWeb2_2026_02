<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Clientes</title>
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="page">
  <div class="card">
    <h1>Lista de Clientes</h1>
    <table style="width:100%;border-collapse:collapse;margin-top:12px">
      <thead>
        <tr style="text-align:left;color:var(--muted)">
          <th>Usuario</th>
          <th>Saldo</th>
        </tr>
      </thead>
      <tbody>
        <% java.util.Collection<atm.User> clients = (java.util.Collection<atm.User>) request.getAttribute("clients");
           if(clients != null){
               for(atm.User u : clients){ %>
        <tr>
          <td><%= u.getUsername() %></td>
          <td>$<%= String.format("%.2f", u.getSaldo()) %></td>
        </tr>
        <%     }
           }
        %>
      </tbody>
    </table>

    <div class="actions" style="margin-top:14px">
      <a class="btn link" href="atm">Volver</a>
    </div>
  </div>
  <footer class="footer">© 2026 ATM Demo</footer>
</div>
</body>
</html>
package atm;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "atmBean")
@ApplicationScoped
public class ATMBean {
    private List<Client> clients = new ArrayList<>();

    @PostConstruct
    public void init() {
      
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("clients.csv")) {
            if (is == null) return;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String acc = parts[0].trim();
                        double bal = Double.parseDouble(parts[1].trim());
                        String pin = parts[2].trim();
                        clients.add(new Client(acc, bal, pin));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Client> getClients() { return clients; }

    public Client findByAccount(String account) {
        if (account == null) return null;
        for (Client c : clients) {
            if (account.equals(c.getAccountNumber())) return c;
        }
        return null;
    }

    public boolean addClient(Client c) {
        if (c == null || c.getAccountNumber() == null) return false;
  
        if (findByAccount(c.getAccountNumber()) != null) return false;
        clients.add(c);
        return true;
    }
}
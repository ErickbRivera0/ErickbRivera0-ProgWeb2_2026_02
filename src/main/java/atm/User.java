package atm;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password; 
    private double saldo;

    public User(String username, String password, double saldo) {
        this.username = username;
        this.password = password;
        this.saldo = saldo;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double s) { this.saldo = s; }
}

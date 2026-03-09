package atm;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

@ManagedBean(name = "userSession")
@SessionScoped
public class UserSession implements Serializable {
    private Client current;
    private String message;
    private String error;

    // login/register form fields
    private String loginAccount;
    private String loginPin;

    private String regAccount;
    private String regPin;

    private double depositAmount;
    private String depositPin;

    private double withdrawAmount;
    private String withdrawPin;

    @ManagedProperty(value = "#{atmBean}")
    private ATMBean atmBean;

    public void setAtmBean(ATMBean atmBean) { this.atmBean = atmBean; }

    public Client getCurrent() { return current; }
    public void setCurrent(Client c) { this.current = c; }

    public boolean isLogged() { return current != null; }

    public void logout() { this.current = null; }

    public String logoutAction() { this.current = null; this.message = null; this.error = null; return "index.xhtml?faces-redirect=true"; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    // form field getters/setters
    public String getLoginAccount() { return loginAccount; }
    public void setLoginAccount(String loginAccount) { this.loginAccount = loginAccount; }
    public String getLoginPin() { return loginPin; }
    public void setLoginPin(String loginPin) { this.loginPin = loginPin; }

    public String getRegAccount() { return regAccount; }
    public void setRegAccount(String regAccount) { this.regAccount = regAccount; }
    public String getRegPin() { return regPin; }
    public void setRegPin(String regPin) { this.regPin = regPin; }

    public double getDepositAmount() { return depositAmount; }
    public void setDepositAmount(double depositAmount) { this.depositAmount = depositAmount; }
    public String getDepositPin() { return depositPin; }
    public void setDepositPin(String depositPin) { this.depositPin = depositPin; }

    public double getWithdrawAmount() { return withdrawAmount; }
    public void setWithdrawAmount(double withdrawAmount) { this.withdrawAmount = withdrawAmount; }
    public String getWithdrawPin() { return withdrawPin; }
    public void setWithdrawPin(String withdrawPin) { this.withdrawPin = withdrawPin; }

    // actions
    public String loginAction() {
        this.error = null; this.message = null;
        Client c = atmBean.findByAccount(loginAccount);
        if (c == null) { this.error = "Cuenta no encontrada"; return null; }
        if (!c.getPin().equals(loginPin)) { this.error = "PIN inválido"; return null; }
        this.current = c;
        this.message = "Bienvenido, cuenta " + c.getAccountNumber();
        return "menu.xhtml?faces-redirect=true";
    }

    public String registerAction() {
        this.error = null; this.message = null;
        if (regAccount == null || regPin == null || regAccount.trim().isEmpty() || regPin.trim().isEmpty()) {
            this.error = "Cuenta y PIN son requeridos"; return null;
        }
        if (atmBean.findByAccount(regAccount) != null) { this.error = "Cuenta ya existe"; return null; }
        double initial = ThreadLocalRandom.current().nextDouble(10.0, 1000.0);
        Client c = new Client(regAccount.trim(), Math.round(initial*100.0)/100.0, regPin.trim());
        atmBean.addClient(c);
        this.current = c;
        this.message = "Cuenta creada. Saldo inicial: $" + String.format("%.2f", c.getBalance());
        c.addTransaction("CREACION," + c.getBalance());
        return "menu.xhtml?faces-redirect=true";
    }

    public String depositAction() {
        this.error = null; this.message = null;
        if (!isLogged()) { this.error = "No autenticado"; return null; }
        if (depositAmount <= 0) { this.error = "Monto inválido"; return null; }
        if (!current.getPin().equals(depositPin)) { this.error = "PIN inválido"; return null; }
        current.setBalance(current.getBalance() + depositAmount);
        String t = "DEPOSITO," + String.format("%.2f", depositAmount) + "," + String.format("%.2f", current.getBalance());
        current.addTransaction(t);
        this.message = "Depósito realizado. Nuevo saldo: $" + String.format("%.2f", current.getBalance());
        return "menu.xhtml?faces-redirect=true";
    }

    public String withdrawAction() {
        this.error = null; this.message = null;
        if (!isLogged()) { this.error = "No autenticado"; return null; }
        if (withdrawAmount <= 0) { this.error = "Monto inválido"; return null; }
        if (!current.getPin().equals(withdrawPin)) { this.error = "PIN inválido"; return null; }
        if (withdrawAmount > current.getBalance()) { this.error = "Saldo insuficiente"; return null; }
        current.setBalance(current.getBalance() - withdrawAmount);
        String t = "RETIRO," + String.format("%.2f", withdrawAmount) + "," + String.format("%.2f", current.getBalance());
        current.addTransaction(t);
        this.message = "Retiro realizado. Nuevo saldo: $" + String.format("%.2f", current.getBalance());
        return "menu.xhtml?faces-redirect=true";
    }

    public String exportHistory() {
        if (!isLogged()) { this.error = "No autenticado"; return null; }
        String filename = "data/transactions_" + current.getAccountNumber() + ".csv";
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();
        File f = new File(filename);
        try (FileWriter fw = new FileWriter(f)) {
            fw.write("tipo,monto,saldo\n");
            for (String r : current.getTransactions()) {
                fw.write(r + "\n");
            }
        } catch (IOException e) {
            this.error = "Error al exportar historial: " + e.getMessage();
            return null;
        }
        this.message = "Historial exportado a " + f.getAbsolutePath();
        return null;
    }
}
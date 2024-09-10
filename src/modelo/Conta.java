package modelo;

import java.util.ArrayList;

public class Conta {
    private final int id;
    private String data;
    private double saldo;
    private ArrayList<Correntista> correntistas;

    public Conta(int id, String data) {
        this.id = id;
        this.data = data;
        this.correntistas = new ArrayList<>();
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void creditar(double valor) {
        this.saldo += valor;
    }

    public void debitar(double valor) throws Exception {
        if (valor <= this.saldo) {
            this.saldo -= valor;
            return;
        }
        throw new Exception("Saldo insuficiente");
    }

    public void transferir(double valor, Conta destino) throws Exception {
        this.debitar(valor);
        destino.creditar(valor);
    }


}

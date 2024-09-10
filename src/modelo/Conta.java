package modelo;

import java.util.ArrayList;

public class Conta {
    private final int id;
    private String data;
    private double saldo;
    private ArrayList<Correntista> correntistas;

    public Conta(int id, String data, double saldo) {
        this.id = id;
        this.data = data;
        this.saldo = saldo;
        this.correntistas = new ArrayList<>();
    }

    public double getSaldo() {
        return saldo;
    }

    public void creditar(double valor) {
        this.saldo += valor;
    }

    public void debitar(double valor) {
        this.saldo -= valor;
    }

    public void transferir(double valor, Conta destino) {
        this.debitar(valor);
        destino.creditar(valor);
    }


}

package modelo;

import java.util.ArrayList;

public class Correntista {
    private final String cpf;
    private String nome;
    private String senha;
    private final ArrayList<Conta> contas;

    public Correntista(String cpf, String nome, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    public double getSaldoTotal() {
        double saldoTotal = 0;
        for (Conta conta : contas) {
            saldoTotal += conta.getSaldo();
        }
        return saldoTotal;
    }
}

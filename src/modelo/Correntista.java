package modelo;

import java.util.ArrayList;

public class Correntista {
    private final String cpf;
    private String nome;
    private int senha;
    private final ArrayList<Conta> contas;

    public Correntista(String cpf, String nome, int senha) {
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

    public void adicionarConta(Conta conta) {
        this.contas.add(conta);
    }

    public String getCpf() {
        return this.cpf;
    }

    public int getQuantidadeContas(){
        return this.contas.size();
    }
}

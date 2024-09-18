package modelo;

import java.util.ArrayList;

public class Correntista {
    private final String cpf;
    private String nome;
    private final String senha;
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

    public void adicionarConta(Conta conta) throws Exception {
        boolean contemConta = this.contas.contains(conta);
        if (!contemConta) {
            this.contas.add(conta);
            return;
        }
        throw new Exception("Conta já cadastrada");
    }

    public void removerConta(Conta conta) throws Exception {
        boolean contemConta = this.contas.contains(conta);
        if (contemConta) {
            this.contas.remove(conta);
            return;
        }
        throw new Exception("Conta não cadastrada");
    }

    public String getCpf() {
        return this.cpf;
    }

    public void validarSenha(String senha) throws Exception {
        if (!this.senha.equals(senha)) {
            throw new Exception("Senha inválida");
        }
    }

    public boolean correntistaTitularConta(){
        for (Conta conta : this.contas){
            if(conta.getCorrentistas().getFirst().equals(this)){
                return true;
            }
        }
        return false;
    }
}

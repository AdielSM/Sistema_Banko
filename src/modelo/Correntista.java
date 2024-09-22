package modelo;

import java.util.ArrayList;

public class Correntista {
    private final String cpf;
    private String nome;
    private final String senha;
    private final ArrayList<Conta> contas;

    @Override
	public String toString() {
		return "Correntista [cpf=" + cpf + ", nome=" + nome + "]";
	}

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
        throw new Exception("Conta já associada ao correntista");
    }

    public void removerConta(Conta conta) throws Exception {
        if (this.contas.contains(conta)) {
            this.contas.remove(conta);
            conta.removerCorrentista(this);
        } else {
        	throw new Exception("Conta não associada ao correntista.");
        }
    }

    public String getCpf() {
        return this.cpf;
    }
    
    public String getNome() {
    	return this.nome;
    }
    
    public String getSenha() {
    	return this.senha;
    }
    
    public String getContasIds() {
    	String ids = "";
    	for (Conta c : this.contas) {
    		ids = ids + c.getId() + ",";
    	}
    	if (!ids.isEmpty()) {		
    		ids = ids.substring(0, ids.length() - 1);
    	} else {
    		ids = "-";
    	}
    	return ids;
    }

    public void validarSenha(String senha) throws Exception {
        if (!this.senha.equals(senha)) {
            throw new Exception("Senha inválida");
        }
    }
 
    public boolean correntistaTitularConta(){
        for (Conta conta : this.contas){
        	ArrayList<Correntista> correntistas = conta.getCorrentistas();
        	System.out.println(correntistas);
        	System.out.println(correntistas.isEmpty());
        	if (correntistas.isEmpty()) {
        		continue;
        	} else {
        		if(conta.getCorrentistas().get(0).equals(this)){
        			return true;
        		}
        	}
        }
        return false;
    }
}

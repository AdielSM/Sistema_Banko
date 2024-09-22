package modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class Conta {
    @Override
	public String toString() {
		return "Conta [id=" + id + ", data=" + data + ", saldo=" + saldo + ", correntistas=" + correntistas + "]";
	}

	private final int id;
    private String data;
    private double saldo;
    private final ArrayList<Correntista> correntistas;

    public Conta(int id, String data) {
        this.id = id;
        this.data = data;
        this.correntistas = new ArrayList<>();
    }
    
    public Conta(int id, String data, double saldo) {
        this.id = id;
        this.data = data;
        this.saldo = saldo;
        this.correntistas = new ArrayList<>();
    }

    public String getData() {
        return this.data;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void adicionarCorrentista(Correntista correntista) throws Exception {

        for (Correntista correntista1 : correntistas) {
            if (correntista1.getCpf().equals(correntista.getCpf())) {
                throw new Exception("Correntista já cadastrado nesta conta");
            }
        }

        this.correntistas.add(correntista);
    }
    
    public void removerCorrentista(Correntista correntista) throws Exception {
        if (this.correntistas.contains(correntista)) {
        	this.correntistas.remove(correntista);
        }
    }

    public void creditar(double valor) throws Exception {
        if (valor < 0.01) {
            throw new Exception("Valor inválido");
        }
        this.saldo += valor;
    }

    public void debitar(double valor) throws Exception {
        if (valor < 0.01) {
            throw new Exception("Valor inválido");
        }

        if (this.saldo < valor) {
            throw new Exception("Saldo insuficiente");
        }

        this.saldo -= valor;
    }

    public void transferir(double valor, Conta destino) throws Exception {
        this.debitar(valor);
        destino.creditar(valor);
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Correntista> getCorrentistas() {
        return new ArrayList<>(this.correntistas);
    }
    
    public String exibirCpfCorrentistas() {
    	String correntistas = "";
    	for (Correntista c : this.correntistas) {
    		correntistas += c.getCpf() + ", ";
    	}
    	if (correntistas.endsWith(", ")) {
    		correntistas = correntistas.substring(0, correntistas.length() - 2);
    	}
    	return correntistas;
    }

    public void desvincularCorrentistas() throws Exception {
        for (Correntista correntista : this.correntistas) {
            correntista.removerConta(this);
        }
    }
}

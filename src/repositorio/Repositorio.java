package repositorio;

import modelo.Conta;
import modelo.Correntista;

import java.util.ArrayList;
import java.util.Comparator;

public class Repositorio {
    private ArrayList<Conta> contas;
    private ArrayList<Correntista> correntistas;

    public Repositorio() {
        this.contas = new ArrayList<>();
        this.correntistas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) {
        this.contas.add(conta);
    }

    public void adicionarCorrentista(Correntista correntista) {
        this.correntistas.add(correntista);
    }

    public Correntista buscarCorrentista(String cpf) {
        for (Correntista correntista : correntistas) {
            if (correntista.getCpf().equals(cpf)) {
                return correntista;
            }
        }
        return null;
    }

    public ArrayList<Correntista> listarCorrentistas() {
        ArrayList<Correntista> correntistasOrdenados = new ArrayList<>(this.correntistas);
        correntistasOrdenados.sort(Comparator.comparing(Correntista::getCpf));
        return correntistasOrdenados;
    }

    public ArrayList<Conta> listarContas() {
        return this.contas;
    }

    public void criarCorrentista(String cpf, String nome, String senha) throws Exception {
        Correntista correntista = new Correntista(cpf, nome, senha);
        this.validarCpf(cpf);
        this.adicionarCorrentista(correntista);
    }
    private void validarCpf(String cpf) throws Exception {
        if (this.buscarCorrentista(cpf) != null) {
            throw new Exception("CPF já cadastrado");
        }

    }
}

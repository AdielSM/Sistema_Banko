package repositorio;

import modelo.Conta;
import modelo.Correntista;

import java.util.ArrayList;
import java.util.Comparator;

public class Repositorio {
    private final ArrayList<Conta> contas;
    private final ArrayList<Correntista> correntistas;

    public Repositorio() {
        this.contas = new ArrayList<>();
        this.correntistas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) {
        this.contas.add(conta);
    }

    public void adicionarCorrentista(Correntista correntista) throws Exception {
        this.validarCpfExistente(correntista.getCpf());
        this.correntistas.add(correntista);
    }

    public void validarCpfExistente(String cpf) throws Exception {
        Correntista correntista = this.buscarCorrentista(cpf);
        if (correntista == null){
            return;
        }
        throw new Exception("CPF já cadastrado");
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

    public int getNewContaId() {
        if (this.contas.isEmpty()) {
            return 1;
        }
        return this.contas.size() + 1;
    }

    public Conta buscarConta(int id) {
        for (Conta conta : contas) {
            if (conta.getId() == id) {
                return conta;
            }
        }
        return null;
    }

    public void removerConta(Conta conta) {
        this.contas.remove(conta);
    }

    public boolean correntistaTitular(String cpf, Conta conta) throws Exception {
        Correntista correntista = this.buscarCorrentista(cpf);

        if (correntista == null){
            throw new Exception("Cpf do correntista não encontrado");
        }

        ArrayList <Correntista> correntistas = conta.getCorrentistas();
        return correntistas.getFirst().equals(correntista);
    }

//    public void removerConta()


//    public void criarConta(String cpf) throws Exception {
//        Correntista correntista = this.buscarCorrentista(cpf);
//        LocalDate data = LocalDate.now();
//        Conta conta = new Conta(contaId, data.toString());
//        Repositorio.contaId++;
//        correntista.adicionarConta(conta);
//        this.adicionarConta(conta);
//    }


}

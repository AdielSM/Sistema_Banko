package regras_negocio;

import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Correntista;
import repositorio.Repositorio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Fachada {

    private static final Repositorio repositorio = new Repositorio();

    public Fachada() {
    }

    public static ArrayList<Correntista> listarCorrentistas() {
        return repositorio.listarCorrentistas();
    }

    public static ArrayList<Conta> listarContas() {
        return repositorio.listarContas();
    }

    public static void criarCorrentista(String cpf, String nome, String senha) throws Exception {
        repositorio.validarCpfExistente(cpf);
        Fachada.verificarSenha(senha);
        Correntista correntista = new Correntista(cpf, nome, senha);
        repositorio.adicionarCorrentista(correntista);
    }

    private static void verificarSenha(String senha) throws Exception {
        String regex = "^\\d+$";
        if (!senha.matches(regex)) {
            throw new Exception("A senha deve conter apenas números");
        }
        if (senha.length() != 4) {
            throw new Exception("A senha deve conter 4 dígitos");
        }
    }

    public static void criarConta(String cpf) throws Exception {
        String dataFormatada = Fachada.dataAtual();

        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if(correntista == null){
            throw new Exception("Cpf do correntista não encontrado");
        }

        if(correntista.getQuantidadeContas() != 0){
            throw new Exception("O Correntista deste Cpf já é titular de uma conta");
        }

        Conta conta = new Conta(repositorio.getNewContaId(), dataFormatada);
        repositorio.adicionarConta(conta);
        correntista.adicionarConta(conta);
        conta.adicionarCorrentista(correntista);
    }

    public static void criarContaEspecial(String cpf, double limite) throws Exception {
        String dataFormatada = Fachada.dataAtual();

        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if(correntista == null){
            throw new Exception("Cpf do correntista não encontrado");
        }

        if(correntista.getQuantidadeContas() != 0){
            throw new Exception("O Correntista deste Cpf já é titular de uma conta");
        }

        ContaEspecial conta = new ContaEspecial(repositorio.getNewContaId(), dataFormatada, limite);
        repositorio.adicionarConta(conta);
        correntista.adicionarConta(conta);
        conta.adicionarCorrentista(correntista);
    }

    private static String dataAtual() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static void inserirCorrentistaConta(String cpf, int id) throws Exception {
        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if(correntista == null){
            throw new Exception("Cpf do correntista não encontrado");
        }

        Conta conta = repositorio.buscarConta(id);
        if(conta == null){
            throw new Exception("Id da conta não encontrado");
        }

        correntista.adicionarConta(conta);
        conta.adicionarCorrentista(correntista);
    }

    public static void removerCorrentistaConta(String cpf, int id) throws Exception {
        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if(correntista == null){
            throw new Exception("Cpf do correntista não encontrado");
        }

        Conta conta = repositorio.buscarConta(id);
        if(conta == null){
            throw new Exception("Id da conta não encontrado");
        }

        boolean correntistaTitular = repositorio.correntistaTitular(correntista, conta);
        if(!correntistaTitular){
            correntista.removerConta(conta);
            return;
        }
        throw new Exception("O correntista é titular desta conta, portanto não pode ser removido");
    }

    public static void apagarConta(int id) throws Exception {
        Conta conta = repositorio.buscarConta(id);

        if(conta == null){
            throw new Exception("Id da conta não encontrado" );
        }

        conta.desvincularCorrentistas();
        repositorio.removerConta(conta);
    }

    public static void creditarValor(int id, String cpf, String senha, double valor) throws Exception {
        Conta conta = repositorio.buscarConta(id);
        if(conta == null){
            throw new Exception("Id da conta não encontrado");
        }

        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if(correntista == null){
            throw new Exception("Cpf do correntista não encontrado");
        }

        correntista.validarSenha(senha);
        conta.creditar(valor);
    }

    public static void debitarValor(int id, String cpf, String senha, double valor) throws Exception {
        Conta conta = repositorio.buscarConta(id);
        if(conta == null){
            throw new Exception("Id da conta não encontrado");
        }

        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if(correntista == null){
            throw new Exception("Cpf do correntista não encontrado");
        }

        correntista.validarSenha(senha);
        conta.debitar(valor);
    }

    public static void transferirValor(int id1, String cpf, String senha, double valor, int id2) throws Exception {
        Conta conta1 = repositorio.buscarConta(id1);
        Conta conta2 = repositorio.buscarConta(id2);

        if(conta1 == null){
            throw new Exception("Id da conta 1 não encontrado");
        }

        if(conta2 == null){
            throw new Exception("Id da conta 2 não encontrado");
        }

        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if(correntista == null){
            throw new Exception("Cpf do correntista não encontrado");
        }

        correntista.validarSenha(senha);
        conta1.transferir(valor, conta2);
    }

}
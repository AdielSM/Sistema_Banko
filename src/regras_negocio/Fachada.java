package regras_negocio;

import modelo.Conta;
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

    public static void criarCorrentista(String cpf, String nome, int senha) throws Exception {
        repositorio.buscarCpfExistente(cpf);
        Fachada.verificarSenha(senha);
        Correntista correntista = new Correntista(cpf, nome, senha);
        repositorio.adicionarCorrentista(correntista);
    }

    private static void verificarSenha(int senha) throws Exception {
        String senhaStr = Integer.toString(senha);
        if (senhaStr.length() != 4){
            throw new Exception("A senha deve ter 4 dígitos");
        }
    }

    public static void criarConta(String cpf) throws Exception {
        String dataFormatada = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

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





}

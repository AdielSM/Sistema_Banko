package regras_negocio;

import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Correntista;
import repositorio.Repositorio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class Fachada {

    private static final Repositorio repositorio = new Repositorio();

    public Fachada() {
    }

        /**
     * Lista todos os correntistas ordenados pelo cpf.
     *
     * @return uma lista de todos os correntistas
     */
    public static ArrayList<Correntista> listarCorrentistas() {
    	return repositorio.listarCorrentistas();
    }
    
    public static ArrayList<Correntista> listarCorrentistas(String filtro) {
    	ArrayList<Correntista> lista = new ArrayList<>();
    	for(Correntista c : repositorio.listarCorrentistas())
			if(c.getNome().contains(filtro) ||  c.getCpf().contains(filtro))
				lista.add(c);
		return lista;
    }

    /**
     * Lista todas as contas.
     *
     * @return uma lista de todas as contas
     */
    public static ArrayList<Conta> listarContas() {
        return repositorio.listarContas();
    }
    
    public static ArrayList<Conta> listarContas(String filtro) {
    	ArrayList<Conta> lista = new ArrayList<>();
    	for(Conta c : repositorio.listarContas())
			if(Integer.toString(c.getId()).contains(filtro) || c.exibirCpfCorrentistas().contains(filtro))
				lista.add(c);
        return lista;
    }

        /**
     * Cria um novo correntista.
     *
     * @param cpf   o CPF do correntista
     * @param nome  o nome do correntista
     * @param senha a senha do correntista
     * @throws Exception se o CPF já estiver cadastrado ou se a senha não atender aos critérios de validação
     */
    public static void criarCorrentista(String cpf, String nome, String senha) throws Exception {
        repositorio.validarCpfExistente(cpf);
        Fachada.verificarSenha(senha);
        Fachada.verificarCpf(cpf);
        Correntista correntista = new Correntista(cpf, nome, senha);
        repositorio.adicionarCorrentista(correntista);
        repositorio.salvarObjetos();
    }

        private static void verificarCpf(String cpf) throws Exception {
        	String regex = "^\\d+$";
            if (!cpf.matches(regex)) {
                throw new Exception("O CPF deve conter apenas números");
            }
		}

		/**
     * Verifica se a senha atende aos critérios de validação.
     *
     * @param senha a senha a ser verificada
     * @throws Exception se a senha não contiver apenas números ou se não tiver 4 dígitos
     */
    private static void verificarSenha(String senha) throws Exception {
        String regex = "^\\d+$";
        if (!senha.matches(regex)) {
            throw new Exception("A senha deve conter apenas números");
        }
        if (senha.length() != 4) {
            throw new Exception("A senha deve conter 4 dígitos");
        }
    }

        /**
     * Cria uma nova conta para um correntista.
     *
     * @param cpf o CPF do correntista
     * @throws Exception se o CPF do correntista não for encontrado ou se o correntista já for titular de uma conta
     */
    public static void criarConta(String cpf) throws Exception {
        String dataFormatada = Fachada.dataAtual();

        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if (correntista == null) {
            throw new Exception("Cpf do correntista não encontrado");
        }

        if (correntista.correntistaTitularConta()) {
            throw new Exception("O Correntista deste Cpf já é titular de alguma outra conta");
        }

        Conta conta = new Conta(repositorio.getNewContaId(), dataFormatada);
        repositorio.adicionarConta(conta);
        correntista.adicionarConta(conta);
        conta.adicionarCorrentista(correntista);
        repositorio.salvarObjetos();
    }

        /**
     * Cria uma conta especial para um correntista.
     *
     * @param cpf    o CPF do correntista
     * @param limite o limite da conta especial
     * @throws Exception se o CPF do correntista não for encontrado ou se o correntista já for titular de uma conta
     */
    public static void criarContaEspecial(String cpf, double limite) throws Exception {
        String dataFormatada = Fachada.dataAtual();

        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if(correntista == null){
            throw new Exception("Cpf do correntista não encontrado");
        }

        if (correntista.correntistaTitularConta()) {
            throw new Exception("O Correntista deste Cpf já é titular de alguma outra conta");
        }

        ContaEspecial conta = new ContaEspecial(repositorio.getNewContaId(), dataFormatada, limite);
        repositorio.adicionarConta(conta);
        correntista.adicionarConta(conta);
        conta.adicionarCorrentista(correntista);
        
        repositorio.salvarObjetos();
    }

        /**
     * Retorna a data atual formatada como uma string no formato "dd/MM/yyyy".
     *
     * @return a data atual formatada
     */
    private static String dataAtual() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

        /**
     * Insere um correntista em uma conta existente.
     *
     * @param cpf o CPF do correntista
     * @param id  o ID da conta
     * @throws Exception se o CPF do correntista não for encontrado ou se o ID da conta não for encontrado
     */
    public static void inserirCorrentistaConta(int id, String cpf) throws Exception {
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
        
        repositorio.salvarObjetos();
    }

        /**
     * Remove um correntista de uma conta.
     *
     * @param cpf o CPF do correntista
     * @param id  o ID da conta
     * @throws Exception se o CPF do correntista não for encontrado, se o ID da conta não for encontrado,
     *                   ou se o correntista for titular da conta
     */
    public static void removerCorrentistaConta(int id, String cpf) throws Exception {
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
            repositorio.salvarObjetos();
            return;
        }
        throw new Exception("O correntista é titular desta conta, portanto não pode ser removido");
    }

    /**
     * Apaga uma conta do repositório, removendo também os correntistas associados.
     *
     * @param id o ID da conta a ser apagada
     * @throws Exception se a conta não for encontrada, se a conta ainda possuir saldo ou se ocorrer um erro ao desvincular correntistas
     */
    public static void apagarConta(int id) throws Exception {
        Conta conta = repositorio.buscarConta(id);

        if (conta == null) {
            throw new Exception("Id da conta não encontrado");
        }

        if (conta.getSaldo() != 0) {
            throw new Exception("A conta não pode ser apagada pois ainda possui saldo");
        }
        Iterator<Correntista> iterator = conta.getCorrentistas().iterator();
        while (iterator.hasNext()) {
            Correntista correntista = iterator.next();
            correntista.removerConta(conta);
            iterator.remove();
        }
//        try {
//        	conta.desvincularCorrentistas();
//        } catch (Exception e) {
        	
//        }
        repositorio.removerConta(conta);
    	repositorio.salvarObjetos();
    }

    /**
     * Credita um valor em uma conta.
     *
     * @param id     o ID da conta
     * @param cpf    o CPF do correntista
     * @param senha  a senha do correntista
     * @param valor  o valor a ser creditado
     * @throws Exception se a conta não for encontrada, se o CPF do correntista não for encontrado,
     *                   se a senha for inválida ou se o valor for inválido
     */
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
        
        repositorio.salvarObjetos();
    }

        /**
     * Debita um valor de uma conta.
     *
     * @param id     o ID da conta
     * @param cpf    o CPF do correntista
     * @param senha  a senha do correntista
     * @param valor  o valor a ser debitado
     * @throws Exception se a conta não for encontrada, se o CPF do correntista não for encontrado,
     *                   se a senha for inválida ou se o saldo for insuficiente
     */
    public static void debitarValor(int id, String cpf, String senha, double valor) throws Exception {
        Conta conta = repositorio.buscarConta(id);
        if (conta == null) {
            throw new Exception("Id da conta não encontrado");
        }

        Correntista correntista = repositorio.buscarCorrentista(cpf);
        if (correntista == null) {
            throw new Exception("Cpf do correntista não encontrado");
        }

        correntista.validarSenha(senha);
        conta.debitar(valor);
        
        repositorio.salvarObjetos();
    }

    /**
     * Transfere um valor de uma conta para outra.
     *
     * @param id1   o ID da conta de origem
     * @param cpf   o CPF do correntista que está realizando a transferência
     * @param senha a senha do correntista
     * @param valor o valor a ser transferido
     * @param id2   o ID da conta de destino
     * @throws Exception se qualquer uma das contas não for encontrada, se o CPF do correntista não for encontrado,
     *                   se a senha for inválida ou se o saldo for insuficiente
     */
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
        
        repositorio.salvarObjetos();
    }

}
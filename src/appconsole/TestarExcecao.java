package appconsole;
/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 *
 */

import regras_negocio.Fachada;

public class TestarExcecao {

	public static void main (String[] args) {

		System.out.println("\n-------TESTE DE EXCEÇÕES LANÇADAS PELOS METODOS DA FACHADA--------");
		try {
			Fachada.criarCorrentista("0001","abc", "1111"); //cpf,nome,senha
			System.out.println("*************1--->Nao lançou exceção para: criar correntista com cpf existente "); 
		}catch (Exception e) {System.out.println("1ok--->"+e.getMessage());}

		try {
			Fachada.criarCorrentista("9999","abc", "111X"); //cpf,nome,senha
			System.out.println("*************2--->Nao lançou exceção para: criar correntista com senha numerica "); 
		}catch (Exception e) {System.out.println("2ok--->"+e.getMessage());}

		try {
			Fachada.criarConta("0001"); //cpf,nome,senha
			System.out.println("*************3--->Nao lançou exceção para: criar conta com titular de outra conta "); 
		}catch (Exception e) {System.out.println("3ok--->"+e.getMessage());}

		try {
			Fachada.criarConta("0000"); //cpf,nome,senha
			System.out.println("*************4--->Nao lançou exceção para: criar conta com titular inexistente "); 
		}catch (Exception e) {System.out.println("4ok--->"+e.getMessage());}

		try {
			Fachada.removerCorrentistaConta(3,"0003"); //cpf,nome,senha
			System.out.println("*************5--->Nao lançou exceção para: remover correntista titular "); 
		}catch (Exception e) {System.out.println("5ok--->"+e.getMessage());}

		try {
			Fachada.apagarConta(3); //cpf,nome,senha
			System.out.println("*************6--->Nao lançou exceção para: apagar conta com saldo nao zerado "); 
		}catch (Exception e) {System.out.println("6ok--->"+e.getMessage());}

	}
}



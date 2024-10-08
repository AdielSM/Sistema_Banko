package repositorio;

import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Correntista;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Repositorio {
    private final ArrayList<Conta> contas = new ArrayList<>();
    private final ArrayList<Correntista> correntistas = new ArrayList<>();

    public Repositorio() {
        this.carregarObjetos();
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
        return new ArrayList<>(this.contas);
    }

    public int getNewContaId() {
        if (this.contas.isEmpty()) {
            return 1;
        }
        return this.contas.get(contas.size() - 1).getId() + 1;
    }

    public Conta buscarConta(int id) {
        for (Conta conta : contas) {
            if (conta.getId() == id) {
                return conta;
            }
        }
        return null;
    }

    public void removerConta(Conta conta) throws Exception {
        if (this.contas.remove(conta)) {
            return;
        }
        throw new Exception("Conta não encontrada");
    }

    public boolean correntistaTitular(Correntista correntista, Conta conta) throws Exception {
        ArrayList <Correntista> correntistas = conta.getCorrentistas();
        return correntistas.get(0).equals(correntista);
    }

    
	public void adicionar(Conta c)	{
		contas.add(c);
	}
	public void remover(Conta c)	{
		contas.remove(c);
	}
	
	public void adicionar(Correntista c)	{
		correntistas.add(c);
	}
	public void remover(Correntista c)	{
		correntistas.remove(c);
	}
    
    public void carregarObjetos() {
        try {
            File contas = new File(Paths.get(".").toAbsolutePath().normalize().toString(), "contas.csv");
            File correntistas = new File(Paths.get(".").toAbsolutePath().normalize().toString(), "correntistas.csv");
            if (!contas.exists() || (!correntistas.exists())) {
                FileWriter arquivo1 = new FileWriter(contas); arquivo1.close();
                FileWriter arquivo2 = new FileWriter(correntistas); arquivo2.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
        }

        String linha;
        String[] partes;
        Conta cc;
        Correntista corrCC;
        
        try {
            String id, data, saldo, limite, tipo;
            File f1 = new File( new File("contas.csv").getCanonicalPath() )  ;
			Scanner arquivo1 = new Scanner(f1);	 
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();		
				partes = linha.split(";");	
				tipo = partes[0];
				id = partes[1];
				data = partes[2];
				saldo = partes[3];
				limite = partes[4];
				if (tipo.equals("CONTA")) {
					cc = new Conta(Integer.parseInt(id), data, Double.parseDouble(saldo));

				} else {					
					cc = new ContaEspecial(Integer.parseInt(id), data, Double.parseDouble(saldo), Double.parseDouble(limite));
				}

				this.adicionar(cc);
				
			}
			arquivo1.close();
        }  catch(Exception ex)		{
        	System.out.println(ex);
			throw new RuntimeException("leitura arquivo de contas:"+ex.getMessage());
		}   
        
        try {
            String cpf, nome, senha, ids;
            File f2 = new File( new File("correntistas.csv").getCanonicalPath() )  ;
			Scanner arquivo2 = new Scanner(f2);	 
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();		
				partes = linha.split(";");	
				cpf = partes[0];
				nome = partes[1];
				senha = partes[2];
				ids = partes[3].replace("-", "");
				corrCC = new Correntista(cpf, nome, senha);
				this.adicionar(corrCC);
				
			    if(!ids.isEmpty()) {
			        ids = ids.replace("[", "").replace("]", "");
					for(String id : ids.split(",")){
						Conta conta = this.buscarConta(Integer.parseInt(id));
						conta.adicionarCorrentista(corrCC);
						corrCC.adicionarConta(conta);
					}
				}
			} 
			arquivo2.close();
        }  catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de correntistas:"+ex.getMessage());
		}
        
    }
    
    public void	salvarObjetos()  {
		try	{
	        File f = new File(Paths.get(".", "contas.csv").toAbsolutePath().normalize().toString());
			FileWriter arquivo1 = new FileWriter(f); 
			for(Conta c : contas) 	{
				  if (c instanceof ContaEspecial contaEspecial) {
				        arquivo1.write("CONTAESPECIAL" + ";" +  c.getId() + ";" + c.getData() + ";" + c.getSaldo() + ";" + contaEspecial.getLimite() + "\n");
				    } else {
				        arquivo1.write("CONTA" + ";" + c.getId() + ";" + c.getData() + ";" + c.getSaldo() + ";" + "-"+"\n");
				    }
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na criação do arquivo contas.csv "+e.getMessage());
		}
		
		try	{
	        File f = new File(Paths.get(".", "correntistas.csv").toAbsolutePath().normalize().toString());
			FileWriter arquivo2 = new FileWriter(f); 
			for(Correntista c : correntistas) 	{				  
				arquivo2.write(c.getCpf() + ";" + c.getNome() + ";" + c.getSenha() + ";" + c.getContasIds() + "\n");
			} 
			arquivo2.close();
		}
		catch(Exception e){
			System.out.println(e);
			throw new RuntimeException("problema na criação do arquivo correntistas.csv "+e.getMessage());
		}

		
	}

}

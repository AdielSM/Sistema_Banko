package modelo;

public class ContaEspecial extends Conta{
    private final double limite;
    private static final int LIMITE_MINIMO = 50;

    public ContaEspecial(int id, String data,double limite) throws Exception {
        super(id, data);
        this.validarLimite(limite);
        this.limite = limite;
    }

    private void validarLimite(double valor) throws Exception {
        if (valor < LIMITE_MINIMO){
            throw new Exception("O limite de uma conta especial deve ser maior ou igual a 50.");
        }
    }

    @Override
    public void debitar(double valor) throws Exception {
        if (valor <= this.getSaldo() + this.limite) {
            this.setSaldo(this.getSaldo() - valor);
            return;
        }
        throw new Exception("Saldo insuficiente e limite estourado");

    }

    @Override
    public void transferir(double valor, Conta destino) throws Exception {
        this.debitar(valor);
        destino.creditar(valor);
    }
}

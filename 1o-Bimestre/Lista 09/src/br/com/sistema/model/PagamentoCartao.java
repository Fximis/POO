package br.com.sistema.model;

public class PagamentoCartao implements Pagamento {
    @Override
    public void processar(double valor) {
        System.out.println("Validando limite no cartão para cobrança de R$ " + valor);
    }
}

package br.com.sistema.model;

public class ProcessadorPagamento {
    public void finalizarCompra(double valor, Pagamento formaPagamento) {
        formaPagamento.processar(valor);
    }
}

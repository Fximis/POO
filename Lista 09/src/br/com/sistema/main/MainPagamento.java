package br.com.sistema.main;

import br.com.sistema.model.Pagamento;
import br.com.sistema.model.PagamentoPix;
import br.com.sistema.model.PagamentoCartao;
import br.com.sistema.model.ProcessadorPagamento;

public class MainPagamento {
    public static void main(String[] args) {
        ProcessadorPagamento processador = new ProcessadorPagamento();
        
        Pagamento pPix = new PagamentoPix();
        Pagamento pCartao = new PagamentoCartao();
        
        processador.finalizarCompra(150.0, pPix);
        processador.finalizarCompra(300.0, pCartao);
    }
}

package br.com.ecommerce.model;

import br.com.ecommerce.exception.TipoFreteInvalidoException;

public class CalculadoraFrete {
    public double processarFrete(double valorPedido, EstrategiaFrete estrategia) {
        if (estrategia == null) {
            throw new TipoFreteInvalidoException("Estratégia de frete inválida (null).");
        }
        return estrategia.calcular(valorPedido);
    }
}

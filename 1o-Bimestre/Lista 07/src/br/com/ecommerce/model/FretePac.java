package br.com.ecommerce.model;

public class FretePac implements EstrategiaFrete {
    @Override
    public double calcular(double valorPedido) {
        return valorPedido * 0.05;
    }
}

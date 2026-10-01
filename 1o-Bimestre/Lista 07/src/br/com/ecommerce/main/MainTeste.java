package br.com.ecommerce.main;

import br.com.ecommerce.exception.TipoFreteInvalidoException;
import br.com.ecommerce.model.CalculadoraFrete;
import br.com.ecommerce.model.FreteSedex;
import br.com.ecommerce.model.FretePac;
import br.com.ecommerce.model.FreteMotoboy;

public class MainTeste {
    public static void main(String[] args) {
        CalculadoraFrete calc = new CalculadoraFrete();
        
        try {
            System.out.println("Sedex: " + calc.processarFrete(100.0, new FreteSedex()));
            System.out.println("PAC: " + calc.processarFrete(100.0, new FretePac()));
            System.out.println("Motoboy: " + calc.processarFrete(100.0, new FreteMotoboy()));
            
            // Força o erro
            calc.processarFrete(100.0, null);
        } catch (TipoFreteInvalidoException e) {
            System.out.println("Erro capturado: " + e.getMessage());
        }
    }
}

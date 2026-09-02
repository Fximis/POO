package br.com.cyberfestival.main;

import br.com.cyberfestival.model.Bilheteria;
import br.com.cyberfestival.model.Ingresso;
import br.com.cyberfestival.model.IngressoInvalidoException;

/**
 * Classe principal para testes do sistema CyberFestival.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Instancia a Bilheteria
        Bilheteria bilheteria = new Bilheteria();

        // 2. Criação dos 3 ingressos conforme especificação
        Ingresso ingresso1 = new Ingresso("A-01", "VIP", 500.00);
        Ingresso ingresso2 = new Ingresso("A-02", "PISTA", 200.00);
        Ingresso ingresso3 = new Ingresso("A-01", "PISTA", 200.00); // Clone cambista (mesmo código A-01)

        // 3. Venda dos Ingressos 1 e 2
        try {
            bilheteria.venderIngresso(ingresso1);
            bilheteria.venderIngresso(ingresso2);
        } catch (IngressoInvalidoException e) {
            System.out.println(e.getMessage());
        }

        // 4. Teste da Falha: Tentar vender o Ingresso 3
        try {
            // Tenta vender o ingresso clonado
            bilheteria.venderIngresso(ingresso3);
        } catch (IngressoInvalidoException e) {
            // Imprime o alerta da exceção de forma limpa, sem estourar o programa (Crash)
            System.out.println(e.getMessage());
        }

        // 5. Teste da Stream: Imprimir o valor retornado por calcularReceitaVIP()
        double receitaVip = bilheteria.calcularReceitaVIP();
        System.out.printf(java.util.Locale.US, "%.2f\n", receitaVip);
    }
}

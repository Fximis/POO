package br.com.cyberfestival.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por gerenciar a venda de ingressos do evento.
 */
public class Bilheteria {
    
    // Banco de dados em memória utilizando Map, conforme RN03
    // A chave é o codigoId (String) e o valor é o próprio Ingresso
    private Map<String, Ingresso> ingressosVendidos;

    /**
     * Construtor da Bilheteria, inicializa o Map com um HashMap.
     */
    public Bilheteria() {
        this.ingressosVendidos = new HashMap<>();
    }

    /**
     * Método para registrar a venda de um ingresso.
     * @param ingresso O ingresso a ser vendido
     * @throws IngressoInvalidoException Se o ingresso já tiver sido validado/vendido (código duplicado)
     */
    public void venderIngresso(Ingresso ingresso) {
        // Verifica se o mapa já contém a chave do ingresso
        if (this.ingressosVendidos.containsKey(ingresso.getCodigoId())) {
            // Se contiver, lança a exceção customizada como barreira anti-cambista
            throw new IngressoInvalidoException();
        }
        // Se não contiver, adiciona o ingresso no mapa de ingressos vendidos
        this.ingressosVendidos.put(ingresso.getCodigoId(), ingresso);
    }

    /**
     * Calcula a receita total apenas dos ingressos do tipo "VIP".
     * @return O valor total arrecadado com ingressos VIP.
     */
    public double calcularReceitaVIP() {
        // Utiliza apenas Streams API, conforme exigido na RN04 (proibido usar for e if)
        return this.ingressosVendidos.values().stream()
            // Filtra para deixar passar apenas os ingressos cujo tipo seja exatamente "VIP"
            .filter(ingresso -> "VIP".equals(ingresso.getTipo()))
            // Extrai o valor do ingresso usando mapToDouble
            .mapToDouble(Ingresso::getValor)
            // Retorna a soma total
            .sum();
    }
}

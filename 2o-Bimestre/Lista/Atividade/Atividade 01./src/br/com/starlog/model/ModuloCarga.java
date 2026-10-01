package br.com.starlog.model;

import br.com.starlog.exception.CapacidadeExcedidaException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um módulo de contenção e transporte de cargas espaciais.
 * Controla capacidade máxima e provê processamento analítico funcional via Streams API.
 */
public class ModuloCarga {

    private final String idModulo;
    private final int capacidadeMaxima;
    private final List<Carga> cargas;

    /**
     * Inicializa o módulo com seu identificador e limite de capacidade.
     *
     * @param idModulo Identificador do módulo (não nulo e não em branco).
     * @param capacidadeMaxima Limite máximo de cargas suportado (> 0).
     * @throws IllegalArgumentException Se os parâmetros forem inválidos.
     */
    public ModuloCarga(String idModulo, int capacidadeMaxima) {
        if (idModulo == null || idModulo.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do módulo não pode ser nulo ou vazio.");
        }
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("Capacidade máxima deve ser estritamente positiva (> 0).");
        }

        this.idModulo = idModulo;
        this.capacidadeMaxima = capacidadeMaxima;
        this.cargas = new ArrayList<>();
    }

    /**
     * Adiciona uma carga ao módulo, respeitando a barreira de capacidade máxima.
     *
     * @param carga Instância de Carga a ser acomodada.
     * @throws IllegalArgumentException Se carga for nula.
     * @throws CapacidadeExcedidaException Se a capacidade máxima do módulo for atingida.
     */
    public void carregarCarga(Carga carga) throws CapacidadeExcedidaException {
        if (carga == null) {
            throw new IllegalArgumentException("Carga não pode ser nula.");
        }
        if (this.cargas.size() >= this.capacidadeMaxima) {
            throw new CapacidadeExcedidaException(
                    "Capacidade máxima de " + this.capacidadeMaxima + " cargas atingida no módulo: " + this.idModulo
            );
        }
        this.cargas.add(carga);
    }

    public String getIdModulo() {
        return idModulo;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    /**
     * Retorna a lista de cargas encapsulada e imutável contra mutações externas.
     *
     * @return Lista não-modificável contendo as cargas carregadas.
     */
    public List<Carga> getCargas() {
        return Collections.unmodifiableList(this.cargas);
    }

    // =========================================================================
    // MÉTODOS DE AGREGAÇÃO FUNCIONAL VIA STREAMS API (ZERO 'if' / ZERO 'for')
    // =========================================================================

    /**
     * Calcula o somatório total do valor de seguro de todas as cargas no módulo.
     * Implementação puramente funcional sem condicionais ou laços de repetição.
     *
     * @return Somatório monetário total das apólices de seguro.
     */
    public double calcularSeguroTotal() {
        return this.cargas.stream()
                .mapToDouble(Carga::getValorSeguro)
                .sum();
    }

    /**
     * Realiza a contagem de cargas pertencentes a uma categoria específica.
     * Implementação puramente funcional com tratamento null-safe no predicado.
     *
     * @param categoria Nome da categoria desejada.
     * @return Quantidade de itens de carga compatíveis.
     */
    public long contarPorCategoria(String categoria) {
        return this.cargas.stream()
                .filter(c -> c.getCategoria() != null && c.getCategoria().equalsIgnoreCase(categoria))
                .count();
    }

    /**
     * Calcula o valor total do seguro apenas para as cargas cujo peso excede o piso especificado.
     * Implementação puramente funcional com filtragem estrita (pesoKg > pesoMinimo).
     *
     * @param pesoMinimo Limiar de peso mínimo em kg.
     * @return Somatório dos seguros das cargas que ultrapassam o peso informado.
     */
    public double calcularSeguroPesadas(double pesoMinimo) {
        return this.cargas.stream()
                .filter(c -> c.getPesoKg() > pesoMinimo)
                .mapToDouble(Carga::getValorSeguro)
                .sum();
    }
}

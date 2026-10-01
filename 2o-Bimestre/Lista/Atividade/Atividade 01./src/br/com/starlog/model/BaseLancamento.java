package br.com.starlog.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Gerenciador de módulos de carga da Base de Lançamento StarLog-Zero.
 * Armazena e recupera módulos com complexidade algorítmica O(1) através de tabela hash.
 */
public class BaseLancamento {

    private final Map<String, ModuloCarga> modulos;

    public BaseLancamento() {
        this.modulos = new HashMap<>();
    }

    /**
     * Registra um módulo de carga na base utilizando seu idModulo como chave primária em O(1).
     *
     * @param modulo Módulo a ser cadastrado.
     * @throws IllegalArgumentException Se o módulo for nulo ou tiver ID inválido.
     */
    public void cadastrarModulo(ModuloCarga modulo) {
        if (modulo == null) {
            throw new IllegalArgumentException("Módulo não pode ser nulo.");
        }
        if (modulo.getIdModulo() == null || modulo.getIdModulo().trim().isEmpty()) {
            throw new IllegalArgumentException("ID do módulo não pode ser nulo ou vazio.");
        }
        this.modulos.put(modulo.getIdModulo(), modulo);
    }

    /**
     * Recupera um módulo de carga pelo seu identificador único em O(1).
     *
     * @param idModulo Identificador do módulo.
     * @return Módulo de carga correspondente ou null caso não encontrado.
     */
    public ModuloCarga buscarModulo(String idModulo) {
        if (idModulo == null) {
            return null;
        }
        return this.modulos.get(idModulo);
    }

    /**
     * Retorna o total de módulos registrados atualmente na base de lançamento.
     *
     * @return Quantidade de módulos registrados.
     */
    public int totalModulos() {
        return this.modulos.size();
    }
}

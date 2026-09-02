package br.com.cyberfestival.model;

/**
 * Classe que representa um Ingresso do CyberFestival.
 * É um POJO (Plain Old Java Object) que armazena os dados básicos do ingresso.
 */
public class Ingresso {
    // Atributos privados conforme especificado na RN01
    private String codigoId;
    private String tipo; // ex: "VIP" ou "PISTA"
    private double valor;

    /**
     * Construtor completo para inicializar todos os atributos do Ingresso.
     * @param codigoId Código único do ingresso
     * @param tipo Tipo do ingresso (VIP, PISTA, etc.)
     * @param valor Valor do ingresso
     */
    public Ingresso(String codigoId, String tipo, double valor) {
        this.codigoId = codigoId;
        this.tipo = tipo;
        this.valor = valor;
    }

    // Métodos Getters para acessar os atributos privados
    
    /**
     * @return O código identificador do ingresso.
     */
    public String getCodigoId() {
        return codigoId;
    }

    /**
     * @return O tipo do ingresso.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return O valor do ingresso.
     */
    public double getValor() {
        return valor;
    }
}

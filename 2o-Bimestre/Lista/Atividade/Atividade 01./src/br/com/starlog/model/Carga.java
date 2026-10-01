package br.com.starlog.model;

import java.util.Objects;

/**
 * Entidade que representa uma unidade de carga no sistema StarLog-Zero.
 * Possui identificador imutável (codigoRastreio) e validações com fail-fast.
 */
public class Carga {

    private final String codigoRastreio;
    private String categoria;
    private double pesoKg;
    private double valorSeguro;

    /**
     * Construtor da Carga com validação fail-fast de invariantes de domínio.
     *
     * @param codigoRastreio Código de rastreio único (não nulo e não em branco).
     * @param categoria Categoria da carga.
     * @param pesoKg Peso da carga em quilogramas (deve ser estritamente > 0).
     * @param valorSeguro Valor monetário de cobertura do seguro da carga (>= 0).
     * @throws IllegalArgumentException Se codigoRastreio for nulo/vazio ou se pesoKg for <= 0.
     */
    public Carga(String codigoRastreio, String categoria, double pesoKg, double valorSeguro) {
        if (codigoRastreio == null || codigoRastreio.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de rastreio não pode ser nulo ou vazio.");
        }
        if (pesoKg <= 0.0) {
            throw new IllegalArgumentException("Peso em kg deve ser estritamente positivo (> 0).");
        }
        if (valorSeguro < 0.0) {
            throw new IllegalArgumentException("Valor do seguro não pode ser negativo.");
        }

        this.codigoRastreio = codigoRastreio;
        this.categoria = categoria;
        this.pesoKg = pesoKg;
        this.valorSeguro = valorSeguro;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        if (pesoKg <= 0.0) {
            throw new IllegalArgumentException("Peso em kg deve ser estritamente positivo (> 0).");
        }
        this.pesoKg = pesoKg;
    }

    public double getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(double valorSeguro) {
        if (valorSeguro < 0.0) {
            throw new IllegalArgumentException("Valor do seguro não pode ser negativo.");
        }
        this.valorSeguro = valorSeguro;
    }

    /**
     * Contrato de igualdade semântica baseado EXCLUSIVAMENTE no codigoRastreio.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carga carga = (Carga) o;
        return Objects.equals(codigoRastreio, carga.codigoRastreio);
    }

    /**
     * Contrato de dispersão hash baseado EXCLUSIVAMENTE no codigoRastreio.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigoRastreio);
    }

    @Override
    public String toString() {
        return "Carga{" +
                "codigoRastreio='" + codigoRastreio + '\'' +
                ", categoria='" + categoria + '\'' +
                ", pesoKg=" + pesoKg +
                ", valorSeguro=" + valorSeguro +
                '}';
    }
}

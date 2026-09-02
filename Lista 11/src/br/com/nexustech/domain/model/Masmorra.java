package br.com.nexustech.domain.model;

import br.com.nexustech.domain.exception.NivelInsuficienteException;

/**
 * Entidade de Domínio representando uma Masmorra no RPG.
 * Requer um nível mínimo de entrada de 50.
 * 
 * Exercício 9: A Masmorra Fechada
 */
public class Masmorra {

    public static final int NIVEL_MINIMO_ACESSO = 50;
    private final String nome;
    private final int nivelMinimo;

    public Masmorra() {
        this("Masmorra do Dragão Ancestral", NIVEL_MINIMO_ACESSO);
    }

    public Masmorra(String nome, int nivelMinimo) {
        this.nome = nome;
        this.nivelMinimo = nivelMinimo;
    }

    public String getNome() {
        return nome;
    }

    public int getNivelMinimo() {
        return nivelMinimo;
    }

    /**
     * Tenta entrar na masmorra com o nível informado.
     * 
     * Exercício 9: Se o nível for menor que 50, lança NivelInsuficienteException.
     * 
     * @param nivelJogador Nível atual do jogador
     * @throws NivelInsuficienteException se nivelJogador < 50
     */
    public void entrar(int nivelJogador) {
        if (nivelJogador < this.nivelMinimo) {
            throw new NivelInsuficienteException();
        }
        System.out.println("Entrada autorizada na " + nome + "! Nível do jogador: " + nivelJogador);
    }

    /**
     * Sobrecarga de conveniência para objeto Jogador.
     */
    public void entrar(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo ao entrar na masmorra.");
        }
        entrar(jogador.getNivel());
    }
}

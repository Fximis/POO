package br.com.nexustech.domain.service;

import br.com.nexustech.domain.exception.BanidoException;
import br.com.nexustech.domain.model.Jogador;

/**
 * Serviço de Domínio para gerenciamento de matchmaking e emparelhamento de jogadores.
 * 
 * Nível Boss - Passo 4: O Sistema
 */
public class Matchmaker {

    /**
     * Tenta encontrar uma sala no modo solicitado, validando o status de banimento do jogador.
     * 
     * @param modo Modo de jogo selecionado (Polimorfismo: Casual, Ranqueado, etc.)
     * @param jogadorBanido Flag indicando se o jogador possui punição ativa
     * @throws BanidoException se jogadorBanido for true
     */
    public void encontrarSala(ModoJogo modo, boolean jogadorBanido) throws BanidoException {
        if (jogadorBanido) {
            throw new BanidoException();
        }
        
        if (modo == null) {
            throw new IllegalArgumentException("O modo de jogo não pode ser nulo.");
        }

        modo.buscarPartida();
    }

    /**
     * Sobrecarga de conveniência para emparelhamento a partir de entidade Jogador.
     */
    public void encontrarSala(ModoJogo modo, Jogador jogador) throws BanidoException {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo.");
        }
        encontrarSala(modo, jogador.isBanido());
    }
}

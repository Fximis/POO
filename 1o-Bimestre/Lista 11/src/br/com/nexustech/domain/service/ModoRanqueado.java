package br.com.nexustech.domain.service;

/**
 * Implementação do Modo Ranqueado (Competitivo) de Jogo.
 * 
 * Nível Boss - Passo 2: As Opções
 */
public class ModoRanqueado implements ModoJogo {

    @Override
    public void buscarPartida() {
        System.out.println("[Modo Ranqueado] Buscando oponentes do mesmo elo para partida competitiva!");
    }
}

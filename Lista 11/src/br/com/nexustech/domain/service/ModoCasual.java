package br.com.nexustech.domain.service;

/**
 * Implementação do Modo Casual de Jogo.
 * 
 * Nível Boss - Passo 2: As Opções
 */
public class ModoCasual implements ModoJogo {

    @Override
    public void buscarPartida() {
        System.out.println("[Modo Casual] Procurando uma partida descontraída e rápida...");
    }
}

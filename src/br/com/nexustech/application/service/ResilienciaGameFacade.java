package br.com.nexustech.application.service;

import br.com.nexustech.domain.exception.BanidoException;
import br.com.nexustech.domain.exception.NivelInsuficienteException;
import br.com.nexustech.domain.model.EstatisticasCombate;
import br.com.nexustech.domain.model.Inventario;
import br.com.nexustech.domain.model.Jogador;
import br.com.nexustech.domain.model.Masmorra;
import br.com.nexustech.domain.service.Matchmaker;
import br.com.nexustech.domain.service.ModoCasual;
import br.com.nexustech.domain.service.ModoJogo;
import br.com.nexustech.domain.service.ModoRanqueado;
import br.com.nexustech.infrastructure.crypto.AesPbkdf2Decryptor.ResultadoDecriptacao;

/**
 * Fachada de Aplicação (Facade Pattern / DDD Application Service)
 * Orquestra todos os casos de uso e cenários de resiliência da NexusTech.
 */
public class ResilienciaGameFacade {

    private final Matchmaker matchmaker;
    private final CofreGabaritoService cofreService;

    public ResilienciaGameFacade() {
        this.matchmaker = new Matchmaker();
        this.cofreService = new CofreGabaritoService();
    }

    // ==========================================
    // NÍVEL 1: UNCHECKED EXCEPTIONS
    // ==========================================

    /**
     * Exercício 1 e 2: Cálculo resiliente de K/D
     */
    public String processarTaxaKd(int kills, int deaths) {
        EstatisticasCombate stats = new EstatisticasCombate(kills, deaths);
        return stats.calcularKdResiliente();
    }

    /**
     * Exercício 3: Inserção resiliente no inventário
     */
    public String equiparItemInventario(Inventario inventario, int slot, String item) {
        return inventario.equiparItemResiliente(slot, item);
    }

    /**
     * Exercício 4: Programação Defensiva contra NullPointerException
     */
    public String verificarStatusJogadorDefensivo(String nomeJogador) {
        return Jogador.verificarStatusConexao(nomeJogador);
    }

    // ==========================================
    // NÍVEL 2: CHECKED EXCEPTIONS & FINALLY
    // ==========================================

    /**
     * Exercício 5, 6 e 7: Conexão resiliente com servidor e liberação de recursos
     */
    public void simularConexaoRedeComFechamento() {
        RedeService.executarRotinaConexaoComFinally();
    }

    // ==========================================
    // NÍVEL 3: EXCEÇÕES CUSTOMIZADAS
    // ==========================================

    /**
     * Exercício 8 e 9: Tentativa de entrada na Masmorra
     */
    public boolean tentarEntrarMasmorra(Masmorra masmorra, int nivelJogador) {
        try {
            masmorra.entrar(nivelJogador);
            return true;
        } catch (NivelInsuficienteException e) {
            System.out.println("Acesso Negado à Masmorra: " + e.getMessage());
            return false;
        }
    }

    // ==========================================
    // NÍVEL BOSS: MATCHMAKER & POLIMORFISMO
    // ==========================================

    /**
     * Nível Boss: Busca de partida via Matchmaker com suporte a polimorfismo e validação de banimento
     */
    public boolean solicitarMatchmaking(ModoJogo modo, boolean jogadorBanido) {
        try {
            matchmaker.encontrarSala(modo, jogadorBanido);
            return true;
        } catch (BanidoException e) {
            System.out.println("Falha no Matchmaking: " + e.getMessage());
            return false;
        }
    }

    // ==========================================
    // DESAFIO EXTRA: CRIPTOGRAFIA E SEGURANÇA
    // ==========================================

    /**
     * Desafio Extra: Desbloqueio do cofre por força bruta
     */
    public ResultadoDecriptacao desbloquearGabarito() {
        return cofreService.desbloquearCofre();
    }
}

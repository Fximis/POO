package br.com.nexustech.domain.model;

/**
 * Entidade de Domínio representando um Jogador na NexusTech.
 * 
 * Exercício 4: O Fantasma (O Mito do NullPointer e Programação Defensiva)
 * Exercício 9: Nível do Jogador para acesso a Masmorras
 * Nível Boss: Status de Banimento para Matchmaking
 */
public class Jogador {

    private String nickname;
    private int nivel;
    private boolean banido;
    private Inventario inventario;
    private EstatisticasCombate estatisticas;

    public Jogador(String nickname, int nivel, boolean banido) {
        this.nickname = nickname;
        this.nivel = nivel;
        this.banido = banido;
        this.inventario = new Inventario();
        this.estatisticas = new EstatisticasCombate(0, 0);
    }

    public Jogador(String nickname, int nivel) {
        this(nickname, nivel, false);
    }

    public Jogador(String nickname) {
        this(nickname, 1, false);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isBanido() {
        return banido;
    }

    public void setBanido(boolean banido) {
        this.banido = banido;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public EstatisticasCombate getEstatisticas() {
        return estatisticas;
    }

    /**
     * Valida e formata o status do jogador usando Programação Defensiva (sem try-catch para NPE).
     * 
     * Exercício 4: Se o jogador for diferente de null, imprime o nome dele.
     * Senão, imprime "Jogador desconectado".
     * 
     * @param nomeJogador Referência que pode ser nula
     * @return Status do jogador
     */
    public static String verificarStatusConexao(String nomeJogador) {
        if (nomeJogador != null) {
            return nomeJogador;
        } else {
            return "Jogador desconectado";
        }
    }

    /**
     * Sobrecarga defensiva para instâncias de Jogador.
     */
    public static String verificarStatusConexao(Jogador jogador) {
        if (jogador != null && jogador.getNickname() != null) {
            return jogador.getNickname();
        } else {
            return "Jogador desconectado";
        }
    }
}

package br.com.nexustech.domain.model;

/**
 * Entidade de Domínio para gerenciamento de estatísticas de combate (Kills, Deaths, K/D Ratio).
 * 
 * Exercício 1 e 2: O Bug do K/D e seu tratamento resiliente.
 */
public class EstatisticasCombate {

    private int kills;
    private int deaths;

    public EstatisticasCombate(int kills, int deaths) {
        this.kills = kills;
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    /**
     * Realiza a divisão crua (sem tratamento) para demonstrar o bug matemático do Ex. 1.
     * @throws ArithmeticException se deaths == 0
     */
    public int calcularKdSemTratamento() {
        return kills / deaths;
    }

    /**
     * Calcula a taxa de K/D de forma segura e resiliente tratando ArithmeticException.
     * Exercício 2: Consertando o K/D
     * 
     * @return Representação textual ou valor do K/D
     */
    public String calcularKdResiliente() {
        try {
            int taxa = kills / deaths;
            return "Taxa K/D: " + taxa;
        } catch (ArithmeticException e) {
            return "Taxa K/D: Jogador Invicto!";
        }
    }
}

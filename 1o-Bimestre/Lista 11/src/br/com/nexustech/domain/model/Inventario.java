package br.com.nexustech.domain.model;

/**
 * Entidade de Domínio representando o inventário do RPG.
 * Possui 3 espaços de capacidade máxima (índices 0, 1 e 2).
 * 
 * Exercício 3: O Inventário Bugado (ArrayIndexOutOfBoundsException)
 */
public class Inventario {

    public static final int CAPACIDADE_MAXIMA = 3;
    private final String[] itens;

    public Inventario() {
        this.itens = new String[CAPACIDADE_MAXIMA];
    }

    public String[] getItens() {
        return itens.clone();
    }

    public int getCapacidade() {
        return CAPACIDADE_MAXIMA;
    }

    /**
     * Tenta forçar a inserção em um slot arbitrário tratando ArrayIndexOutOfBoundsException.
     * 
     * @param posicao Índice do array a ser acessado
     * @param item Nome do item a ser colocado
     * @return Mensagem de status resultante
     */
    public String equiparItemResiliente(int posicao, String item) {
        try {
            itens[posicao] = item;
            return "Item '" + item + "' equipado no espaço " + posicao + " com sucesso!";
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = "Inventário cheio!";
            System.out.println(msg);
            return msg;
        }
    }

    /**
     * Tenta inserir sem tratamento para demonstrar o estouro de limites do array.
     */
    public void equiparItemSemTratamento(int posicao, String item) {
        itens[posicao] = item;
    }
}

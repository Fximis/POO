package br.com.techcorp.main;

import br.com.techcorp.model.Funcionario;
import br.com.techcorp.model.ControleDeAcesso;

public class MainTechCorp {
    public static void main(String[] args) {
        ControleDeAcesso controle = new ControleDeAcesso();
        
        Funcionario f1 = new Funcionario("T-001", "Alice", "Dev");
        Funcionario f2 = new Funcionario("T-001", "Alice Duplicada", "Manager");
        
        System.out.println("--- Teste Catraca ---");
        controle.registrarPassagem(f1);
        controle.registrarPassagem(f2);
        
        System.out.println("--- Teste Sala Segura ---");
        controle.concederAcessoSala(f1);
        controle.concederAcessoSala(f2);
    }
}

package br.com.banco.main;

import br.com.banco.model.Cliente;
import br.com.banco.model.ContaBancaria;
import br.com.banco.model.Agencia;

public class MainTeste {
    public static void main(String[] args) {
        Cliente c1 = new Cliente("12345678900", "João", "joao@email.com");
        Cliente c2 = new Cliente("12345678900", "Maria", "maria@email.com");
        
        if (c1.equals(c2)) {
            System.out.println("Os clientes são iguais.");
        }
        
        ContaBancaria conta = new ContaBancaria("001", c1, 50.0);
        
        boolean saqueSucesso = conta.sacar(50.0);
        System.out.println("Saque aprovado: " + saqueSucesso);
        
        System.out.println("Total contas: " + Agencia.getTotalContasAbertas());
    }
}

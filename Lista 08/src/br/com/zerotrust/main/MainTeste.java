package br.com.zerotrust.main;

import br.com.zerotrust.model.*;

public class MainTeste {
    public static void main(String[] args) {
        Departamento d = new Departamento("TI", "Tecnologia", 5);
        Funcionario func = new Funcionario("123", "Alice", d);
        Veiculo v = new Veiculo("ABC-1234", "Civic", func);

        SistemaSeguranca seg = new SistemaSeguranca(2);

        Credencial c1 = new Credencial("FFF-999", true, func);
        Credencial clone = new Credencial("FFF-999", true, func);

        seg.registrarCatraca(func);
        seg.registrarCatraca(func);

        seg.acessarCofre(c1);
        seg.acessarCofre(clone);

        try {
            seg.estacionarVeiculo(v, 0);
            seg.estacionarVeiculo(v, 5); // Erro provocado
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erro: " + e.toString());
        }
    }
}

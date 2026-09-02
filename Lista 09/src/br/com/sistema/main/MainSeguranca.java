package br.com.sistema.main;

import br.com.sistema.model.Credencial;
import br.com.sistema.model.Sede;

public class MainSeguranca {
    public static void main(String[] args) {
        Sede sede = new Sede();
        Credencial c1 = new Credencial("AAA-123");
        
        sede.registrarPassagemCatraca(c1);
        sede.registrarPassagemCatraca(c1);
        
        sede.autorizarEntradaCofre(c1);
        sede.autorizarEntradaCofre(c1); // Falha
        
        try {
            sede.estacionarVeiculo("XYZ-9999", 0);
            sede.estacionarVeiculo("ABC-1111", 1);
            sede.estacionarVeiculo("DEF-2222", 2);
            sede.estacionarVeiculo("GHI-3333", 3); // Exception
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erro na garagem: " + e.toString());
        }
    }
}

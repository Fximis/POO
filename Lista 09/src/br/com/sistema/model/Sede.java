package br.com.sistema.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sede {
    private String[] vagasGaragem = new String[3];
    private List<Credencial> historicoCatraca = new ArrayList<>();
    private Set<Credencial> acessoCofre = new HashSet<>();

    public void estacionarVeiculo(String placa, int vaga) {
        vagasGaragem[vaga] = placa;
        System.out.println("Veículo " + placa + " estacionado na vaga " + vaga);
    }

    public void registrarPassagemCatraca(Credencial c) {
        historicoCatraca.add(c);
        System.out.println("Passagem registrada: " + c.getCodigoHex());
    }

    public void autorizarEntradaCofre(Credencial c) {
        boolean adicionou = acessoCofre.add(c);
        if (!adicionou) {
            System.out.println("Alerta de Segurança: Tentativa de acesso duplicado com credencial já ativa no cofre");
        } else {
            System.out.println("Acesso cofre autorizado: " + c.getCodigoHex());
        }
    }
}

package br.com.zerotrust.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SistemaSeguranca {
    private Veiculo[] vagasGaragem;
    private List<Funcionario> catracaPrincipal = new ArrayList<>();
    private Set<Credencial> cofreFisico = new HashSet<>();

    public SistemaSeguranca(int totalVagas) {
        this.vagasGaragem = new Veiculo[totalVagas];
    }

    public void estacionarVeiculo(Veiculo v, int vaga) {
        this.vagasGaragem[vaga] = v;
        System.out.println("Garagem: Veículo [" + v.getPlaca() + "] estacionado na vaga [" + vaga + "]");
    }

    public void registrarCatraca(Funcionario f) {
        this.catracaPrincipal.add(f);
        System.out.println("Catraca: Acesso liberado para [" + f.getNome() + "]");
    }

    public void acessarCofre(Credencial cred) {
        boolean adicionou = this.cofreFisico.add(cred);
        if (adicionou) {
            System.out.println("Cofre: Acesso CONCEDIDO. Bem-vindo(a) [" + cred.getTitular().getNome() + "]");
        } else {
            System.out.println("ALERTA MÁXIMO: Credencial [" + cred.getCodigoHex() + "] bloqueada! Tentativa de clonagem detectada.");
        }
    }
}

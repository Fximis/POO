package br.com.nexuscloud.model;

import br.com.nexuscloud.exception.CapacidadeExcedidaException;
import br.com.nexuscloud.exception.ServidorOfflineException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Servidor {
    private String ip;
    private double precoPorHora;
    private double ramTotal;
    private boolean statusLigado;
    private List<Container> containers = new ArrayList<>();

    public Servidor(String ip, double ramTotal, double precoPorHora) {
        this.ip = ip;
        this.ramTotal = ramTotal;
        this.precoPorHora = precoPorHora;
        this.statusLigado = false;
    }

    public void ligar() {
        this.statusLigado = true;
    }

    public void desligar() {
        this.statusLigado = false;
    }

    public String getIp() {
        return ip;
    }

    public double getPrecoPorHora() {
        return precoPorHora;
    }

    public double getRamTotal() {
        return ramTotal;
    }

    public boolean isStatusLigado() {
        return statusLigado;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void alocarContainer(Container c) throws CapacidadeExcedidaException {
        if (!this.statusLigado) {
            throw new ServidorOfflineException("Erro: Não é possível alocar containers em um servidor desligado.");
        }
        if (this.calcularRamEmUso() + c.getRamNecessaria() > this.ramTotal) {
            throw new CapacidadeExcedidaException("Falha Crítica: Memória RAM insuficiente no servidor " + this.ip + ".");
        }
        this.containers.add(c);
    }

    public double calcularRamEmUso() {
        return this.containers.stream()
                .mapToDouble(Container::getRamNecessaria)
                .sum();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Servidor other = (Servidor) obj;
        return Objects.equals(this.ip, other.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }
}

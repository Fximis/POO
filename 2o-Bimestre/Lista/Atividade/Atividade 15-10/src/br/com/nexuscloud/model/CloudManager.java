package br.com.nexuscloud.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CloudManager {
    private Map<Cliente, Set<Servidor>> bancoDeDados = new HashMap<>();

    public CloudManager() {
    }

    public void registrarCliente(Cliente c) {
        this.bancoDeDados.putIfAbsent(c, new HashSet<>());
    }

    public void provisionarServidor(Cliente c, Servidor s) {
        this.bancoDeDados.computeIfAbsent(c, k -> new HashSet<>()).add(s);
    }

    public double calcularFaturaAtiva(Cliente c) {
        return this.bancoDeDados.getOrDefault(c, Collections.emptySet()).stream()
                .filter(Servidor::isStatusLigado)
                .mapToDouble(Servidor::getPrecoPorHora)
                .sum();
    }
}

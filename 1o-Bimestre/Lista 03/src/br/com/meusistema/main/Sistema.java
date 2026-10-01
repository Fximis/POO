package br.com.meusistema.main;

import br.com.meusistema.model.Carro;

public class Sistema {
    public static void main(String[] args) {
        System.out.println("Iniciando o sistema...");

        Carro meuCarro = new Carro();
        meuCarro.modelo = "Fusca";
        meuCarro.cor = "Azul";
        meuCarro.buzinar();

        Carro carro2 = new Carro();
        carro2.modelo = "Ferrari";
        carro2.cor = "Vermelha";
        carro2.buzinar();
    }
}

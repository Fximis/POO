package br.com.meusistema.main;

import br.com.meusistema.model.Carro;

public class Sistema {
    public static void main(String[] args) {
        System.out.println("Iniciando o sistema...");

        Carro meuCarro = new Carro();
        meuCarro.setModelo("Fusca");
        meuCarro.setCor("Azul");
        meuCarro.setVelocidade(-500); // testa o erro
        meuCarro.buzinar();
    }
}

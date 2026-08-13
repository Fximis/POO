package br.meusistema.main;

import br.com.meusistema.model.Carro;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sistema {
    public static void main(String[] args) {
        System.out.println("Iniciando o sistema...");

        List<String> modelos = Arrays.asList("FERRARI", "SUBARU", "PORSCHE", "LAMBORGUINI", "FUSCA");
        List<String> cores = Arrays.asList("Vermelho", "Preto", "Branco", "Amarelo", "Verde");

        List<Carro> meusCarros = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            Carro novoCarro = new Carro();

            novoCarro.modelo = modelos.get(i);
            novoCarro.cor = cores.get(i);

            meusCarros.add(novoCarro);

            novoCarro.buzinar();

            System.out.println("Carro " + (i + 1) + "passou...");

        }
    }
}

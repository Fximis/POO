package br.com.nexuscloud.main;

import br.com.nexuscloud.exception.CapacidadeExcedidaException;
import br.com.nexuscloud.model.Cliente;
import br.com.nexuscloud.model.CloudManager;
import br.com.nexuscloud.model.Container;
import br.com.nexuscloud.model.Servidor;
import java.util.Locale;

public class MainCloud {
    public static void main(String[] args) {
        // 1. Instancie o CloudManager
        CloudManager manager = new CloudManager();

        // 2. Instancie 1 Cliente (Ex: "111.222.333-44", "Wayne Enterprises")
        Cliente cliente = new Cliente("111.222.333-44", "Wayne Enterprises");

        // 3. Instancie 1 Servidor (Ex: IP "192.168.0.1", RAM Total 16.0 GB, Preço R$ 5.50)
        Servidor servidor = new Servidor("192.168.0.1", 16.0, 5.50);

        // 4. Registre o cliente no banco de dados do Manager e adicione o servidor a ele
        manager.registrarCliente(cliente);
        manager.provisionarServidor(cliente, servidor);

        // 5. Instancie 2 Containers diferentes (Ex: ID "C-01" com 10.0 RAM e ID "C-02" com 8.0 RAM)
        Container c1 = new Container("C-01", "Database", 10.0);
        Container c2 = new Container("C-02", "WebApp", 8.0);

        // 6. Teste Falha Unchecked: Tente alocar o Container 1 no Servidor desligado
        // servidor.alocarContainer(c1);

        // 7. Siga em frente: Comente a linha do Passo 6 após testar, para o programa poder prosseguir.
        // Chame o método ligar() do servidor.
        servidor.ligar();

        // 8. Alocou com sucesso: Aloque o Container 1 no Servidor (agora vai dar certo).
        try {
            servidor.alocarContainer(c1);
            System.out.println("Container " + c1.getId() + " alocado com sucesso! RAM em uso: " + servidor.calcularRamEmUso() + " GB");
        } catch (CapacidadeExcedidaException e) {
            System.out.println(e.getMessage());
        }

        // 9. Teste Falha Checked: Tente alocar o Container 2. Envolva esta chamada obrigatoriamente em um bloco try-catch.
        try {
            servidor.alocarContainer(c2);
        } catch (CapacidadeExcedidaException e) {
            System.out.println(e.getMessage());
        }

        // 10. Imprima no console o valor retornado por calcularFaturaAtiva do CloudManager. O valor deve ser 5.50.
        double faturaAtiva = manager.calcularFaturaAtiva(cliente);
        System.out.printf(Locale.US, "Fatura ativa: R$ %.2f%n", faturaAtiva);
    }
}

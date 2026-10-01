package br.com.starlog.main;

import br.com.starlog.exception.CapacidadeExcedidaException;
import br.com.starlog.model.BaseLancamento;
import br.com.starlog.model.Carga;
import br.com.starlog.model.ModuloCarga;

import java.util.HashSet;
import java.util.Set;

/**
 * Ponto de entrada do sistema StarLog-Zero e executor do protocolo de testes
 * adversariais Chaos Monkey para validação empírica de resiliência e integridade.
 */
public class MainResgate {

    // Utilização de escape Unicode para garantir renderização perfeita em codepages Windows
    private static final String SUCESSO_PREFIX = "\u2705 SUCESSO";
    private static final String FALHA_PREFIX = "\u274C FALHA";

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("     SISTEMA LOGÍSTICO STARLOG-ZERO - PROTOCOLO CHAOS MONKEY (RESILIÊNCIA)     ");
        System.out.println("================================================================================");

        boolean ataque1Sucesso = executarAtaque1();
        boolean ataque2Sucesso = executarAtaque2();
        boolean ataque3Sucesso = executarAtaque3();
        boolean ataque4Sucesso = executarAtaque4();

        System.out.println("\n================================================================================");
        if (ataque1Sucesso && ataque2Sucesso && ataque3Sucesso && ataque4Sucesso) {
            System.out.println(" STATUS GERAL: TODOS OS 4 ATAQUES FORAM REPELIDOS COM SUCESSO TOTAL!");
            System.out.println(" MOTOR DE DADOS STARLOG-ZERO VALIDADO E 100% OPERACIONAL.");
            System.out.println("================================================================================\n");
            // Termina normalmente com exit code 0
        } else {
            System.err.println(" STATUS GERAL: DETECTADA VULNERABILIDADE EM UM OU MAIS ATAQUES!");
            System.out.println("================================================================================\n");
            System.exit(1);
        }
    }

    /**
     * Ataque 1: Injeção de Carga com Dados Espúrios.
     * Valida que o construtor de Carga aplica fail-fast e lança IllegalArgumentException
     * para códigos nulos, vazios, em branco, peso zero ou negativo.
     */
    private static boolean executarAtaque1() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" [ATAQUE 1] Injeção de Carga com Dados Espúrios (Fail-Fast & Validação de Entrada)");
        System.out.println("--------------------------------------------------------------------------------");

        boolean okNull = false;
        boolean okVazio = false;
        boolean okBranco = false;
        boolean okPesoZero = false;
        boolean okPesoNeg = false;

        // Vetor 1: Código de rastreio nulo
        try {
            new Carga(null, "ELETRONICOS", 10.0, 100.0);
            System.out.println(" [!] Falha: Carga com codigoRastreio nulo foi instanciada.");
        } catch (IllegalArgumentException e) {
            System.out.println(" [OK] Interceptado codigoRastreio nulo: " + e.getMessage());
            okNull = true;
        }

        // Vetor 2: Código de rastreio vazio
        try {
            new Carga("", "ELETRONICOS", 10.0, 100.0);
            System.out.println(" [!] Falha: Carga com codigoRastreio vazio foi instanciada.");
        } catch (IllegalArgumentException e) {
            System.out.println(" [OK] Interceptado codigoRastreio vazio: " + e.getMessage());
            okVazio = true;
        }

        // Vetor 3: Código de rastreio com apenas espaços em branco
        try {
            new Carga("   ", "ELETRONICOS", 10.0, 100.0);
            System.out.println(" [!] Falha: Carga com codigoRastreio em branco foi instanciada.");
        } catch (IllegalArgumentException e) {
            System.out.println(" [OK] Interceptado codigoRastreio em branco: " + e.getMessage());
            okBranco = true;
        }

        // Vetor 4: Peso igual a zero
        try {
            new Carga("TRK-VALID-01", "ELETRONICOS", 0.0, 100.0);
            System.out.println(" [!] Falha: Carga com peso zero foi instanciada.");
        } catch (IllegalArgumentException e) {
            System.out.println(" [OK] Interceptado peso zero: " + e.getMessage());
            okPesoZero = true;
        }

        // Vetor 5: Peso estritamente negativo
        try {
            new Carga("TRK-VALID-02", "ELETRONICOS", -15.5, 100.0);
            System.out.println(" [!] Falha: Carga com peso negativo foi instanciada.");
        } catch (IllegalArgumentException e) {
            System.out.println(" [OK] Interceptado peso negativo: " + e.getMessage());
            okPesoNeg = true;
        }

        if (okNull && okVazio && okBranco && okPesoZero && okPesoNeg) {
            System.out.println(SUCESSO_PREFIX + ": Ataque 1 interceptado com sucesso! Dados espúrios rejeitados via Fail-Fast.");
            return true;
        } else {
            System.out.println(FALHA_PREFIX + ": Ataque 1 falhou na rejeição de dados espúrios.");
            return false;
        }
    }

    /**
     * Ataque 2: Clone das Sombras (Deduplicação e Identidade Semântica).
     * Valida que duas instâncias de Carga com o mesmo codigoRastreio são consideradas
     * iguais, possuem o mesmo hashCode e sofrem deduplicação em estruturas baseadas em hash (HashSet).
     */
    private static boolean executarAtaque2() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" [ATAQUE 2] Clone das Sombras (Deduplicação & Contrato equals/hashCode)");
        System.out.println("--------------------------------------------------------------------------------");

        Carga original = new Carga("SHADOW-999", "AltaPrioridade", 50.0, 1500.0);
        Carga clone = new Carga("SHADOW-999", "BaixaPrioridade", 200.0, 50.0);
        Carga legitimaDistinta = new Carga("LEGIT-001", "AltaPrioridade", 50.0, 1500.0);

        boolean igualAoClone = original.equals(clone);
        boolean hashCodesIguais = (original.hashCode() == clone.hashCode());
        boolean diferenteDeOutra = !original.equals(legitimaDistinta);
        boolean diferenteDeNull = !original.equals(null);

        Set<Carga> cofreDeduplicado = new HashSet<>();
        cofreDeduplicado.add(original);
        cofreDeduplicado.add(clone);
        boolean deduplicadoComSucesso = (cofreDeduplicado.size() == 1);

        System.out.println(" [INFO] original.equals(clone): " + igualAoClone);
        System.out.println(" [INFO] original.hashCode() == clone.hashCode(): " + hashCodesIguais);
        System.out.println(" [INFO] original.equals(legitimaDistinta): " + !diferenteDeOutra);
        System.out.println(" [INFO] original.equals(null): " + !diferenteDeNull);
        System.out.println(" [INFO] Tamanho do HashSet com original + clone: " + cofreDeduplicado.size() + " (Esperado: 1)");

        if (igualAoClone && hashCodesIguais && diferenteDeOutra && diferenteDeNull && deduplicadoComSucesso) {
            System.out.println(SUCESSO_PREFIX + ": Ataque 2 neutralizado! Clone identificado e deduplicado via equals/hashCode.");
            return true;
        } else {
            System.out.println(FALHA_PREFIX + ": Ataque 2 falhou no contrato equals/hashCode.");
            return false;
        }
    }

    /**
     * Ataque 3: Ruptura de Limite (Capacidade Máxima & Checked Exception).
     * Valida que uma tentativa de sobrecarregar um módulo além de sua capacidadeMaxima
     * dispara a exceção checada CapacidadeExcedidaException e preserva a integridade do estado.
     */
    private static boolean executarAtaque3() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" [ATAQUE 3] Ruptura de Limite (Capacidade Máxima & Checked Exception)");
        System.out.println("--------------------------------------------------------------------------------");

        BaseLancamento base = new BaseLancamento();
        ModuloCarga modulo = new ModuloCarga("MOD-TEST-3", 2);
        base.cadastrarModulo(modulo);

        ModuloCarga moduloRecuperado = base.buscarModulo("MOD-TEST-3");
        boolean excecaoInterceptada = false;

        try {
            moduloRecuperado.carregarCarga(new Carga("CARGA-1", "Alimentos", 10.0, 100.0));
            System.out.println(" [OK] Carga 1/2 carregada no módulo.");

            moduloRecuperado.carregarCarga(new Carga("CARGA-2", "Eletrônicos", 20.0, 200.0));
            System.out.println(" [OK] Carga 2/2 carregada no módulo.");

            System.out.println(" [!] Tentando forçar carga 3 em módulo com capacidade 2...");
            moduloRecuperado.carregarCarga(new Carga("CARGA-3", "Química", 30.0, 300.0));
            System.out.println(" [!] Falha: Carga além da capacidade foi aceita sem disparar exceção!");
        } catch (CapacidadeExcedidaException e) {
            System.out.println(" [OK] Interceptada CapacidadeExcedidaException com sucesso: " + e.getMessage());
            excecaoInterceptada = true;
        }

        boolean integridadeTamanho = (moduloRecuperado.getCargas().size() == 2);
        System.out.println(" [INFO] Contagem final de cargas no módulo: " + moduloRecuperado.getCargas().size() + " (Esperado: 2)");

        if (excecaoInterceptada && integridadeTamanho) {
            System.out.println(SUCESSO_PREFIX + ": Ataque 3 bloqueado! CapacidadeExcedidaException capturada ao atingir o limite.");
            return true;
        } else {
            System.out.println(FALHA_PREFIX + ": Ataque 3 falhou na barreira de contenção de capacidade.");
            return false;
        }
    }

    /**
     * Ataque 4: Processamento Analítico Funcional (Streams API).
     * Valida os cálculos de agregação funcional em ModuloCarga contra o ground truth matemático.
     */
    private static boolean executarAtaque4() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" [ATAQUE 4] Processamento Analítico Funcional (Streams API & Clean Code)");
        System.out.println("--------------------------------------------------------------------------------");

        ModuloCarga modulo = new ModuloCarga("MOD-ANALYTICS", 10);
        try {
            modulo.carregarCarga(new Carga("TRK-A1", "Medicamentos", 10.0, 500.0));
            modulo.carregarCarga(new Carga("TRK-A2", "Eletrônicos", 80.0, 1200.0));
            modulo.carregarCarga(new Carga("TRK-A3", "Medicamentos", 60.0, 800.0));
            modulo.carregarCarga(new Carga("TRK-A4", "Equipamentos", 150.0, 2500.0));
        } catch (CapacidadeExcedidaException e) {
            System.out.println(" [!] Erro inesperado ao carregar cargas no cenário do Ataque 4: " + e.getMessage());
            return false;
        }

        double totalSeguro = modulo.calcularSeguroTotal();
        long totalMedicamentos = modulo.contarPorCategoria("Medicamentos");
        long totalInexistente = modulo.contarPorCategoria("Inexistente");
        double seguroPesadas = modulo.calcularSeguroPesadas(50.0);

        // Ground truth matemático:
        // Seguro Total: 500 + 1200 + 800 + 2500 = 5000.0
        // Medicamentos: 2
        // Inexistente: 0
        // Seguro Pesadas (> 50kg): TRK-A2 (80kg: 1200) + TRK-A3 (60kg: 800) + TRK-A4 (150kg: 2500) = 4500.0
        boolean totalOk = Math.abs(totalSeguro - 5000.0) < 0.0001;
        boolean categoriaOk = (totalMedicamentos == 2L) && (totalInexistente == 0L);
        boolean pesadasOk = Math.abs(seguroPesadas - 4500.0) < 0.0001;

        System.out.println(" [INFO] Seguro Total: R$ " + totalSeguro + " (Esperado: 5000.0) -> " + (totalOk ? "OK" : "FALHA"));
        System.out.println(" [INFO] Contagem 'Medicamentos': " + totalMedicamentos + " (Esperado: 2) -> " + (totalMedicamentos == 2 ? "OK" : "FALHA"));
        System.out.println(" [INFO] Contagem 'Inexistente': " + totalInexistente + " (Esperado: 0) -> " + (totalInexistente == 0 ? "OK" : "FALHA"));
        System.out.println(" [INFO] Seguro Cargas > 50kg: R$ " + seguroPesadas + " (Esperado: 4500.0) -> " + (pesadasOk ? "OK" : "FALHA"));

        if (totalOk && categoriaOk && pesadasOk) {
            System.out.println(SUCESSO_PREFIX + ": Ataque 4 concluído! Processamento analítico funcional executado com precisão.");
            return true;
        } else {
            System.out.println(FALHA_PREFIX + ": Ataque 4 falhou na validação matemática das operações funcionais.");
            return false;
        }
    }
}

# Atividade Prática • NexusCloud Manager (15/10)

> **UniCesumar • Engenharia de Software & ADS**  
> **Docente:** Prof. Me. Júlio B. Herculani  
> **Missão:** Núcleo de Orquestração de Servidores Virtuais e Containers (POJOs com Identidade Lógica, Estruturas de Dados Baseadas em Hash, Exceções Checked/Unchecked e Streams API)

---

## 🎯 Arquitetura da Solução

```text
Atividade 15-10/
├── README.md
└── src/
    └── br/com/nexuscloud/
        ├── exception/
        │   ├── CapacidadeExcedidaException.java  <- Exceção Checked (extends Exception)
        │   └── ServidorOfflineException.java     <- Exceção Unchecked (extends RuntimeException)
        ├── model/
        │   ├── Cliente.java                      <- Identidade lógica baseada exclusivamente no CPF
        │   ├── Container.java                    <- Identidade lógica baseada exclusivamente no ID
        │   ├── Servidor.java                     <- Alocação Fail-Fast e auditoria de RAM via Streams API
        │   └── CloudManager.java                 <- Persistência em memória (HashMap/HashSet) e tarifação via Streams API
        └── main/
            └── MainCloud.java                    <- Roteiro de homologação dos 10 passos de negócio
```

---

## 🛠️ Regras de Negócio Implementadas

1. **RN01 — Domínio e Identidade Absoluta (POJOs):**
   - Encapsulamento estrito com atributos privados e métodos de acesso.
   - Sobrescrita de `equals()` e `hashCode()` garantindo igualdade lógica por chave de negócio: `Cliente` por `cpf`, `Servidor` por `ip` e `Container` por `id`.
2. **RN02 — Estruturas de Associação (Collections):**
   - `Servidor` inicializa `containers` diretamente na declaração como `new ArrayList<>()`, prevenindo `NullPointerException`.
   - `CloudManager` inicializa `bancoDeDados` diretamente na declaração como `new HashMap<>()`, associando cada `Cliente` a um `Set<Servidor>` (`HashSet<>`) livre de duplicatas.
3. **RN03 — Motor de Alocação e Resiliência (Exceções Fail-Fast):**
   - **Regra de Status (Unchecked):** `ServidorOfflineException` é disparada caso se tente alocar um container em um servidor desligado (`statusLigado == false`).
   - **Regra de Capacidade (Checked):** `CapacidadeExcedidaException` é disparada caso a soma da RAM em uso com a RAM necessária do novo container ultrapasse a `ramTotal` do servidor.
4. **RN04 — Motor de Auditoria e Finanças (Streams API):**
   - `Servidor.calcularRamEmUso()`: pipeline funcional com `.stream().mapToDouble(Container::getRamNecessaria).sum()`, sem uso de laços imperativos ou condicionais.
   - `CloudManager.calcularFaturaAtiva(Cliente)`: pipeline funcional com `.stream().filter(Servidor::isStatusLigado).mapToDouble(Servidor::getPrecoPorHora).sum()`, tarifando exclusivamente os servidores ativos do cliente.

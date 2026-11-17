# Laboratório Interativo • Aula 30 | Padrão DAO & CRUD Completo

> **UniCesumar • Engenharia de Software & ADS**  
> **Docente:** Prof. Me. Júlio B. Herculani  
> **Missão:** Sistema de Almoxarifado StarLog (Padrão DAO, Inversão de Dependência e Ciclo CRUD Completo)  
> **Chave Criptográfica:** `sqlite` (AES-GCM 256 bits)

---

## 🎯 Arquitetura da Solução

```text
exc 30/
├── README.md
└── src/
    └── br/com/starlog/
        ├── factory/
        │   └── ConexaoFactory.java      <- Provedor centralizado de conexões JDBC SQLite
        ├── model/
        │   └── Produto.java             <- Entidade de domínio pura com invariantes blindadas
        ├── dao/
        │   ├── ProdutoDAO.java          <- Interface CRUD desacoplada (DIP - SOLID)
        │   └── ProdutoDAOSqlite.java    <- Implementação com SQLite, PreparedStatement e Optional
        ├── controller/
        │   └── ProdutoController.java   <- Orquestrador das operações CRUD de negócio
        └── main/
            └── Main.java                <- Roteiro canônico de testes das 5 etapas CRUD
```

---

## 🔐 Gabarito Oficial Descriptografado

Código obtido após quebra da proteção criptográfica AES-GCM (palavra-chave: `sqlite`).

1. **Domínio Puro (`Produto.java`):** Construtor e setters com blindagem de invariantes (`IllegalArgumentException` para nome nulo/menor que 3 caracteres, preço <= 0 e estoque < 0).
2. **Contrato Desacoplado (`ProdutoDAO.java`):** Interface pura declarando as 5 operações CRUD (`salvar`, `buscarPorId`, `listarTodos`, `atualizar`, `excluir`).
3. **Persistência SQLite (`ProdutoDAOSqlite.java`):**
   - Criação determinística da tabela `produtos` no construtor;
   - `PreparedStatement` tipado sem vulnerabilidade a SQL Injection;
   - Captura de chaves geradas via `Statement.RETURN_GENERATED_KEYS`;
   - Mapeamento centralizado de tuplas (`mapearLinha`);
   - Validação de linhas afetadas (`linhasAfetadas > 0`) em `atualizar` e `excluir`;
   - Retorno resiliente com `Optional<Produto>` erradicando o `NullPointerException`.
4. **Orquestração e Testes (`ProdutoController.java`, `ConexaoFactory.java` e `Main.java`):** Controller desacoplado via injeção de dependência, fábrica de conexão JDBC SQLite e script completo executando o ciclo de vida CRUD (Create, Read, Update, Delete e Final State).

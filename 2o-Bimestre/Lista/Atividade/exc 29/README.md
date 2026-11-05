# Laboratório Interativo • Aula 29 | Padrão DAO & Mapeamento Objeto-Relacional

> **UniCesumar • Engenharia de Software & ADS**  
> **Docente:** Prof. Me. Júlio B. Herculani  
> **Missão:** Catálogo BookStore Enterprise (Padrão DAO, Inversão de Dependência e Mapeamento Objeto-Relacional)  
> **Chave Criptográfica:** `adapter` (AES-GCM 256 bits)

---

## 🎯 Arquitetura da Solução

```text
exc 29/
├── README.md
└── src/
    └── br/com/bookstore/
        ├── factory/
        │   └── ConexaoFactory.java   <- Provedor centralizado de conexões JDBC SQLite
        ├── model/
        │   └── Livro.java            <- Entidade de domínio pura com invariantes blindadas
        ├── dao/
        │   ├── LivroDAO.java         <- Interface desacoplada (DIP - SOLID)
        │   └── LivroDAOSqlite.java   <- Implementação com SQLite, PreparedStatement e Optional
        └── main/
            └── Main.java             <- Roteiro canônico de testes e validação
```

---

## 🔐 Gabarito Oficial Descriptografado

Código obtido após quebra da proteção criptográfica AES-GCM (palavra-chave: `adapter`).

1. **Domínio Puro (`Livro.java`):** Construtor com blindagem de invariantes (`IllegalArgumentException` para título/autor vazios e preço <= 0).
2. **Contrato Desacoplado (`LivroDAO.java`):** Interface pura sem acoplamento a detalhes de persistência.
3. **Persistência SQLite (`LivroDAOSqlite.java`):**
   - Criação determinística da tabela `livros` no construtor;
   - `PreparedStatement` tipado sem vulnerabilidade a SQL Injection;
   - Captura de chaves geradas via `Statement.RETURN_GENERATED_KEYS`;
   - Mapeamento centralizado de tuplas (`mapearLinha`);
   - Retorno resiliente com `Optional<Livro>` erradicando o `NullPointerException`.
4. **Infraestrutura e Testes (`ConexaoFactory.java` e `Main.java`):** Fábrica de conexão JDBC SQLite e script completo executando o fluxo canônico de testes.

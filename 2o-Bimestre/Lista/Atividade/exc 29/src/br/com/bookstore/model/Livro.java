package br.com.bookstore.model;

public class Livro {
    private Long id;
    private String titulo;
    private String autor;
    private double preco;

    // Construtor completo para hidratação vinda do banco
    public Livro(Long id, String titulo, String autor, double preco) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título do livro é obrigatório!");
        }
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O autor do livro é obrigatório!");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("O preço do livro deve ser positivo!");
        }
        this.id = id;
        this.titulo = titulo.trim();
        this.autor = autor.trim();
        this.preco = preco;
    }

    // Construtor de conveniência para cadastro novo (sem ID)
    public Livro(String titulo, String autor, double preco) {
        this(null, titulo, autor, preco);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public double getPreco() { return preco; }

    @Override
    public String toString() {
        return String.format("[#%d] %s (Autor: %s) - R$ %.2f", id, titulo, autor, preco);
    }
}

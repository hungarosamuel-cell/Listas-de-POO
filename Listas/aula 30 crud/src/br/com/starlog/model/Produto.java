package br.com.starlog.model;

public class Produto {

    private Long id;
    private String nome;
    private double preco;
    private int estoque;

    // Construtor de persistência: usado quando o registro já existe no banco (com id)
    public Produto(Long id, String nome, double preco, int estoque) {
        this(nome, preco, estoque);
        this.id = id;
    }

    // Construtor de negócio: produto novo, sem id (o banco gera via AUTOINCREMENT)
    public Produto(String nome, double preco, int estoque) {
        if (nome == null || nome.trim().length() < 3) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 3 caracteres.");
        }
        validarPreco(preco);
        validarEstoque(estoque);
        this.nome = nome.trim();
        this.preco = preco;
        this.estoque = estoque;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPreco(double preco) {
        validarPreco(preco);
        this.preco = preco;
    }

    public void setEstoque(int estoque) {
        validarEstoque(estoque);
        this.estoque = estoque;
    }

    private static void validarPreco(double preco) {
        if (preco <= 0.0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
    }

    private static void validarEstoque(int estoque) {
        if (estoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo.");
        }
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.US, "Produto[id=%d, nome=%s, preco=R$ %.2f, estoque=%d]",
                id, nome, preco, estoque);
    }
}

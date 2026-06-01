package udesc.br.model;

public class Medicamento {
    private String id;
    private String nome;
    private double valorCompra;
    private double valorAplicacao;
    private int estoque;

    public Medicamento(String id, String nome, double valorCompra, double valorAplicacao, int estoque) {
        this.id = id;
        this.nome = nome;
        this.valorCompra = valorCompra;
        this.valorAplicacao = valorAplicacao;
        this.estoque = estoque;
    }
}

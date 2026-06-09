package udesc.br.model;

public class Medicamento {
    private int id;
    private String nome;
    private double valorCompra;
    private double valorAplicacao;
    private int estoque;

    public Medicamento(String nome, double valorCompra, double valorAplicacao) {
        this.id = id;
        this.nome = nome;
        this.valorCompra = valorCompra;
        this.valorAplicacao = valorAplicacao;
        this.estoque = 0;
    }
    public boolean adicionarEstoque(int quantidade){
        if (estoque <= 0){
            return false;
        }
        this.estoque += quantidade;
        return true;
    }
    public boolean removerEstoque(int quantidade){
        if (estoque <= 0){
            return false;
        }
        this.estoque -= quantidade;
        return true;
    }

    public boolean setEstoque(int quantidade){
        if (estoque <= 0){
            return false;
        }
        this.estoque += quantidade;
        return true;
    }
    public String getNome() {
        return nome;
    }
}

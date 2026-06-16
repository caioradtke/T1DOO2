package udesc.br.model;

public class Medicamento {
    private int id;
    private String nome;
    private double valorCompra;
    private double estoque; //medido em mg

    public Medicamento(String nome, double valorCompra) {
        this.id = id;
        this.nome = nome;
        this.valorCompra = valorCompra;
        this.estoque = 0;
    }
    public boolean adicionarEstoque(double quantidade){
        if (estoque <= 0){
            return false;
        }
        this.estoque += quantidade;
        return true;
    }
    public boolean removerEstoque(double quantidade){
        if (estoque <= 0){
            return false;
        }
        this.estoque -= quantidade;
        return true;
    }

    public boolean setEstoque(double quantidade){
        if (estoque <= 0){
            return false;
        }
        this.estoque = quantidade;
        return true;
    }
    public double getEstoque(){
        return estoque;
    }
    public String getNome() {
        return nome;
    }
    public int getId(){
        return this.id;
    }
    
    @Override
    public String toString(){
            return nome + " (" + estoque + " mg disponíveis)";
    }
}

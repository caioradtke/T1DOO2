package udesc.br.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL gera o ID automaticamente
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "valor_compra", nullable = false)
    private double valorCompra;

    @Column(nullable = false)
    private double estoque; // medido em mg

    public Medicamento(String nome, double valorCompra, double estoque) {
        this.nome = nome;
        this.valorCompra = valorCompra;
        this.estoque = estoque;
    }

    // JPA exige construtor vazio
    public Medicamento() {
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
    public Long getId(){
        return this.id;
    }
    
    @Override
    public String toString(){
            return nome + " (" + estoque + " mg disponíveis)";
    }
}

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
    private double valorCompraPorMg;

    @Column(nullable = false)
    private double estoque; // medido em mg
        
    public Medicamento(String nome, double valor, double estoque) {
        this.nome = nome;
        this.valorCompraPorMg = valor;
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
        if (estoque < quantidade){
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
    
    public double getValor(){
        return this.valorCompraPorMg;
    }
    public boolean setValor(double valor){
        if (valor < 0){
            return false;
        }
        this.valorCompraPorMg = valor;
        return true;
    }
    
    @Override
    public String toString(){
            return nome + " (" + estoque + " mg disponíveis)";
    }
}

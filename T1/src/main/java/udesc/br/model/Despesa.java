package udesc.br.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "despesa")
public class Despesa extends MovimentacaoFinanceira {

    @Column(nullable = false)
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @Column(name = "valor_por_miligrama", nullable = false)
    private double valorPorMg;

    public Despesa(String descricao, LocalDate data, double valor, int quantidade,
                    double valorPorMg, Medicamento medicamento) {
        super(descricao, data, valor);
        this.quantidade = quantidade;
        this.valorPorMg = valorPorMg;
        this.medicamento = medicamento;
    }

    // JPA exige construtor vazio
    public Despesa() {
    }

    @Override
    public double getValor(){
        return this.valorPorMg * this.quantidade;
    }

    public double getValorPorMg() {
        return valorPorMg;
    }
    public int  getQuantidade() {
        return quantidade;
    }
    
    public Medicamento getMedicamento() {
        return medicamento;
    }

    @Override
    public String toString(){
        return "Compra de " +
                quantidade +
                " unidades de " +
                medicamento.getNome() +
                " - " +
                valorPorMg +
                " - Valor total: " +
                getValor();
    }
}

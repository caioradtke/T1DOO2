package udesc.br.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "despesa")
public class Despesa extends MovimentacaoFinanceira {

    @Column(nullable = false)
    private double quantidade;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @Column(name = "valor_por_mg", nullable = false)
    private double valorPorMg;

    public Despesa(String descricao, LocalDate data, double quantidade,
                    double valorUnitario, Medicamento medicamento) {
        super(descricao, data);
        this.quantidade = quantidade;
        this.valorPorMg = valorUnitario;
        this.medicamento = medicamento;
    }

    // JPA exige construtor vazio
    public Despesa() {
    }

    @Override
    public double calcularValor(){
        return this.valorPorMg * this.quantidade;
    }

    public double getValorPorMg() {
        return valorPorMg;
    }
    public double  getQuantidade() {
        return quantidade;
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
                calcularValor();
    }
}

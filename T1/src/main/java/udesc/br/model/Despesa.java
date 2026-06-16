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

    @Column(name = "valor_unitario", nullable = false)
    private double valorUnitario;

    public Despesa(String descricao, LocalDate data, int quantidade,
                    double valorUnitario, Medicamento medicamento) {
        super(descricao, data);
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.medicamento = medicamento;
    }

    // JPA exige construtor vazio
    public Despesa() {
    }

    @Override
    public double calcularValor(){
        return this.valorUnitario * this.quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }
    public int  getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString(){
        return "Compra de " +
                quantidade +
                " unidades de " +
                medicamento.getNome() +
                " - " +
                valorUnitario +
                " - Valor total: " +
                calcularValor();
    }
}

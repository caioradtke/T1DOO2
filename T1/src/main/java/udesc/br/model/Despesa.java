package udesc.br.model;

import java.time.LocalDate;

public class Despesa extends MovimentaFinanceira{
    private int quantidade;
    private Medicamento medicamento;
    private double valorUnitario;
    public Despesa(String descricao, LocalDate data, int quantidade, double valorUnitario, Medicamento medicamento) {
        super(descricao, data);
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.medicamento = medicamento;
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

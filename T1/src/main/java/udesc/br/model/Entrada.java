package udesc.br.model;

import java.time.LocalDate;

public class Entrada extends MovimentaFinanceira {
    private Paciente paciente;
    private double valor;
    public Entrada(String id, String descricao, LocalDate data, Paciente paciente, double valor) {
        super(id, descricao, data);
        this.paciente = paciente;
        this.valor = valor;
    }

    @Override
    public double calcularValor() {
        return this.valor;
    }
}

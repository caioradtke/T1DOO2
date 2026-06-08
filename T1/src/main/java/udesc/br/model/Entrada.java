package udesc.br.model;

import java.time.LocalDate;

public class Entrada extends MovimentaFinanceira {
    private Paciente paciente;
    private double valor;
    public Entrada(String descricao, LocalDate data, Paciente paciente, double valor) {
        super(descricao, data);
        this.paciente = paciente;
        this.valor = valor;
    }

    @Override
    public double calcularValor() {
        return this.valor;
    }

    public Paciente getPaciente() {
        return paciente;
    }
    public boolean setValor(double valor) {
        if (valor <= 0) {
            return false;
        }
        this.valor = valor;
        return true;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "id='" + getId() + '\'' +
                ", paciente=" + paciente.getNome() +
                ", valor=" + valor +
                '}';
    }

}

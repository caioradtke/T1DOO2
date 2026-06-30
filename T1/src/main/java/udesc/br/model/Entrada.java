package udesc.br.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "entrada")
public class Entrada extends MovimentacaoFinanceira {

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    public Entrada(String descricao, LocalDate data, double valor, Paciente paciente) {
        super(descricao, data, valor);
        this.paciente = paciente;
    }

    // JPA exige construtor vazio
    public Entrada() {
    }

    public Paciente getPaciente() {
        return paciente;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "id='" + getId() + '\'' +
                ", paciente=" + paciente.getNome() +
                ", valor=" + getValor() +
                '}';
    }

}

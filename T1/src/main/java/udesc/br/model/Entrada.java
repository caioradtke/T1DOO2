package udesc.br.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "entrada")
public class Entrada extends MovimentacaoFinanceira {

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "aplicacao_id", nullable = false)
    private Aplicacao aplicacao;

    @Column(nullable = false)
    private double valor;

    public Entrada(String descricao, LocalDate data, Paciente paciente, Aplicacao aplicacao) {
        super(descricao, data);
        this.paciente = paciente;
        this.aplicacao = aplicacao;
        this.valor = aplicacao.getPreco();
    }

    // JPA exige construtor vazio
    public Entrada() {
    }
    @Override
    public double calcularValor() {
        return this.valor;
    }

    public Paciente getPaciente() {
        return paciente;
    }
    public Aplicacao getAplicacao(){
        return aplicacao;
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
